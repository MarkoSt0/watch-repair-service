/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.watchrepairservice.entity.Client;
import rs.ac.bg.fon.watchrepairservice.entity.Watch;
import rs.ac.bg.fon.watchrepairservice.enums.Movement;

/**
 *
 * @author Marko
 */
@Repository
public interface WatchRepository extends JpaRepository<Watch, Long>{
    
    public List<Watch> findByIdClient(Client client);

    public List<Watch> findByBrand(String brand);
    
    public List<Watch> findByMovement(Movement movement);
}
