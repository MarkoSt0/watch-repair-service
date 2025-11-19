/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.mapper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.watchrepairservice.dto.RepairItemPartDTO;
import rs.ac.bg.fon.watchrepairservice.entity.RepairItemPart;

/**
 *
 * @author Marko
 */
@Component
public class RepairItemPartMapper {
    public static RepairItemPartDTO toDTO(RepairItemPart entity) {
        if (entity == null) {
            return null;
        }

        RepairItemPartDTO dto = new RepairItemPartDTO();
        dto.setIdRepairItemPart(entity.getIdRepairItemPart());
        dto.setBrandName(entity.getBrandName());
        dto.setQuantity(entity.getQuantity());
        dto.setCost(entity.getCost());
        
        dto.setPart(entity.getIdPart() != null ? 
            PartMapper.toDTO(entity.getIdPart()) : null);
        dto.setIdRepairItem(entity.getIdRepairItem() != null ? 
            entity.getIdRepairItem().getIdRepairItem() : null);
        
        return dto;
    }
    
    public static RepairItemPart toEntity(RepairItemPartDTO dto) {
        if (dto == null) {
            return null;
        }

        RepairItemPart entity = new RepairItemPart();
        entity.setBrandName(dto.getBrandName());
        entity.setQuantity(dto.getQuantity() != null ? dto.getQuantity() : 1); // Default 1
        entity.setCost(dto.getCost() != null ? dto.getCost() : BigDecimal.ZERO);
        
        
        return entity;
    }
    
    public static void updateEntityFromDTO(RepairItemPartDTO dto, RepairItemPart entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setBrandName(dto.getBrandName());
        entity.setQuantity(dto.getQuantity());
        entity.setCost(dto.getCost());
    }
    
    public static List<RepairItemPartDTO> toDTOList(List<RepairItemPart> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(RepairItemPartMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    
    public static RepairItemPartDTO toDTOWithPartDetails(RepairItemPart entity) {
        if (entity == null) {
            return null;
        }

        RepairItemPartDTO dto = new RepairItemPartDTO();
        dto.setIdRepairItemPart(entity.getIdRepairItemPart());
        dto.setBrandName(entity.getBrandName());
        dto.setQuantity(entity.getQuantity());
        dto.setCost(entity.getCost());
        
        dto.setPart(entity.getIdPart() != null ? 
            PartMapper.toDTO(entity.getIdPart()) : null);
        dto.setIdRepairItem(entity.getIdRepairItem() != null ? 
            entity.getIdRepairItem().getIdRepairItem() : null);
        
        return dto;
    }
    
    // Helper method for calculation of total cost
    public static BigDecimal calculateTotalCost(RepairItemPartDTO dto) {
        if (dto == null || dto.getQuantity() == null || dto.getCost() == null) {
            return BigDecimal.ZERO;
        }
        
        return new BigDecimal(dto.getQuantity()).multiply(dto.getCost());
    }
}
