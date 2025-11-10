/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.entities;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.*;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "client")
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
//    @NamedQuery(name = "Client.findByIdClient", query = "SELECT c FROM Client c WHERE c.idClient = :idClient"),
    @NamedQuery(name = "Client.findByEmail", query = "SELECT c FROM Client c WHERE c.email = :email"),
    @NamedQuery(name = "Client.findByFirstName", query = "SELECT c FROM Client c WHERE c.firstName = :firstName"),
    @NamedQuery(name = "Client.findByLastName", query = "SELECT c FROM Client c WHERE c.lastName = :lastName"),
    @NamedQuery(name = "Client.findByPhone", query = "SELECT c FROM Client c WHERE c.phone = :phone")})
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_client")
    private Long idClient;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone")
    private String phone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClient")
    private Collection<Repair> repairCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClient")
    private Collection<Watch> watchCollection;

    public Client() {
    }

    public Client(Long idClient) {
        this.idClient = idClient;
    }

    public Client(Long idClient, String email) {
        this.idClient = idClient;
        this.email = email;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Collection<Repair> getRepairCollection() {
        return repairCollection;
    }

    public void setRepairCollection(Collection<Repair> repairCollection) {
        this.repairCollection = repairCollection;
    }

    public Collection<Watch> getWatchCollection() {
        return watchCollection;
    }

    public void setWatchCollection(Collection<Watch> watchCollection) {
        this.watchCollection = watchCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClient != null ? idClient.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.idClient == null && other.idClient != null) || (this.idClient != null && !this.idClient.equals(other.idClient))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idClient + " " + firstName + " " + lastName;
    }
    
}
