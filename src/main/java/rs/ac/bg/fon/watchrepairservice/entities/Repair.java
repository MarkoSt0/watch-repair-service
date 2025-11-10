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
import rs.ac.bg.fon.watchrepairservice.enums.Status;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "repair")
@NamedQueries({
    @NamedQuery(name = "Repair.findAll", query = "SELECT r FROM Repair r"),
    @NamedQuery(name = "Repair.findByIdRepair", query = "SELECT r FROM Repair r WHERE r.idRepair = :idRepair"),
    @NamedQuery(name = "Repair.findByCreatedAt", query = "SELECT r FROM Repair r WHERE r.createdAt = :createdAt"),
    @NamedQuery(name = "Repair.findByTotalCost", query = "SELECT r FROM Repair r WHERE r.totalCost = :totalCost"),
    @NamedQuery(name = "Repair.findByStatus", query = "SELECT r FROM Repair r WHERE r.status = :status")})
public class Repair implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_repair")
    private Long idRepair;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_cost")
    private BigDecimal totalCost;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @JoinColumn(name = "id_client", referencedColumnName = "id_client")
    @ManyToOne(optional = false)
    private Client idClient;
    @JoinColumn(name = "id_employee", referencedColumnName = "id_employee")
    @ManyToOne(optional = false)
    private Employee idEmployee;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRepair")
    private Collection<RepairItem> repairItemCollection;

    public Repair() {
    }

    public Repair(Long idRepair) {
        this.idRepair = idRepair;
    }

    public Repair(Long idRepair, Date createdAt) {
        this.idRepair = idRepair;
        this.createdAt = createdAt;
    }

    public Long getIdRepair() {
        return idRepair;
    }

    public void setIdRepair(Long idRepair) {
        this.idRepair = idRepair;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
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

    public Client getIdClient() {
        return idClient;
    }

    public void setIdClient(Client idClient) {
        this.idClient = idClient;
    }

    public Employee getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Employee idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Collection<RepairItem> getRepairItemCollection() {
        return repairItemCollection;
    }

    public void setRepairItemCollection(Collection<RepairItem> repairItemCollection) {
        this.repairItemCollection = repairItemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRepair != null ? idRepair.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Repair)) {
            return false;
        }
        Repair other = (Repair) object;
        if ((this.idRepair == null && other.idRepair != null) || (this.idRepair != null && !this.idRepair.equals(other.idRepair))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.ac.bg.fon.watchrepairservice.models.Repair[ idRepair=" + idRepair + " ]";
    }
    
}
