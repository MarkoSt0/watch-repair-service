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
public class RepairItemPartDTO {
    private Long idRepairItemPart;
    private String brandName;
    private Integer quantity;
    private BigDecimal cost;
    private PartDTO part;
    private Long idRepairItem;

    public RepairItemPartDTO() {
    }

    public RepairItemPartDTO(Long idRepairItemPart, String brandName, Integer quantity, BigDecimal cost, PartDTO part, Long idRepairItem) {
        this.idRepairItemPart = idRepairItemPart;
        this.brandName = brandName;
        this.quantity = quantity;
        this.cost = cost;
        this.part = part;
        this.idRepairItem = idRepairItem;
    }

    public Long getIdRepairItemPart() {
        return idRepairItemPart;
    }

    public void setIdRepairItemPart(Long idRepairItemPart) {
        this.idRepairItemPart = idRepairItemPart;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public PartDTO getPart() {
        return part;
    }

    public void setPart(PartDTO part) {
        this.part = part;
    }

    public Long getIdRepairItem() {
        return idRepairItem;
    }

    public void setIdRepairItem(Long idRepairItem) {
        this.idRepairItem = idRepairItem;
    }
    
    
    
}
