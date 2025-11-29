/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.watchrepairservice.dto.WatchDTO;
import rs.ac.bg.fon.watchrepairservice.entity.Watch;

/**
 *
 * @author Marko
 */
@Component
public class WatchMapper {
    //Entity -> DTO method
    public static WatchDTO toDTO(Watch entity) {
        if (entity == null) { return null; }
        
        return new WatchDTO(
            entity.getIdWatch(),
            entity.getBrand(),
            entity.getModel(),
            entity.getCaseMaterial(),
            entity.getDialColor(),
            entity.getCrystalMaterial(),
            entity.getMovement(),
            entity.getIdClient() != null ? entity.getIdClient().getIdClient() : null
        );
    }
    
    //DTO -> Entity method, idClient is used in Service
    public static Watch toEntity(WatchDTO dto) {
        if (dto == null) { return null; }
        
        Watch entity = new Watch();
        entity.setIdWatch(dto.getIdWatch());
        entity.setBrand(dto.getBrand());
        entity.setModel(dto.getModel());
        entity.setCaseMaterial(dto.getCaseMaterial());
        entity.setDialColor(dto.getDialColor());
        entity.setCrystalMaterial(dto.getCrystalMaterial());
        entity.setMovement(dto.getMovement());
        
        return entity;
    }
    
    // Update mapper, idClient is used in Service
    public static void updateEntityFromDTO(WatchDTO dto, Watch entity) {
        if (dto == null || entity == null) { return; }

        entity.setBrand(dto.getBrand());
        entity.setModel(dto.getModel());
        entity.setCaseMaterial(dto.getCaseMaterial());
        entity.setDialColor(dto.getDialColor());
        entity.setCrystalMaterial(dto.getCrystalMaterial());
        entity.setMovement(dto.getMovement());
    }
    
    // List of watches is transf. into list of DTOs
    public static List<WatchDTO> toDTOList(List<Watch> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(WatchMapper::toDTO)
                .collect(Collectors.toList());
    }
    
//    ...in future add helper class with information about watch with content like:
//    brand, model...

    public static boolean isValidDTO(WatchDTO dto) {
        if(dto.getBrand() == null || dto.getIdClient() == null ||
                dto.getBrand().trim().isEmpty() || dto.getIdClient() < 1)
            return false;
        return true;
    }
    
}
