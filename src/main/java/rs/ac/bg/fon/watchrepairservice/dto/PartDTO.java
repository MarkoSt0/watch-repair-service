/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.dto;

import java.math.BigDecimal;

/**
 *
 * @author Marko
 */
public class PartDTO {
    private Long idPart;
    private String name;
    private BigDecimal currentPrice;

    public PartDTO() {
    }

    public PartDTO(Long idPart, String name, BigDecimal currentPrice) {
        this.idPart = idPart;
        this.name = name;
        this.currentPrice = currentPrice;
    }
    
    public Long getIdPart() {
        return idPart;
    }

    public void setIdPart(Long idPart) {
        this.idPart = idPart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
    
}
