/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.watchrepairservice.communication.ApiResponse;
import rs.ac.bg.fon.watchrepairservice.dto.ClientDTO;
import rs.ac.bg.fon.watchrepairservice.repository.ClientRepository;

/**
 *
 * @author Marko
 */
@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientRepository clientRepo;

    public ClientController(ClientRepository clientRepo) {
        this.clientRepo = clientRepo;
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse> addClient(ClientDTO dto){
        return null;
    }
}
