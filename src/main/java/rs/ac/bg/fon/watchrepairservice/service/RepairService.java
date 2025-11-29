/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.watchrepairservice.communication.ServiceResult;
import rs.ac.bg.fon.watchrepairservice.dto.RepairDTO;
import rs.ac.bg.fon.watchrepairservice.dto.RepairItemDTO;
import rs.ac.bg.fon.watchrepairservice.dto.RepairItemPartDTO;
import rs.ac.bg.fon.watchrepairservice.entity.Client;
import rs.ac.bg.fon.watchrepairservice.entity.Employee;
import rs.ac.bg.fon.watchrepairservice.entity.Part;
import rs.ac.bg.fon.watchrepairservice.entity.Repair;
import rs.ac.bg.fon.watchrepairservice.entity.RepairItem;
import rs.ac.bg.fon.watchrepairservice.entity.RepairItemPart;
import rs.ac.bg.fon.watchrepairservice.entity.Watch;
import rs.ac.bg.fon.watchrepairservice.mapper.RepairItemMapper;
import rs.ac.bg.fon.watchrepairservice.mapper.RepairItemPartMapper;
import rs.ac.bg.fon.watchrepairservice.mapper.RepairMapper;
import rs.ac.bg.fon.watchrepairservice.repository.ClientRepository;
import rs.ac.bg.fon.watchrepairservice.repository.EmployeeRepository;
import rs.ac.bg.fon.watchrepairservice.repository.PartRepository;
import rs.ac.bg.fon.watchrepairservice.repository.RepairItemRepository;
import rs.ac.bg.fon.watchrepairservice.repository.RepairRepository;
import rs.ac.bg.fon.watchrepairservice.repository.WatchRepository;

/**
 *
 * @author Marko
 */
@Service
@Transactional
public class RepairService {
    private final RepairRepository repairRepo;
    private final EmployeeRepository employeeRepo;
    private final ClientRepository clientRepo;
    private final WatchRepository watchRepo;
    private final PartRepository partRepo;
    private final RepairItemRepository repairItemRepo;

    public RepairService(RepairRepository repairRepo, EmployeeRepository employeeRepo, ClientRepository clientRepo, WatchRepository watchRepo, PartRepository partRepo, RepairItemRepository repairItemRepo) {
        this.repairRepo = repairRepo;
        this.employeeRepo = employeeRepo;
        this.clientRepo = clientRepo;
        this.watchRepo = watchRepo;
        this.partRepo = partRepo;
        this.repairItemRepo = repairItemRepo;
    }
    
    // This is complicated solution, later i want to add 2 methods, first for making empty
    // repair and second one to use that method AND populate items...
    public ServiceResult add(RepairDTO dto){
        try {
            if (!RepairMapper.isValidDTO(dto)) {
                return ServiceResult.errorMessage("Invalid repair data. Brand and client are required.");
            }
            Repair repair = RepairMapper.toEntity(dto);
            
            Client client = clientRepo.findById(dto.getClient().getIdClient())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Client with ID " + dto.getClient().getIdClient() +
                                    " not found."));
            repair.setIdClient(client);
            
            Employee employee = employeeRepo.findById(dto.getEmployee().getIdEmployee())
                    .orElseThrow(()->new EntityNotFoundException(
                            "Employee with ID " + dto.getEmployee().getIdEmployee() +
                                    " not found."));
            repair.setIdEmployee(employee);
            
            List<RepairItemDTO> itemDTOs = dto.getRepairItemCollection();
            List<RepairItem> items = new ArrayList<>();
            
            for (RepairItemDTO itemDTO : itemDTOs) {
                RepairItem item = RepairItemMapper.toEntity(itemDTO);
                item.setIdRepair(repair);
                Watch watch = watchRepo.findById(itemDTO.getWatch().getIdWatch())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Watch with ID " + item.getIdWatch().getIdWatch() + " not found."));
                item.setIdWatch(watch);
                
                List<RepairItemPart> parts = new ArrayList<>();
                for (RepairItemPartDTO partDTO : itemDTO.getRepairItemPartCollection()) {
                    RepairItemPart part = RepairItemPartMapper.toEntity(partDTO);
                    part.setIdRepairItem(item);
                    
                    Part partEntity = partRepo.findById(partDTO.getPart().getIdPart())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Part with ID " + partDTO.getPart().getIdPart() + " not found."));
                    
                    part.setIdPart(partEntity);

                    parts.add(part);
                }  
                item.setRepairItemPartCollection(parts);
                
                BigDecimal partsCost = calculateRepairItemPartsCost(item);
                item.setPartsCost(partsCost);
                
                items.add(item);
            }
            repair.setRepairItemCollection(items);
            
            BigDecimal totalCost = calculateRepairTotalCost(repair);
            repair.setTotalCost(totalCost);
            
            Repair saved = repairRepo.save(repair);
            
            return ServiceResult.successMessageData(
                "Repair added successfully.", 
                RepairMapper.toDTOWithItems(saved)
            );
            
        } catch (EntityNotFoundException e) {
            return ServiceResult.errorMessage(e.getMessage());
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while adding watch: " + e.getMessage());
        }
    }
    
    public ServiceResult update(RepairDTO dto) {
        try {
            Repair existing = repairRepo.findById(dto.getIdRepair())
                    .orElseThrow(() -> new EntityNotFoundException("Repair not found."));
            
            if (!RepairMapper.isValidDTO(dto)) {
                return ServiceResult.errorMessage("Invalid repair data.");
            }
            
            RepairMapper.updateEntityFromDTO(dto, existing);
            
            if (dto.getClient() != null && 
                !dto.getClient().getIdClient().equals(existing.getIdClient().getIdClient())) {
                
                Client newClient = clientRepo.findById(dto.getClient().getIdClient())
                        .orElseThrow(() -> new EntityNotFoundException("Client not found."));
                existing.setIdClient(newClient);
            }
            
            if (dto.getEmployee() != null && 
                !dto.getEmployee().getIdEmployee().equals(existing.getIdEmployee().getIdEmployee())) {
                
                Employee newEmployee = employeeRepo.findById(dto.getEmployee().getIdEmployee())
                        .orElseThrow(() -> new EntityNotFoundException("Employee not found."));
                existing.setIdEmployee(newEmployee);
            }
            
            BigDecimal totalCost = calculateRepairTotalCost(existing);
            existing.setTotalCost(totalCost);
            
            Repair updated = repairRepo.save(existing);
            
            return ServiceResult.successMessageData(
                "Repair updated successfully.", 
                RepairMapper.toDTO(updated)
            );
            
        } catch (EntityNotFoundException e) {
            return ServiceResult.errorMessage(e.getMessage());
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while updating repair: " + e.getMessage());
        }
    }
    
    public ServiceResult getRepair(Long id) {
        try {
            Repair repair = repairRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Repair not found."));
            
            return ServiceResult.successMessageData(
                "Repair found.", 
                RepairMapper.toDTO(repair)
            );
            
        } catch (EntityNotFoundException e) {
            return ServiceResult.errorMessage(e.getMessage());
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while fetching repair: " + e.getMessage());
        }
    }
    
    public ServiceResult getRepairWithItems(Long id) {
        try {
            Repair repair = repairRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Repair not found."));
            
            return ServiceResult.successMessageData(
                "Repair with items found.", 
                RepairMapper.toDTOWithItems(repair)
            );
            
        } catch (EntityNotFoundException e) {
            return ServiceResult.errorMessage(e.getMessage());
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while fetching repair: " + e.getMessage());
        }
    }
    
    public ServiceResult getAllRepairs() {
        List<RepairDTO> repairsDTO = RepairMapper.toDTOList(repairRepo.findAll());
        return ServiceResult.successMessageData("All repairs loaded.", repairsDTO);
    }
    
    public ServiceResult deleteRepair(Long id) {
        try {
            Repair repair = repairRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Repair not found."));
            
            if (repair.getRepairItemCollection() != null && 
                !repair.getRepairItemCollection().isEmpty()) {
                return ServiceResult.errorMessage(
                    "Cannot delete repair that has repair items. Delete items first."
                );
            }
            
            repairRepo.deleteById(id);
            
            return ServiceResult.successMessage("Repair deleted successfully.");
            
        } catch (EntityNotFoundException e) {
            return ServiceResult.errorMessage(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ServiceResult.errorMessage(
                "Cannot delete repair that has repair items."
            );
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while deleting repair: " + e.getMessage());
        }
    }
    
    public ServiceResult recalculateTotalCost(Long repairId) {
        try {
            Repair repair = repairRepo.findById(repairId)
                    .orElseThrow(() -> new EntityNotFoundException("Repair not found."));
            
            BigDecimal totalCost = calculateRepairTotalCost(repair);
            repair.setTotalCost(totalCost);
            Repair updated = repairRepo.save(repair);
            
            return ServiceResult.successMessageData(
                "Total cost recalculated: " + totalCost, 
                RepairMapper.toDTO(updated)
            );
            
        } catch (EntityNotFoundException e) {
            return ServiceResult.errorMessage(e.getMessage());
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while recalculating: " + e.getMessage());
        }
    }
    
    private BigDecimal calculateRepairTotalCost(Repair repair) {
        if (repair == null || repair.getRepairItemCollection() == null) {
            return BigDecimal.ZERO;
        }
        
        return repair.getRepairItemCollection().stream()
                .map(item -> item.getPartsCost() != null ? 
                    item.getPartsCost() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private BigDecimal calculateRepairItemPartsCost(RepairItem repairItem) {
        if (repairItem == null || repairItem.getRepairItemPartCollection() == null) {
            return BigDecimal.ZERO;
        }
        
        return repairItem.getRepairItemPartCollection().stream()
                .map(part -> {
                    BigDecimal cost = part.getCost() != null ? part.getCost() : BigDecimal.ZERO;
                    Integer qty = part.getQuantity() != null ? part.getQuantity() : 0;
                    return cost.multiply(new BigDecimal(qty));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
