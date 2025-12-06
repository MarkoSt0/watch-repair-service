/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.watchrepairservice.communication.ApiResponse;
import rs.ac.bg.fon.watchrepairservice.dto.PartDTO;
import rs.ac.bg.fon.watchrepairservice.service.PartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Marko
 */
@RestController
@RequestMapping("/parts")
public class PartController {
    private final PartService partService;

    public PartController(PartService partService) {
        this.partService = partService;
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse> addPart(@RequestBody PartDTO dto){
        PartDTO savedPart = partService.addPart(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse(true, "Part added successufully.", savedPart)
        );
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse> getAllParts(){
        List<PartDTO> partDTOs = partService.getAllParts();
        return ResponseEntity.ok(
                new ApiResponse(true, "All parts loaded.", partDTOs)
        );
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePart(@PathVariable Long id, @RequestBody PartDTO dto){
        PartDTO updatedPartDTO = partService.updatePart(id, dto);
        return ResponseEntity.ok(
                new ApiResponse(true, "Part successfully updated.", updatedPartDTO)
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPartById(@PathVariable Long id) {
        PartDTO part = partService.getPart(id);
        return ResponseEntity.ok(
            new ApiResponse(true, "Part retrieved successfully.", part)
        );
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchPartByName(@RequestParam String name) {
        PartDTO part = partService.getPartByName(name);
        return ResponseEntity.ok(
            new ApiResponse(true, "Part retrieved successfully.", part)
        );
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePart(@PathVariable Long id){
        partService.deletePartById(id);
        return ResponseEntity.ok(
            new ApiResponse(true, "Part deleted successfully.", null)
        );
    }
}
