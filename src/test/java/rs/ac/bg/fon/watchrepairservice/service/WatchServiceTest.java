/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import rs.ac.bg.fon.watchrepairservice.dto.*;
import rs.ac.bg.fon.watchrepairservice.entity.*;
import rs.ac.bg.fon.watchrepairservice.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import rs.ac.bg.fon.watchrepairservice.communication.ServiceResult;
import rs.ac.bg.fon.watchrepairservice.dto.WatchDTO;
import rs.ac.bg.fon.watchrepairservice.enums.Movement;

/**
 *
 * @author Marko
 */
@ExtendWith(MockitoExtension.class)
public class WatchServiceTest {
    @Mock 
    private ClientRepository clientRepo;
    @Mock 
    private EmployeeRepository employeeRepo;
    @Mock 
    private WatchRepository watchRepo;
    @Mock 
    private RepairRepository repairRepo;
    
    @InjectMocks
    private RepairService repairService;
    
    public WatchServiceTest() {
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
     * Test of add method, of class WatchService.
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

        when(clientRepo.findById(1L)).thenReturn(Optional.of(client));
        when(employeeRepo.findById(2L)).thenReturn(Optional.of(employee));
        when(watchRepo.findById(5L)).thenReturn(Optional.of(watch));
        when(repairRepo.save(any(Repair.class))).thenReturn(saved);

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

    /**
     * Test of update method, of class WatchService.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        WatchDTO dto = null;
        WatchService instance = null;
        ServiceResult expResult = null;
        ServiceResult result = instance.update(dto);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWatch method, of class WatchService.
     */
    @Test
    public void testGetWatch() {
        System.out.println("getWatch");
        Long id = null;
        WatchService instance = null;
        ServiceResult expResult = null;
        ServiceResult result = instance.getWatch(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllWatches method, of class WatchService.
     */
    @Test
    public void testGetAllWatches() {
        System.out.println("getAllWatches");
        WatchService instance = null;
        ServiceResult expResult = null;
        ServiceResult result = instance.getAllWatches();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWatchesByClient method, of class WatchService.
     */
    @Test
    public void testGetWatchesByClient() {
        System.out.println("getWatchesByClient");
        Long clientId = null;
        WatchService instance = null;
        ServiceResult expResult = null;
        ServiceResult result = instance.getWatchesByClient(clientId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWatchesByBrand method, of class WatchService.
     */
    @Test
    public void testGetWatchesByBrand() {
        System.out.println("getWatchesByBrand");
        String brand = "";
        WatchService instance = null;
        ServiceResult expResult = null;
        ServiceResult result = instance.getWatchesByBrand(brand);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWatchesByMovement method, of class WatchService.
     */
    @Test
    public void testGetWatchesByMovement() {
        System.out.println("getWatchesByMovement");
        Movement movement = null;
        WatchService instance = null;
        ServiceResult expResult = null;
        ServiceResult result = instance.getWatchesByMovement(movement);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteWatch method, of class WatchService.
     */
    @Test
    public void testDeleteWatch() {
        System.out.println("deleteWatch");
        Long id = null;
        WatchService instance = null;
        ServiceResult expResult = null;
        ServiceResult result = instance.deleteWatch(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
