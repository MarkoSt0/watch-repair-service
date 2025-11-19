/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.watchrepairservice.entity.Client;

/**
 *
 * @author Marko
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
    public Client findByIdClient(Long id);
}
