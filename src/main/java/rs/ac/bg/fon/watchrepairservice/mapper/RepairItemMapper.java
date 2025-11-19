/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.watchrepairservice.dto.RepairItemDTO;
import rs.ac.bg.fon.watchrepairservice.dto.RepairItemPartDTO;
import rs.ac.bg.fon.watchrepairservice.entity.RepairItem;
import rs.ac.bg.fon.watchrepairservice.enums.ItemStatus;

/**
 *
 * @author Marko
 */
@Component
public class RepairItemMapper {
    public static RepairItemDTO toDTO(RepairItem entity) {
        if (entity == null) {
            return null;
        }

        RepairItemDTO dto = new RepairItemDTO();
        dto.setIdRepairItem(entity.getIdRepairItem());
        dto.setPartsCost(entity.getPartsCost());
        dto.setItemStatus(entity.getItemStatus());
        dto.setCompletedAt(entity.getCompletedAt());
        dto.setIdRepair(entity.getIdRepair() != null ? 
            entity.getIdRepair().getIdRepair() : null);
        dto.setWatch(entity.getIdWatch() != null ? 
            WatchMapper.toDTO(entity.getIdWatch()) : null);
        dto.setRepairItemPartCollection(null);
        
        return dto;
    }
    
    // Note: in this solution i need to set idRepair and idWatch in service
    // repairItemPartCollection needs to be added after creation
    public static RepairItem toEntity(RepairItemDTO dto) {
        if (dto == null) {
            return null;
        }

        RepairItem entity = new RepairItem();
        entity.setPartsCost(dto.getPartsCost() != null ? 
            dto.getPartsCost() : BigDecimal.ZERO);
        entity.setItemStatus(dto.getItemStatus() != null ? 
            dto.getItemStatus() : ItemStatus.PENDING);
        entity.setCompletedAt(dto.getCompletedAt());
        
        return entity;
    }
    
    // No update for repair and watch after creation
    public static void updateEntityFromDTO(RepairItemDTO dto, RepairItem entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setPartsCost(dto.getPartsCost());
        entity.setItemStatus(dto.getItemStatus());
        entity.setCompletedAt(dto.getCompletedAt());
        
    }
    
    public static List<RepairItemDTO> toDTOList(List<RepairItem> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(RepairItemMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    // This is method for getting all data like before + repairItemPartCOllection
    // for presentation purpose (for now)
    public static RepairItemDTO toDTOWithParts(RepairItem entity) {
        if (entity == null) {
            return null;
        }

        RepairItemDTO dto = new RepairItemDTO();
        dto.setIdRepairItem(entity.getIdRepairItem());
        dto.setPartsCost(entity.getPartsCost());
        dto.setItemStatus(entity.getItemStatus());
        dto.setCompletedAt(entity.getCompletedAt());
        
        dto.setIdRepair(entity.getIdRepair() != null ? 
            entity.getIdRepair().getIdRepair() : null);
        dto.setWatch(entity.getIdWatch() != null ? 
            WatchMapper.toDTO(entity.getIdWatch()) : null);
        
        //Collection part
        if (entity.getRepairItemPartCollection() != null) {
            List<RepairItemPartDTO> parts = entity.getRepairItemPartCollection().stream()
                    .map(RepairItemPartMapper::toDTO)
                    .collect(Collectors.toList());
            dto.setRepairItemPartCollection(parts);
        } else {
            dto.setRepairItemPartCollection(Collections.emptyList());
        }
        
        return dto;
    }
    
    
//    HELPER METHODS
    
    public static BigDecimal calculatePartsCost(List<RepairItemPartDTO> repairItemParts) {
        if (repairItemParts == null || repairItemParts.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return repairItemParts.stream()
                .map(part -> {
                    if (part.getQuantity() != null && part.getCost() != null) {
                        return new BigDecimal(part.getQuantity())
                                .multiply(part.getCost());
                    }
                    return BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public static void markAsCompleted(RepairItem entity) {
        if (entity == null) {
            return;
        }
        entity.setItemStatus(ItemStatus.COMPLETED);
        entity.setCompletedAt(LocalDate.now());
    }

    // BasicDTO inner class may be added later.
    
}
