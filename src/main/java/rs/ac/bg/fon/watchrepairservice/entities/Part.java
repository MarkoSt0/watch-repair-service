/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import jakarta.persistence.*;
/**
 *
 * @author Marko
 */
@Entity
@Table(name = "part")
@NamedQueries({
    @NamedQuery(name = "Part.findAll", query = "SELECT p FROM Part p"),
    @NamedQuery(name = "Part.findByIdPart", query = "SELECT p FROM Part p WHERE p.idPart = :idPart"),
    @NamedQuery(name = "Part.findByName", query = "SELECT p FROM Part p WHERE p.name = :name"),
    @NamedQuery(name = "Part.findByCurrentPrice", query = "SELECT p FROM Part p WHERE p.currentPrice = :currentPrice")})
public class Part implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_part")
    private Long idPart;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "current_price")
    private BigDecimal currentPrice;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPart")
    private Collection<RepairItemPart> repairItemPartCollection;

    public Part() {
    }

    public Part(Long idPart) {
        this.idPart = idPart;
    }

    public Part(Long idPart, String name) {
        this.idPart = idPart;
        this.name = name;
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

    public Collection<RepairItemPart> getRepairItemPartCollection() {
        return repairItemPartCollection;
    }

    public void setRepairItemPartCollection(Collection<RepairItemPart> repairItemPartCollection) {
        this.repairItemPartCollection = repairItemPartCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPart != null ? idPart.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Part)) {
            return false;
        }
        Part other = (Part) object;
        if ((this.idPart == null && other.idPart != null) || (this.idPart != null && !this.idPart.equals(other.idPart))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.ac.bg.fon.watchrepairservice.models.Part[ idPart=" + idPart + " ]";
    }
    
}
