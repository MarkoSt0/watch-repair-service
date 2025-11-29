/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.watchrepairservice.communication.ServiceResult;
import rs.ac.bg.fon.watchrepairservice.dto.WatchDTO;
import rs.ac.bg.fon.watchrepairservice.entity.Client;
import rs.ac.bg.fon.watchrepairservice.entity.Watch;
import rs.ac.bg.fon.watchrepairservice.enums.Movement;
import rs.ac.bg.fon.watchrepairservice.mapper.WatchMapper;
import rs.ac.bg.fon.watchrepairservice.repository.ClientRepository;
import rs.ac.bg.fon.watchrepairservice.repository.RepairItemRepository;
import rs.ac.bg.fon.watchrepairservice.repository.WatchRepository;

/**
 *
 * @author Marko
 */
@Service
@Transactional
public class WatchService {
    private final WatchRepository watchRepo;
    private final ClientRepository clientRepo;
    private final RepairItemRepository repairItemRepo;

    public WatchService(WatchRepository watchRepo, ClientRepository clientRepo, RepairItemRepository repairItemRepo) {
        this.watchRepo = watchRepo;
        this.clientRepo = clientRepo;
        this.repairItemRepo = repairItemRepo;
    }
    
    public ServiceResult add(WatchDTO dto) {
        try {
            if (!WatchMapper.isValidDTO(dto)) {
                return ServiceResult.errorMessage("Invalid watch data. Brand and client are required.");
            }
            
            // toEntity without client!! Check toEntity method 
            Watch watch = WatchMapper.toEntity(dto);
            
            Client client = clientRepo.findById(dto.getIdClient())
                    .orElseThrow(() -> new EntityNotFoundException("Client with ID " + dto.getIdClient() + " not found."));
            
            //This is actually Client entity not idCLient - Hibernate automatic entity creation made mistake
            watch.setIdClient(client);
            
            Watch saved = watchRepo.save(watch);
            
            return ServiceResult.successMessageData(
                "Watch added successfully.", 
                WatchMapper.toDTO(saved)
            );
            
        } catch (EntityNotFoundException e) {
            return ServiceResult.errorMessage(e.getMessage());
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while adding watch: " + e.getMessage());
        }
    }
    
    public ServiceResult update(WatchDTO dto) {
        try {
            Watch existing = watchRepo.findById(dto.getIdWatch())
                    .orElseThrow(() -> new EntityNotFoundException("Watch not found."));
            
            if (!WatchMapper.isValidDTO(dto)) {
                return ServiceResult.errorMessage("Invalid watch data.");
            }
            
            WatchMapper.updateEntityFromDTO(dto, existing);
            
            if (dto.getIdClient() != null && 
                !dto.getIdClient().equals(existing.getIdClient().getIdClient())) {
                
                Client newClient = clientRepo.findById(dto.getIdClient())
                        .orElseThrow(() -> new EntityNotFoundException("Client with ID " + dto.getIdClient() + " not found."));
                
                existing.setIdClient(newClient);
            }
            
            Watch updated = watchRepo.save(existing);
            
            return ServiceResult.successMessageData(
                "Watch updated successfully.", 
                WatchMapper.toDTO(updated)
            );
            
        }catch (Exception e) {
            return ServiceResult.errorMessage("Error while updating watch: " + e.getMessage());
        }
    }
    
    public ServiceResult getWatch(Long id) {
        try {
            Watch watch = watchRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Watch not found."));
            
            return ServiceResult.successMessageData(
                "Watch found.", 
                WatchMapper.toDTO(watch)
            );
            
        } catch (EntityNotFoundException e) {
            return ServiceResult.errorMessage(e.getMessage());
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while fetching watch: " + e.getMessage());
        }
    }
    
    public ServiceResult getAllWatches() {
        List<WatchDTO> watchesDTO = WatchMapper.toDTOList(watchRepo.findAll());
        return ServiceResult.successMessageData("All watches loaded.", watchesDTO);
    }
    
    public ServiceResult getWatchesByClient(Long clientId) {
        try {
            Client client = clientRepo.findById(clientId)
                    .orElseThrow(() -> new EntityNotFoundException("Client not found."));
            
            List<Watch> watches = watchRepo.findByIdClient(client);
            List<WatchDTO> watchesDTO = WatchMapper.toDTOList(watches);
            
            return ServiceResult.successMessageData(
                "Watches for client loaded.", 
                watchesDTO
            );
            
        }catch (Exception e) {
            return ServiceResult.errorMessage("Error while fetching watches: " + e.getMessage());
        }
    }
    
    public ServiceResult getWatchesByBrand(String brand) {
        List<Watch> watches = watchRepo.findByBrand(brand);
        List<WatchDTO> watchesDTO = WatchMapper.toDTOList(watches);
        
        return ServiceResult.successMessageData(
            "Watches by brand loaded.", 
            watchesDTO
        );
    }
    
    public ServiceResult getWatchesByMovement(Movement movement) {
        List<Watch> watches = watchRepo.findByMovement(movement);
        List<WatchDTO> watchesDTO = WatchMapper.toDTOList(watches);
        
        return ServiceResult.successMessageData(
            "Watches by movement loaded.", 
            watchesDTO
        );
    }
    
    public ServiceResult deleteWatch(Long id) {
        try {
            Watch watch = watchRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Watch not found."));
            
             if (repairItemRepo.existsByIdWatch(watch)) {
                 return ServiceResult.errorMessage("Cannot delete watch that has repair history.");
             }
            
            watchRepo.deleteById(id);
            
            return ServiceResult.successMessage("Watch deleted successfully.");
            
        } catch (DataIntegrityViolationException e) {
            return ServiceResult.errorMessage("Cannot delete watch that has repair history.");
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while deleting watch: " + e.getMessage());
        }
    }
}
