/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.watchrepairservice.entity.Part;

/**
 *
 * @author Marko
 */
@Repository
public interface PartRepository extends JpaRepository<Part, Long>{

    public Optional<Part> findByName(String name);

    public boolean existsByName(String name);
    
}
