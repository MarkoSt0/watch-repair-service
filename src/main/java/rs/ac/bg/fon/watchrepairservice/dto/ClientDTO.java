/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.dto;

import java.util.List;

/**
 *
 * @author Marko
 */

/*
    Klasa ClientDTO se upotrebljava u komunikaciji putem REST API.
    Prilikom rada sistema sa klijentima svaki deo zahteva odredjene informacije:
    Ucitavanje svih korisnika: id, email, ime, prezime, telefon, lista satova
    Brisanje korisnika: id (korisnika koji se brise, a koji je izabran iz liste)
    Dodavanje: email, ime, prezime, telefon, lista satova
    Pretraga: id (ili prosto filtriranje liste)
*/

public class ClientDTO {
    private Long idClient;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private List<WatchDTO> watchCollection;

    public ClientDTO() {
    }

    public ClientDTO(Long idClient, String email, String firstName, String lastName, String phone, List<WatchDTO> watchCollection) {
        this.idClient = idClient;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.watchCollection = watchCollection;
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

    public List<WatchDTO> getWatchCollection() {
        return watchCollection;
    }

    public void setWatchCollection(List<WatchDTO> watchCollection) {
        this.watchCollection = watchCollection;
    }

    
    
    
}
