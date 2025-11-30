/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.service;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.watchrepairservice.communication.ServiceResult;
import rs.ac.bg.fon.watchrepairservice.dto.WatchDTO;
import rs.ac.bg.fon.watchrepairservice.entity.Client;
import rs.ac.bg.fon.watchrepairservice.entity.Watch;
import rs.ac.bg.fon.watchrepairservice.repository.ClientRepository;
import rs.ac.bg.fon.watchrepairservice.repository.RepairItemRepository;
import rs.ac.bg.fon.watchrepairservice.repository.WatchRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import rs.ac.bg.fon.watchrepairservice.enums.Movement;

/**
 *
 * @author Marko
 */
@ExtendWith(MockitoExtension.class)
public class WatchServiceTest {
    @Mock
    private WatchRepository watchRepo;
    @Mock
    private ClientRepository clientRepo;
    @Mock
    private RepairItemRepository repairItemRepo;
    
    @InjectMocks
    private WatchService watchService;
    
    // Entities-DTOs
    private WatchDTO watchDTO;
    private Watch watchEntity;
    private Client client;
    
    
    public WatchServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    // First time using this method
    @BeforeEach
    public void setUp() {
        client = new Client();
        client.setIdClient(1L);
        
        watchDTO = new WatchDTO();
        watchDTO.setIdClient(1L);
        watchDTO.setBrand("Longines");
        
        watchEntity = new Watch();
        watchEntity.setIdWatch(5L);
        watchEntity.setIdClient(client);
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of add method, of class WatchService.
     */
    @Test
    public void add_validWatch_success(){
        when(clientRepo.findById(1L)).thenReturn(Optional.of(client));
        when(watchRepo.save(any(Watch.class))).thenReturn(watchEntity);
        
        ServiceResult result = watchService.add(watchDTO);
        
        assertTrue(result.isSuccess());
        assertEquals("Watch added successfully.", result.getMessage());
        assertNotNull(result.getData()); verify(watchRepo, times(1)).save(any(Watch.class));
    }
    
    @Test
    public void add_invalidWatch_returnsError(){
        watchDTO.setBrand(null);
        ServiceResult result = watchService.add(watchDTO);
        assertFalse(result.isSuccess());
        assertEquals("Invalid watch data. Brand and client are required.",
                result.getMessage());
        verify(watchRepo, never()).save(any());
    }
    
    @Test
    public void add_clientNotFound_returnsError(){
        when(clientRepo.findById(1L)).thenReturn(Optional.empty());
        ServiceResult result = watchService.add(watchDTO);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Client with ID 1 not found."));
    }
    /**
     * Test of update method, of class WatchService.
     */
    @Test
    public void update_validWatch_success(){
        when(watchRepo.findById(10L)).thenReturn(Optional.of(watchEntity));
        when(watchRepo.save(any(Watch.class))).thenReturn(watchEntity);
        watchDTO.setIdWatch(10L);
        
        ServiceResult result = watchService.update(watchDTO);
        
        assertTrue(result.isSuccess());
        assertEquals("Watch updated successfully.",
                result.getMessage());
        verify(watchRepo, times(1)).save(any(Watch.class));
    }
    
    @Test 
    public void update_watchNotFound_returnsError(){
        when(watchRepo.findById(10L)).thenReturn(Optional.empty());
        watchDTO.setIdWatch(10L);
        
        ServiceResult result = watchService.update(watchDTO);
       
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Watch not found."));
    }

    /**
     * Test of getWatch method, of class WatchService.
     */
    @Test
    public void getWatch_found_success(){
        when(watchRepo.findById(10L)).thenReturn(Optional.of(watchEntity));
        
        ServiceResult result = watchService.getWatch(10L);
        
        assertTrue(result.isSuccess());
        assertEquals("Watch found.", result.getMessage());
        assertNotNull(result.getData());
    }
    
    @Test
    public void getWatch_notFound_returnsError(){
        when(watchRepo.findById(10L)).thenReturn(Optional.empty());
        
        ServiceResult result = watchService.getWatch(10L);
        
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Watch not found."));
    }

    /**
     * Test of getAllWatches method, of class WatchService.
     */
    @Test
    public void getAllWatches_success(){
        when(watchRepo.findAll()).thenReturn(List.of(watchEntity));
        
        ServiceResult result = watchService.getAllWatches();
        
        assertTrue(result.isSuccess());
        assertEquals("All watches loaded.", result.getMessage());
        assertNotNull(result.getData());
    }

    /**
     * Test of getWatchesByClient method, of class WatchService.
     */
    @Test
    public void getWatchesByClient_found_success(){
        when(clientRepo.findById(1L)).thenReturn(Optional.of(client));
        when(watchRepo.findByIdClient(client)).thenReturn(List.of(watchEntity));
        
        ServiceResult result = watchService.getWatchesByClient(1L);
        
        assertTrue(result.isSuccess());
        assertEquals("Watches for client loaded.", result.getMessage());
        assertNotNull(result.getData());
    }

    @Test
    public void getWatchesByClient_clientNotFound_returnsError(){
        when(clientRepo.findById(1L)).thenReturn(Optional.empty());
        
        ServiceResult result = watchService.getWatchesByClient(1L);
        
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Client not found."));
    }
    /**
     * Test of getWatchesByBrand method, of class WatchService.
     */
    @Test
    public void getWatchesByBrand_success() {
        when(watchRepo.findByBrand("Longines")).thenReturn(List.of(watchEntity));
        
        ServiceResult result = watchService.getWatchesByBrand("Longines");
        
        assertTrue(result.isSuccess());
        assertEquals("Watches by brand loaded.", result.getMessage());
        assertNotNull(result.getData());
    }
    
    @Test
    public void getWatchesByBrand_notFound_returnsEmpty(){
        when(watchRepo.findByBrand("Omega")).thenReturn(List.of());
        
        ServiceResult result = watchService.getWatchesByBrand("Omega");
        
        assertTrue(result.isSuccess());
        assertEquals("Watches by brand loaded.", result.getMessage());
        assertNotNull(result.getData());
    }

    /**
     * Test of getWatchesByMovement method, of class WatchService.
     */
    @Test
    public void getWatchesByMovement_success(){
        when(watchRepo.findByMovement(Movement.AUTOMATIC_MECHANICAL)).thenReturn(List.of(watchEntity));
        
        ServiceResult result = watchService.getWatchesByMovement(Movement.AUTOMATIC_MECHANICAL);
        
        assertTrue(result.isSuccess());
        assertEquals("Watches by movement loaded.", result.getMessage());
        assertNotNull(result.getData());
    }
    
    @Test
    public void getWatchesByMovement_notFound_returnsEmpty(){
        when(watchRepo.findByMovement(Movement.AUTOMATIC_MECHANICAL)).thenReturn(List.of());
        
        ServiceResult result = watchService.getWatchesByMovement(Movement.AUTOMATIC_MECHANICAL);
        
        assertTrue(result.isSuccess());
        assertEquals("Watches by movement loaded.", result.getMessage());
        assertNotNull(result.getData());
    }

    /**
     * Test of deleteWatch method, of class WatchService.
     */
    @Test
    public void deleteWatch_success(){
        when(watchRepo.findById(10L)).thenReturn(Optional.of(watchEntity));
        when(repairItemRepo.existsByIdWatch(watchEntity)).thenReturn(false);
        
        ServiceResult result = watchService.deleteWatch(10L);
        
        assertTrue(result.isSuccess());
        assertEquals("Watch deleted successfully.", result.getMessage());
        verify(watchRepo, times(1)).deleteById(10L);
    }
    
    @Test
    public void deleteWatch_hasRepairHistory_returnsError(){
        when(watchRepo.findById(10L)).thenReturn(Optional.of(watchEntity));
        when(repairItemRepo.existsByIdWatch(watchEntity)).thenReturn(true);
        
        ServiceResult result = watchService.deleteWatch(10L);
        
        assertFalse(result.isSuccess());
        assertEquals("Cannot delete watch that has repair history.", result.getMessage());
        verify(watchRepo, never()).deleteById(anyLong());
    }
    
}
