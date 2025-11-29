/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.watchrepairservice.entity.Client;
import rs.ac.bg.fon.watchrepairservice.entity.Employee;
import rs.ac.bg.fon.watchrepairservice.entity.Repair;

/**
 *
 * @author Marko
 */
@Repository
public interface RepairRepository extends JpaRepository<Repair, Long>{

    public boolean existsByIdClient(Client client);

    public boolean existsByIdEmployee(Employee employee);

    public List<Repair> findByIdEmployee(Employee employee);
    
}
