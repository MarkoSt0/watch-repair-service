/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.entity;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.*;
import rs.ac.bg.fon.watchrepairservice.enums.CrystalMaterial;
import rs.ac.bg.fon.watchrepairservice.enums.Movement;
/**
 *
 * @author Marko
 */
@Entity
@Table(name = "watch")
@NamedQueries({
    @NamedQuery(name = "Watch.findAll", query = "SELECT w FROM Watch w"),
    @NamedQuery(name = "Watch.findByIdWatch", query = "SELECT w FROM Watch w WHERE w.idWatch = :idWatch"),
    @NamedQuery(name = "Watch.findByBrand", query = "SELECT w FROM Watch w WHERE w.brand = :brand"),
    @NamedQuery(name = "Watch.findByModel", query = "SELECT w FROM Watch w WHERE w.model = :model"),
    @NamedQuery(name = "Watch.findByCaseMaterial", query = "SELECT w FROM Watch w WHERE w.caseMaterial = :caseMaterial"),
    @NamedQuery(name = "Watch.findByDialColor", query = "SELECT w FROM Watch w WHERE w.dialColor = :dialColor"),
    @NamedQuery(name = "Watch.findByCrystalMaterial", query = "SELECT w FROM Watch w WHERE w.crystalMaterial = :crystalMaterial"),
    @NamedQuery(name = "Watch.findByMovement", query = "SELECT w FROM Watch w WHERE w.movement = :movement")})
public class Watch implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_watch")
    private Long idWatch;
    @Basic(optional = false)
    @Column(name = "brand")
    private String brand;
    @Column(name = "model")
    private String model;
    @Column(name = "case_material")
    private String caseMaterial;
    @Column(name = "dial_color")
    private String dialColor;
    @Column(name = "crystal_material")
    @Enumerated(EnumType.STRING)
    private CrystalMaterial crystalMaterial;
    @Column(name = "movement")
    @Enumerated(EnumType.STRING)
    private Movement movement;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idWatch")
    private Collection<RepairItem> repairItemCollection;
    @JoinColumn(name = "id_client", referencedColumnName = "id_client")
    @ManyToOne(optional = false)
    private Client idClient;

    public Watch() {
    }

    public Watch(Long idWatch) {
        this.idWatch = idWatch;
    }

    public Watch(Long idWatch, String brand) {
        this.idWatch = idWatch;
        this.brand = brand;
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

    public Collection<RepairItem> getRepairItemCollection() {
        return repairItemCollection;
    }

    public void setRepairItemCollection(Collection<RepairItem> repairItemCollection) {
        this.repairItemCollection = repairItemCollection;
    }

    public Client getIdClient() {
        return idClient;
    }

    public void setIdClient(Client idClient) {
        this.idClient = idClient;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idWatch != null ? idWatch.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Watch)) {
            return false;
        }
        Watch other = (Watch) object;
        if ((this.idWatch == null && other.idWatch != null) || (this.idWatch != null && !this.idWatch.equals(other.idWatch))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.ac.bg.fon.watchrepairservice.models.Watch[ idWatch=" + idWatch + " ]";
    }
    
}
