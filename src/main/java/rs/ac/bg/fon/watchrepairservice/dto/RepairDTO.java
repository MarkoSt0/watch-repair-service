/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import rs.ac.bg.fon.watchrepairservice.enums.Status;

/**
 *
 * @author Marko
 */
public class RepairDTO {
    private Long idRepair;
    private LocalDate createdAt;
    private BigDecimal totalCost;
    private Status status;
    private ClientDTO idClient;
    private EmployeeDTO idEmployee;
    private List<RepairItemDTO> repairItemCollection;

    public RepairDTO() {
    }

    public RepairDTO(Long idRepair, LocalDate createdAt, BigDecimal totalCost, Status status, ClientDTO idClient, EmployeeDTO idEmployee, List<RepairItemDTO> repairItemCollection) {
        this.idRepair = idRepair;
        this.createdAt = createdAt;
        this.totalCost = totalCost;
        this.status = status;
        this.idClient = idClient;
        this.idEmployee = idEmployee;
        this.repairItemCollection = repairItemCollection;
    }

    public Long getIdRepair() {
        return idRepair;
    }

    public void setIdRepair(Long idRepair) {
        this.idRepair = idRepair;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ClientDTO getIdClient() {
        return idClient;
    }

    public void setIdClient(ClientDTO idClient) {
        this.idClient = idClient;
    }

    public EmployeeDTO getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(EmployeeDTO idEmployee) {
        this.idEmployee = idEmployee;
    }

    public List<RepairItemDTO> getRepairItemCollection() {
        return repairItemCollection;
    }

    public void setRepairItemCollection(List<RepairItemDTO> repairItemCollection) {
        this.repairItemCollection = repairItemCollection;
    }
    
    
}
