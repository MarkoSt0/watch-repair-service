/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.watchrepairservice.communication.ServiceResult;
import rs.ac.bg.fon.watchrepairservice.dto.PartDTO;
import rs.ac.bg.fon.watchrepairservice.entity.Part;
import rs.ac.bg.fon.watchrepairservice.mapper.PartMapper;
import rs.ac.bg.fon.watchrepairservice.repository.PartRepository;

/**
 *
 * @author Marko
 */
@Service
@Transactional
public class PartService {
    private final PartRepository partRepo;

    public PartService(PartRepository partRepo) {
        this.partRepo = partRepo;
    }
    
    public ServiceResult addPart(PartDTO dto){
        try {
            if (!PartMapper.isValidDTO(dto)) {
                return ServiceResult.errorMessage("Invalid part data. Name is required.");
            }
            if (partRepo.existsByName(dto.getName())) {
                return ServiceResult.errorMessage("Part with this name already exists.");
            }
            Part part = partRepo.save(PartMapper.toEntity(dto));
            return ServiceResult.successMessageData(
                    "Part added successuflly.", 
                    PartMapper.toDTO(part)
            );
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while adding part: " + e.getMessage());
        }
    }
    
    public ServiceResult updatePart(PartDTO dto){
        Part existing = partRepo.findById(dto.getIdPart())
                .orElseThrow(() -> new EntityNotFoundException("Part not found."));
        if (!PartMapper.isValidDTO(dto)) {
            return ServiceResult.errorMessage("Invalid part data. Name is required.");
        }
        if (!dto.getName().equals(existing.getName()) && 
            partRepo.existsByName(dto.getName())) {
            return ServiceResult.errorMessage("Part with this name already exists.");
        }
        try {
            PartMapper.updateEntityFromDTO(dto, existing);
            Part updated = partRepo.save(existing);
            return ServiceResult.successMessageData(
                    "Part successfully updated.", 
                    PartMapper.toDTO(updated)
            );
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while updating part" + e.getMessage());
        }
    }
    
    public ServiceResult getPart(Long id){
        try {
            Part part = partRepo.findById(id).orElseThrow(() ->
                    new EntityNotFoundException("Part not found."));
            return ServiceResult.successMessageData(
                    "Part found.", 
                    PartMapper.toDTO(part)
            );
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while fetching part: " + e.getMessage());
        }
        
    }
    
    public ServiceResult getPartByName(String name){
        try {
            Part part = partRepo.findByName(name).orElseThrow(() ->
                    new EntityNotFoundException("Part not found."));
            return ServiceResult.successMessageData(
                    "Part found.", 
                    PartMapper.toDTO(part)
            );            
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while fetching part: " + e.getMessage());

        }
    }
    
    public ServiceResult deletePartById(Long id){
        partRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Part not found."));
        try {
            partRepo.deleteById(id);
            return ServiceResult.successMessage("Part deleted.");
        } catch (DataIntegrityViolationException e) {
            return ServiceResult.errorMessage("Cannot delete part that is used in repairs.");
        }catch (Exception e) {
            return ServiceResult.errorMessage("Part not deleted");
        }
    }
    
    public ServiceResult getAllParts(){
        List<PartDTO> citiesDTO = partRepo.findAll().stream().map(PartMapper::toDTO).collect(Collectors.toList());
        return ServiceResult.successMessageData("All parts loaded.", citiesDTO);
    }
}
