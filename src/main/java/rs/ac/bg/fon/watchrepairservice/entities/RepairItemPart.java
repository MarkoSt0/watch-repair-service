/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.*;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "repair_item_part")
@NamedQueries({
    @NamedQuery(name = "RepairItemPart.findAll", query = "SELECT r FROM RepairItemPart r"),
    @NamedQuery(name = "RepairItemPart.findByIdRepairItemPart", query = "SELECT r FROM RepairItemPart r WHERE r.idRepairItemPart = :idRepairItemPart"),
    @NamedQuery(name = "RepairItemPart.findByBrandName", query = "SELECT r FROM RepairItemPart r WHERE r.brandName = :brandName"),
    @NamedQuery(name = "RepairItemPart.findByQuantity", query = "SELECT r FROM RepairItemPart r WHERE r.quantity = :quantity"),
    @NamedQuery(name = "RepairItemPart.findByCost", query = "SELECT r FROM RepairItemPart r WHERE r.cost = :cost")})
public class RepairItemPart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_repair_item_part")
    private Long idRepairItemPart;
    @Column(name = "brand_name")
    private String brandName;
    @Column(name = "quantity")
    private Integer quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "cost")
    private BigDecimal cost;
    @JoinColumn(name = "id_part", referencedColumnName = "id_part")
    @ManyToOne(optional = false)
    private Part idPart;
    @JoinColumn(name = "id_repair_item", referencedColumnName = "id_repair_item")
    @ManyToOne(optional = false)
    private RepairItem idRepairItem;

    public RepairItemPart() {
    }

    public RepairItemPart(Long idRepairItemPart) {
        this.idRepairItemPart = idRepairItemPart;
    }

    public RepairItemPart(Long idRepairItemPart, BigDecimal cost) {
        this.idRepairItemPart = idRepairItemPart;
        this.cost = cost;
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

    public Part getIdPart() {
        return idPart;
    }

    public void setIdPart(Part idPart) {
        this.idPart = idPart;
    }

    public RepairItem getIdRepairItem() {
        return idRepairItem;
    }

    public void setIdRepairItem(RepairItem idRepairItem) {
        this.idRepairItem = idRepairItem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRepairItemPart != null ? idRepairItemPart.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RepairItemPart)) {
            return false;
        }
        RepairItemPart other = (RepairItemPart) object;
        if ((this.idRepairItemPart == null && other.idRepairItemPart != null) || (this.idRepairItemPart != null && !this.idRepairItemPart.equals(other.idRepairItemPart))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.ac.bg.fon.watchrepairservice.models.RepairItemPart[ idRepairItemPart=" + idRepairItemPart + " ]";
    }
    
}
