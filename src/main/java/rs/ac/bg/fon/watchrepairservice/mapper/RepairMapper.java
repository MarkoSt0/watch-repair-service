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
import rs.ac.bg.fon.watchrepairservice.dto.RepairDTO;
import rs.ac.bg.fon.watchrepairservice.dto.RepairItemDTO;
import rs.ac.bg.fon.watchrepairservice.entity.Repair;
import rs.ac.bg.fon.watchrepairservice.enums.Status;

/**
 *
 * @author Marko
 */
@Component
public class RepairMapper {
    public static RepairDTO toDTO(Repair entity) {
        if (entity == null) {
            return null;
        }

        RepairDTO dto = new RepairDTO();
        dto.setIdRepair(entity.getIdRepair());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setTotalCost(entity.getTotalCost());
        dto.setStatus(entity.getStatus());
        
        dto.setIdClient(entity.getIdClient() != null ? 
            ClientMapper.toDTO(entity.getIdClient()) : null);
        dto.setIdEmployee(entity.getIdEmployee() != null ? 
            EmployeeMapper.toDTO(entity.getIdEmployee()) : null);
        
        dto.setRepairItemCollection(null);
        
        return dto;
    }
    
    public static Repair toEntity(RepairDTO dto) {
        if (dto == null) {
            return null;
        }

        Repair entity = new Repair();
        entity.setCreatedAt(dto.getCreatedAt() != null ? 
            dto.getCreatedAt() : LocalDate.now());
        entity.setTotalCost(dto.getTotalCost() != null ? 
            dto.getTotalCost() : BigDecimal.ZERO);
        entity.setStatus(dto.getStatus() != null ? 
            dto.getStatus() : Status.RECEIVED);
        
        return entity;
    }
    
    public static void updateEntityFromDTO(RepairDTO dto, Repair entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setTotalCost(dto.getTotalCost());
        entity.setStatus(dto.getStatus());
    }
    
    public static List<RepairDTO> toDTOList(List<Repair> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(RepairMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public static RepairDTO toDTOWithItems(Repair entity) {
        if (entity == null) {
            return null;
        }

        RepairDTO dto = new RepairDTO();
        dto.setIdRepair(entity.getIdRepair());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setTotalCost(entity.getTotalCost());
        dto.setStatus(entity.getStatus());
        
        dto.setIdClient(entity.getIdClient() != null ? 
            ClientMapper.toDTO(entity.getIdClient()) : null);
        dto.setIdEmployee(entity.getIdEmployee() != null ? 
            EmployeeMapper.toDTO(entity.getIdEmployee()) : null);
        
        if (entity.getRepairItemCollection() != null) {
            List<RepairItemDTO> items = 
                    entity.getRepairItemCollection().stream()
                    .map(RepairItemMapper::toDTO)
                    .collect(Collectors.toList());
            dto.setRepairItemCollection(items);
        } else {
            dto.setRepairItemCollection(Collections.emptyList());
        }
        
        return dto;
    }
}
