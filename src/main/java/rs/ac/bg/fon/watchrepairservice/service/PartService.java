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
import rs.ac.bg.fon.watchrepairservice.exception.ResourceInUseException;
import rs.ac.bg.fon.watchrepairservice.exception.InvalidDataException;
import rs.ac.bg.fon.watchrepairservice.exception.ResourceAlreadyExistsException;
import rs.ac.bg.fon.watchrepairservice.exception.ResourceNotFoundException;
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
    
    public PartDTO addPart(PartDTO dto){
            validatePartDTO(dto);
            
            if (partRepo.existsByName(dto.getName())) {
                throw new ResourceAlreadyExistsException("Part with name " + dto.getName() + "already exists.");
            }
            Part part = partRepo.save(PartMapper.toEntity(dto));
            
            return PartMapper.toDTO(part);
        
    }
    
    public PartDTO updatePart(Long id, PartDTO dto){
        Part existing = partRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Part with id " + id + " not found."));
        
        validatePartDTO(dto);
        
        if (!dto.getName().equals(existing.getName()) && 
            partRepo.existsByName(dto.getName())) {
            throw new ResourceAlreadyExistsException("Part with name " + dto.getName() + " already exists.");
        }
        
        PartMapper.updateEntityFromDTO(dto, existing);
        Part updated = partRepo.save(existing);
        return PartMapper.toDTO(updated);
        
    }
    
    public PartDTO getPart(Long id){
        Part part = partRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Part with id " + id + " not found."));
        return PartMapper.toDTO(part);
        
    }
    
    public PartDTO getPartByName(String name){
        Part part = partRepo.findByName(name).orElseThrow(() ->
                new ResourceNotFoundException("Part with name " + name + " not found."));
        return PartMapper.toDTO(part);            
        
    }
    
    public void deletePartById(Long id){
        //This part of code needs change.
        //If part is part of ACTIVE repair i should restrict delete operation, in other
        //cases NO!
        
        partRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Part with id " + id + " not found."));
        try {
            partRepo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceInUseException("Cannot delete part that is used in repairs.");
        }
    }
    
    public List<PartDTO> getAllParts(){
        return partRepo.findAll().stream().map(PartMapper::toDTO).collect(Collectors.toList());
        
    }
    
    private void validatePartDTO(PartDTO dto){
        if (!PartMapper.isValidDTO(dto)) {
            throw new InvalidDataException("Invalid part data. Name and price are required.");
        }
    }
}
