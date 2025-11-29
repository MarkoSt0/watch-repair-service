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
import rs.ac.bg.fon.watchrepairservice.dto.WatchDTO;
import rs.ac.bg.fon.watchrepairservice.entity.Client;

/**
 *
 * @author Marko
 */
@Component
public class ClientMapper {
    //Entity -> DTO method
    public static ClientDTO toDTO(Client entity) {
        if (entity == null) {return null;}

        ClientDTO dto = new ClientDTO();
        dto.setIdClient(entity.getIdClient());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhone(entity.getPhone());
        dto.setWatchCollection(null); //for now
        
        return dto;
    }
    
    //DTO -> Entity method, idClient is used in Service
    public static Client toEntity(ClientDTO dto) {
        if (dto == null) {return null;}

        Client entity = new Client();
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhone(dto.getPhone());
        
        return entity;
    }
    
    // Update mapper, all collections are connected in Service
    public static void updateEntityFromDTO(ClientDTO dto, Client entity) {
        if (dto == null || entity == null) {return;}

        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhone(dto.getPhone());
    }
    
    // List of watches is transf. into list of DTOs
    public static List<ClientDTO> toDTOList(List<Client> entities) {
        if (entities == null) {return Collections.emptyList();}

        return entities.stream()
                .map(ClientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static boolean isValidDTO(ClientDTO dto) {
        return dto.getEmail() != null && dto.getFirstName() != null &&
                dto.getLastName() != null && dto.getPhone() != null &&
                !dto.getEmail().trim().isEmpty() && !dto.getFirstName().trim().isEmpty() &&
                dto.getLastName().trim().isEmpty() && !dto.getPhone().trim().isEmpty();
    }
    
    public static ClientDTO toDTOWithWatches(Client entity){
        if (entity == null) {return null;}

        ClientDTO dto = new ClientDTO();
        dto.setIdClient(entity.getIdClient());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhone(entity.getPhone());
        dto.setWatchCollection(WatchMapper.toDTOList(entity.getWatchCollection().stream().toList()));
        
        return dto;
    }
}
