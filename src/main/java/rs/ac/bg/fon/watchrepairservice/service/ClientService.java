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
import rs.ac.bg.fon.watchrepairservice.dto.ClientDTO;
import rs.ac.bg.fon.watchrepairservice.dto.WatchDTO;
import rs.ac.bg.fon.watchrepairservice.entity.Client;
import rs.ac.bg.fon.watchrepairservice.entity.Watch;
import rs.ac.bg.fon.watchrepairservice.mapper.ClientMapper;
import rs.ac.bg.fon.watchrepairservice.mapper.WatchMapper;
import rs.ac.bg.fon.watchrepairservice.repository.ClientRepository;
import rs.ac.bg.fon.watchrepairservice.repository.RepairRepository;
import rs.ac.bg.fon.watchrepairservice.repository.WatchRepository;

/**
 *
 * @author Marko
 */
@Service
@Transactional
public class ClientService {
    private final WatchRepository watchRepo;
    private final ClientRepository clientRepo;
    private final RepairRepository repairRepo;

    public ClientService(WatchRepository watchRepo, ClientRepository clientRepo, RepairRepository repairRepo) {
        this.watchRepo = watchRepo;
        this.clientRepo = clientRepo;
        this.repairRepo = repairRepo;
    }
    
    // Next two methods does not work with client collecitons
    public ServiceResult add(ClientDTO dto) {
        try {
            if (!ClientMapper.isValidDTO(dto)) {
                return ServiceResult.errorMessage("Invalid client data. All data is required.");
            }
            
            // toEntity without watches!! Check toEntity method
            Client client = ClientMapper.toEntity(dto);
            
            Client saved = clientRepo.save(client);
            
            return ServiceResult.successMessageData(
                "Clinet added successfully.", 
                ClientMapper.toDTO(saved)
            );
            
        } catch (EntityNotFoundException e) {
            return ServiceResult.errorMessage(e.getMessage());
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while adding client: " + e.getMessage());
        }
    }
    
    public ServiceResult update(ClientDTO dto) {
        try {
            Client existing = clientRepo.findById(dto.getIdClient())
                    .orElseThrow(() -> new EntityNotFoundException("Client not found."));
            
            if (!ClientMapper.isValidDTO(dto)) {
                return ServiceResult.errorMessage("Invalid client data.");
            }
            
            if (!dto.getEmail().equals(existing.getEmail()) && 
                clientRepo.existsByEmail(dto.getEmail())) {
                return ServiceResult.errorMessage("Client with this email already exists.");
            }
            
            ClientMapper.updateEntityFromDTO(dto, existing);
            
            Client updated = clientRepo.save(existing);
            
            return ServiceResult.successMessageData(
                "Client updated successfully.", 
                ClientMapper.toDTO(updated)
            );
            
        }catch (Exception e) {
            return ServiceResult.errorMessage("Error while updating client: " + e.getMessage());
        }
    }
    
    public ServiceResult getClient(Long id) {
        try {
            Client client = clientRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Client not found."));
            
            return ServiceResult.successMessageData(
                "Client found.", 
                ClientMapper.toDTO(client)
            );
            
        } catch (EntityNotFoundException e) {
            return ServiceResult.errorMessage(e.getMessage());
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while fetching client: " + e.getMessage());
        }
    }
    
    public ServiceResult getAllClients() {
        List<ClientDTO> clientsDTO = ClientMapper.toDTOList(clientRepo.findAll());
        return ServiceResult.successMessageData("All clients loaded.", clientsDTO);
    }
    
    
    public ServiceResult getClientWithWatches(Long id) {
        try {
            Client client = clientRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Client not found."));
            
            return ServiceResult.successMessageData(
                "Client with watches found.", 
                ClientMapper.toDTOWithWatches(client)
            );
            
        }catch (Exception e) {
            return ServiceResult.errorMessage("Error while fetching client: " + e.getMessage());
        }
    }
    
    public ServiceResult delete(Long id) {
        try {
            Client client = clientRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Client not found."));
            
            if (repairRepo.existsByIdClient(client)) {
                return ServiceResult.errorMessage("Cannot delete client that was part of repair history.");
            }
            
            clientRepo.deleteById(id);
            
            return ServiceResult.successMessage("Client deleted successfully.");
            
        } catch (DataIntegrityViolationException e) {
            return ServiceResult.errorMessage("Cannot delete client that was part of repair history.");
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while deleting client: " + e.getMessage());
        }
    }
    
    public ServiceResult getClientWatches(Long clientId) {
        try {
            Client client = clientRepo.findById(clientId)
                    .orElseThrow(() -> new EntityNotFoundException("Client not found."));
            
            List<Watch> watches = watchRepo.findByIdClient(client);
            List<WatchDTO> watchesDTO = WatchMapper.toDTOList(watches);
            
            return ServiceResult.successMessageData(
                "Client's watches loaded.", 
                watchesDTO
            );
            
        } catch (EntityNotFoundException e) {
            return ServiceResult.errorMessage(e.getMessage());
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while fetching watches: " + e.getMessage());
        }
    }
}
