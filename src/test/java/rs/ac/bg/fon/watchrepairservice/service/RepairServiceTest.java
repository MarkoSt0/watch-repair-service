/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.watchrepairservice.communication.ServiceResult;
import rs.ac.bg.fon.watchrepairservice.dto.ClientDTO;
import rs.ac.bg.fon.watchrepairservice.dto.EmployeeDTO;
import rs.ac.bg.fon.watchrepairservice.dto.PartDTO;
import rs.ac.bg.fon.watchrepairservice.dto.RepairDTO;
import rs.ac.bg.fon.watchrepairservice.dto.RepairItemDTO;
import rs.ac.bg.fon.watchrepairservice.dto.RepairItemPartDTO;
import rs.ac.bg.fon.watchrepairservice.dto.WatchDTO;
import rs.ac.bg.fon.watchrepairservice.entity.Client;
import rs.ac.bg.fon.watchrepairservice.entity.Employee;
import rs.ac.bg.fon.watchrepairservice.entity.Part;
import rs.ac.bg.fon.watchrepairservice.entity.Repair;
import rs.ac.bg.fon.watchrepairservice.entity.Watch;
import rs.ac.bg.fon.watchrepairservice.repository.ClientRepository;
import rs.ac.bg.fon.watchrepairservice.repository.EmployeeRepository;
import rs.ac.bg.fon.watchrepairservice.repository.PartRepository;
import rs.ac.bg.fon.watchrepairservice.repository.RepairRepository;
import rs.ac.bg.fon.watchrepairservice.repository.WatchRepository;

/**
 *
 * @author Marko
 */
@ExtendWith(MockitoExtension.class)
public class RepairServiceTest {
    
    @Mock 
    private ClientRepository clientRepo;
    @Mock 
    private EmployeeRepository employeeRepo;
    @Mock 
    private WatchRepository watchRepo;
    @Mock 
    private RepairRepository repairRepo;
    @Mock 
    private PartRepository partRepo;
    
    @InjectMocks
    private RepairService repairService;
    
    public RepairServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of add method, of class RepairService.
     */
    @Test
    void add_validData_success() {
//        In orderd added (DTOs): Client, Employee, Watch, One ItemPart, 
//        one Item containing part
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setIdClient(1L);

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setIdEmployee(2L);

        WatchDTO watchDTO = new WatchDTO();
        watchDTO.setIdWatch(5L);

        RepairItemPartDTO partDTO = new RepairItemPartDTO();
        partDTO.setBrandName("Omega");
        partDTO.setQuantity(2);
        partDTO.setCost(new BigDecimal("50"));

        RepairItemDTO itemDTO = new RepairItemDTO();
        itemDTO.setWatch(watchDTO);
        itemDTO.setRepairItemPartCollection(Collections.singletonList(partDTO));

        PartDTO p = new PartDTO();
        p.setIdPart(3L);
        partDTO.setPart(p);
        

        
//        Repair!
        RepairDTO dto = new RepairDTO();
        dto.setClient(clientDTO);
        dto.setEmployee(employeeDTO);
        dto.setRepairItemCollection(Collections.singletonList(itemDTO));

//        Mock database returned balues
        Client client = new Client();
        client.setIdClient(1L);

        Employee employee = new Employee();
        employee.setIdEmployee(2L);

        Watch watch = new Watch();
        watch.setIdWatch(5L);

        Repair saved = new Repair();
        saved.setIdRepair(10L);
        
        Part part = new Part();
        part.setIdPart(3L);

        when(clientRepo.findById(1L)).thenReturn(Optional.of(client));
        when(employeeRepo.findById(2L)).thenReturn(Optional.of(employee));
        when(watchRepo.findById(5L)).thenReturn(Optional.of(watch));
        when(repairRepo.save(any(Repair.class))).thenReturn(saved);
        when(partRepo.findById(3L)).thenReturn(Optional.of(part));

//        execute
        ServiceResult result = repairService.add(dto);
        
        assertTrue(result.isSuccess(), "Should be success, but was: " + result.getMessage());

//        assertTrue(result.isSuccess());
        assertEquals("Repair added successfully.", result.getMessage());
        assertNotNull(result.getData());

        verify(clientRepo).findById(1L);
        verify(employeeRepo).findById(2L);
        verify(watchRepo).findById(5L);
        verify(repairRepo).save(any(Repair.class));
    }
    
}
