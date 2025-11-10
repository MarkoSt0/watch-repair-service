/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.*;
import rs.ac.bg.fon.watchrepairservice.enums.ItemStatus;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "repair_item")
@NamedQueries({
    @NamedQuery(name = "RepairItem.findAll", query = "SELECT r FROM RepairItem r"),
    @NamedQuery(name = "RepairItem.findByIdRepairItem", query = "SELECT r FROM RepairItem r WHERE r.idRepairItem = :idRepairItem"),
    @NamedQuery(name = "RepairItem.findByPartsCost", query = "SELECT r FROM RepairItem r WHERE r.partsCost = :partsCost"),
    @NamedQuery(name = "RepairItem.findByItemStatus", query = "SELECT r FROM RepairItem r WHERE r.itemStatus = :itemStatus"),
    @NamedQuery(name = "RepairItem.findByCompletedAt", query = "SELECT r FROM RepairItem r WHERE r.completedAt = :completedAt")})
public class RepairItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_repair_item")
    private Long idRepairItem;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "parts_cost")
    private BigDecimal partsCost;
    @Column(name = "item_status")
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;
    @Column(name = "completed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date completedAt;
    @JoinColumn(name = "id_repair", referencedColumnName = "id_repair")
    @ManyToOne(optional = false)
    private Repair idRepair;
    @JoinColumn(name = "id_watch", referencedColumnName = "id_watch")
    @ManyToOne(optional = false)
    private Watch idWatch;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRepairItem")
    private Collection<RepairItemPart> repairItemPartCollection;

    public RepairItem() {
    }

    public RepairItem(Long idRepairItem) {
        this.idRepairItem = idRepairItem;
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

    public ItemStatus getRepairStatus() {
        return itemStatus;
    }

    public void setRepairStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public Repair getIdRepair() {
        return idRepair;
    }

    public void setIdRepair(Repair idRepair) {
        this.idRepair = idRepair;
    }

    public Watch getIdWatch() {
        return idWatch;
    }

    public void setIdWatch(Watch idWatch) {
        this.idWatch = idWatch;
    }

    public Collection<RepairItemPart> getRepairItemPartCollection() {
        return repairItemPartCollection;
    }

    public void setRepairItemPartCollection(Collection<RepairItemPart> repairItemPartCollection) {
        this.repairItemPartCollection = repairItemPartCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRepairItem != null ? idRepairItem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RepairItem)) {
            return false;
        }
        RepairItem other = (RepairItem) object;
        if ((this.idRepairItem == null && other.idRepairItem != null) || (this.idRepairItem != null && !this.idRepairItem.equals(other.idRepairItem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.ac.bg.fon.watchrepairservice.models.RepairItem[ idRepairItem=" + idRepairItem + " ]";
    }
    
}
