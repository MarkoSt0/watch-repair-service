/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.watchrepairservice.dto.ClientDTO;
import rs.ac.bg.fon.watchrepairservice.dto.EmployeeDTO;
import rs.ac.bg.fon.watchrepairservice.entity.Employee;

/**
 *
 * @author Marko
 */
@Component
public class EmployeeMapper {
    
    public static EmployeeDTO toDTO(Employee entity) {
        if (entity == null) {
            return null;
        }

        EmployeeDTO dto = new EmployeeDTO();
        dto.setIdEmployee(entity.getIdEmployee());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setUsername(entity.getUsername());
        dto.setPassword(null); // NO PASSWORD!!!
        dto.setRole(entity.getRole());
        
        return dto;
    }
    
    public static EmployeeDTO toDTOWithPassword(Employee entity) {
        if (entity == null) {
            return null;
        }

        EmployeeDTO dto = new EmployeeDTO();
        dto.setIdEmployee(entity.getIdEmployee());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword()); // auth
        dto.setRole(entity.getRole());
        
        return dto;
    }
    
    public static Employee toEntity(EmployeeDTO dto) {
        if (dto == null) {
            return null;
        }

        Employee entity = new Employee();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword()); // Hash in service later
        entity.setRole(dto.getRole());
        
        return entity;
    }
    
    public static void updateEntityFromDTO(EmployeeDTO dto, Employee entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setUsername(dto.getUsername());
        entity.setRole(dto.getRole());
    }
    
    //This method is part of updateEntityFromDTO, reason for separation of methods
    //is in security, password needs to be HASHED before method is called
    public static void updatePassword(Employee entity, String newPassword) {
        if (entity == null || newPassword == null) {
            return;
        }
        entity.setPassword(newPassword);
    }
    
    public static List<EmployeeDTO> toDTOList(List<Employee> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static boolean isValidDTO(EmployeeDTO dto) {
        return dto.getUsername()!= null && dto.getFirstName() != null &&
                dto.getLastName() != null && dto.getPassword()!= null &&
                !dto.getUsername().trim().isEmpty() && !dto.getFirstName().trim().isEmpty() &&
                !dto.getLastName().trim().isEmpty() && !dto.getPassword().trim().isEmpty();
    }
}
