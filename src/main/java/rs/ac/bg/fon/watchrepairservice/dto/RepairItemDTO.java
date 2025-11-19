/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import rs.ac.bg.fon.watchrepairservice.enums.ItemStatus;

/**
 *
 * @author Marko
 */
public class RepairItemDTO {
    private Long idRepairItem;
    private BigDecimal partsCost;
    private ItemStatus itemStatus;
    private LocalDate completedAt;
    private Long idRepair;
    private WatchDTO watch;
    private List<RepairItemPartDTO> repairItemPartCollection;

    public RepairItemDTO() {
    }

    public RepairItemDTO(Long idRepairItem, BigDecimal partsCost, ItemStatus itemStatus, LocalDate completedAt, Long idRepair, WatchDTO idWatch, List<RepairItemPartDTO> repairItemPartCollection) {
        this.idRepairItem = idRepairItem;
        this.partsCost = partsCost;
        this.itemStatus = itemStatus;
        this.completedAt = completedAt;
        this.idRepair = idRepair;
        this.watch = idWatch;
        this.repairItemPartCollection = repairItemPartCollection;
    }

    public Long getIdRepairItem() {
        return idRepairItem;
    }

    public void setIdRepairItem(Long idRepairItem) {
        this.idRepairItem = idRepairItem;
    }

    public BigDecimal getPartsCost() {
        return partsCost;
    }

    public void setPartsCost(BigDecimal partsCost) {
        this.partsCost = partsCost;
    }

    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    public LocalDate getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDate completedAt) {
        this.completedAt = completedAt;
    }

    public Long getIdRepair() {
        return idRepair;
    }

    public void setIdRepair(Long idRepair) {
        this.idRepair = idRepair;
    }

    public WatchDTO getWatch() {
        return watch;
    }

    public void setWatch(WatchDTO watch) {
        this.watch = watch;
    }

    public List<RepairItemPartDTO> getRepairItemPartCollection() {
        return repairItemPartCollection;
    }

    public void setRepairItemPartCollection(List<RepairItemPartDTO> repairItemPartCollection) {
        this.repairItemPartCollection = repairItemPartCollection;
    }
    
}
