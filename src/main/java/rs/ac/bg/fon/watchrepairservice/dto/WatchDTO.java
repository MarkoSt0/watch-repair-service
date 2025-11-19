/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.dto;

import java.util.List;
import rs.ac.bg.fon.watchrepairservice.enums.CrystalMaterial;
import rs.ac.bg.fon.watchrepairservice.enums.Movement;

/**
 *
 * @author Marko
 */
public class WatchDTO {
    private Long idWatch;
    private String brand;
    private String model;
    private String caseMaterial;
    private String dialColor;
    private CrystalMaterial crystalMaterial;
    private Movement movement;
    private Long idClient;

    public WatchDTO() {
    }

    public WatchDTO(Long idWatch, String brand, String model, String caseMaterial, String dialColor, CrystalMaterial crystalMaterial, Movement movement, Long idClient) {
        this.idWatch = idWatch;
        this.brand = brand;
        this.model = model;
        this.caseMaterial = caseMaterial;
        this.dialColor = dialColor;
        this.crystalMaterial = crystalMaterial;
        this.movement = movement;
        this.idClient = idClient;
    }

    public Long getIdWatch() {
        return idWatch;
    }

    public void setIdWatch(Long idWatch) {
        this.idWatch = idWatch;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCaseMaterial() {
        return caseMaterial;
    }

    public void setCaseMaterial(String caseMaterial) {
        this.caseMaterial = caseMaterial;
    }

    public String getDialColor() {
        return dialColor;
    }

    public void setDialColor(String dialColor) {
        this.dialColor = dialColor;
    }

    public CrystalMaterial getCrystalMaterial() {
        return crystalMaterial;
    }

    public void setCrystalMaterial(CrystalMaterial crystalMaterial) {
        this.crystalMaterial = crystalMaterial;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    
    
    
}
