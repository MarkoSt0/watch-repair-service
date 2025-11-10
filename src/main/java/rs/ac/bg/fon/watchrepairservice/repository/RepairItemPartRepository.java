/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.watchrepairservice.entities.RepairItemPart;

/**
 *
 * @author Marko
 */
@Repository
public interface RepairItemPartRepository extends JpaRepository<RepairItemPart, Long>{
    
}
