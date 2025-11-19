/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package rs.ac.bg.fon.watchrepairservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rs.ac.bg.fon.watchrepairservice.entity.Client;
import rs.ac.bg.fon.watchrepairservice.entity.Employee;
import rs.ac.bg.fon.watchrepairservice.entity.Part;
import rs.ac.bg.fon.watchrepairservice.entity.Repair;
import rs.ac.bg.fon.watchrepairservice.entity.RepairItem;
import rs.ac.bg.fon.watchrepairservice.entity.RepairItemPart;
import rs.ac.bg.fon.watchrepairservice.entity.Watch;
import rs.ac.bg.fon.watchrepairservice.repository.ClientRepository;
import rs.ac.bg.fon.watchrepairservice.repository.EmployeeRepository;
import rs.ac.bg.fon.watchrepairservice.repository.PartRepository;
import rs.ac.bg.fon.watchrepairservice.repository.RepairItemPartRepository;
import rs.ac.bg.fon.watchrepairservice.repository.RepairItemRepository;
import rs.ac.bg.fon.watchrepairservice.repository.RepairRepository;
import rs.ac.bg.fon.watchrepairservice.repository.WatchRepository;

/**
 *
 * @author Marko
 */
@SpringBootApplication
//@EntityScan(basePackages = "rs.ac.bg.fon.watchrepairservice.entities")
//@EnableJpaRepositories
//        (basePackages = "rs.ac.bg.fon.watchrepairservice.repository")
public class WatchRepairService implements CommandLineRunner{
    ClientRepository clientRepo;
    EmployeeRepository employeeRepo;
    PartRepository partRepo;
    RepairRepository repairRepo;
    RepairItemRepository repairItemRepo;
    RepairItemPartRepository repairItemPartRepo;
    WatchRepository watchRepo;

    public WatchRepairService(ClientRepository clientRepo, 
            EmployeeRepository employeeRepo,
            PartRepository partRepo,
            RepairRepository repairRepo,
            RepairItemRepository repairItemRepo,
            RepairItemPartRepository repairItemPartRepo,
            WatchRepository watchRepo) {
        this.clientRepo = clientRepo;
        this.employeeRepo = employeeRepo;
        this.partRepo = partRepo;
        this.repairItemPartRepo = repairItemPartRepo;
        this.repairItemRepo = repairItemRepo;
        this.repairRepo = repairRepo;
        this.watchRepo = watchRepo;
    }
    
    

    public static void main(String[] args) {
        SpringApplication.run(WatchRepairService.class, args);
    }
    
    @Override
    public void run(String... args){
        Employee employee = employeeRepo.findById(Long.valueOf(1)).orElse(null);
        Part part = partRepo.findById(Long.valueOf(1)).orElse(null);
        Repair repair = repairRepo.findById(Long.valueOf(1)).orElse(null);
        RepairItem repairItem = repairItemRepo.findById(Long.valueOf(1)).orElse(null);
        RepairItemPart repairItemPart = repairItemPartRepo.findById(Long.valueOf(1)).orElse(null);
        Watch watch = watchRepo.findById(Long.valueOf(1)).orElse(null);
        Client client = clientRepo.findById(Long.valueOf(1)).orElse(null);
        System.out.println(employee);
        System.out.println(part);
        System.out.println(repair);
        System.out.println(repairItem);
        System.out.println(repairItemPart);
        System.out.println(watch);
        System.out.println(client);
        
    }
}
