/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.watchrepairservice.dto.PartDTO;
import rs.ac.bg.fon.watchrepairservice.entity.Part;

/**
 *
 * @author Marko
 */
@Component
public class PartMapper {
    public static PartDTO toDTO(Part entity) {
        if (entity == null) {
            return null;
        }

        return new PartDTO(
            entity.getIdPart(),
            entity.getName(),
            entity.getCurrentPrice()
        );
    }
    
    public static Part toEntity(PartDTO dto) {
        if (dto == null) {
            return null;
        }

        Part entity = new Part();
        
        entity.setName(dto.getName());
        entity.setCurrentPrice(dto.getCurrentPrice());
        
        return entity;
    }
    
    public static void updateEntityFromDTO(PartDTO dto, Part entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setName(dto.getName());
        entity.setCurrentPrice(dto.getCurrentPrice());
    }
    
    public static List<PartDTO> toDTOList(List<Part> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(PartMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    // For basicDTO, validation of data, add new methods
    
    public static boolean isValidDTO(PartDTO dto){
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
