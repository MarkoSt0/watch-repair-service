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
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.watchrepairservice.dto.ClientDTO;
import rs.ac.bg.fon.watchrepairservice.entity.Client;
import rs.ac.bg.fon.watchrepairservice.repository.ClientRepository;
import static org.mockito.Mockito.*;
import rs.ac.bg.fon.watchrepairservice.communication.ServiceResult;
import rs.ac.bg.fon.watchrepairservice.entity.Watch;
import rs.ac.bg.fon.watchrepairservice.repository.RepairRepository;
import rs.ac.bg.fon.watchrepairservice.repository.WatchRepository;


/**
 *
 * @author Marko
 */
@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock
    private ClientRepository clientRepo;
    @Mock
    private RepairRepository repairRepo;
    @Mock
    private WatchRepository watchRepo;
    
    @InjectMocks
    private ClientService clientService;
    
    public ClientServiceTest() {
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
     * Test of add method, of class ClientService.
     */
    @Test
    public void add_valuedDto_success() {
        ClientDTO dto = new ClientDTO();
        dto.setEmail("noviklijent@gmail.com");
        dto.setFirstName("Petar");
        dto.setLastName("Petrovic");
        dto.setPhone("+381658967833");
        
        Client existing = new Client();
        existing.setIdClient(5L);
        
        when(clientRepo.save(any(Client.class))).thenReturn(existing);
        
        ServiceResult result = clientService.add(dto);
        
        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
        assertEquals("Clinet added successfully.", result.getMessage());
        assertEquals(5L, ((ClientDTO)result.getData()).getIdClient());
        verify(clientRepo, times(1)).save(any(Client.class));
    }
    
    @Test
    public void add_invalidDto_returnsError(){
        ClientDTO dto = new ClientDTO();
        
        ServiceResult result = clientService.add(dto);
        
        assertFalse(result.isSuccess());
        assertEquals("Invalid client data. All data is required.", result.getMessage());
        verify(clientRepo, never()).save(any());
    }
    
    @Test
    public void add_repositroyThrows_errorReturned(){
        ClientDTO dto = new ClientDTO();
        dto.setEmail("noviklijent@gmail.com");
        dto.setFirstName("Petar");
        dto.setLastName("Petrovic");
        dto.setPhone("+381658967833");
        
        when(clientRepo.save(any(Client.class))).thenThrow(new RuntimeException(("Error while adding client:")));
        
        ServiceResult result = clientService.add(dto);
        
        assertFalse(result.isSuccess());
        assertNull(result.getData());
        assertTrue(result.getMessage().contains("Error while adding client:"));
    }

    /**
     * Test of update method, of class ClientService.
     */
    @Test
    public void update_validDto_success() {
        ClientDTO dto = new ClientDTO();
        dto.setIdClient(1L);
        dto.setEmail("new@gmail.com");
        dto.setFirstName("Pera");
        dto.setLastName("Peric");
        dto.setPhone("123");

        Client existing = new Client();
        existing.setIdClient(1L);
        existing.setEmail("old@gmail.com");

        when(clientRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(clientRepo.existsByEmail("new@gmail.com")).thenReturn(false);
        when(clientRepo.save(existing)).thenReturn(existing);

        ServiceResult result = clientService.update(dto);

        assertTrue(result.isSuccess());
        assertEquals("Client updated successfully.", result.getMessage());
        assertNotNull(result.getData());
        assertEquals("new@gmail.com", ((ClientDTO)result.getData()).getEmail());
    }
    
    @Test
    public void update_invalidDto_returnsError() {
        ClientDTO dto = new ClientDTO();
        dto.setIdClient(1L);

        Client existing = new Client();
        existing.setIdClient(1L);

        when(clientRepo.findById(1L)).thenReturn(Optional.of(existing));

        ServiceResult result = clientService.update(dto);

        assertFalse(result.isSuccess());
        assertEquals("Invalid client data.", result.getMessage());
    }
    
    @Test
    public void update_emailAlreadyExists_returnsError() {
        ClientDTO dto = new ClientDTO();
        dto.setIdClient(1L);
        dto.setEmail("test@gmail.com");
        dto.setFirstName("Pera");
        dto.setLastName("Peric");
        dto.setPhone("123");

        Client existing = new Client();
        existing.setIdClient(1L);
        existing.setEmail("old@gmail.com");
        existing.setFirstName("Mika");
        existing.setLastName("Mikic");
        existing.setPhone("123");

        when(clientRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(clientRepo.existsByEmail("test@gmail.com")).thenReturn(true);

        ServiceResult result = clientService.update(dto);

        assertFalse(result.isSuccess());
        assertEquals("Client with this email already exists.", result.getMessage());
    }

    @Test
    public void update_clientNotFound_returnsError() {
        ClientDTO dto = new ClientDTO();
        dto.setIdClient(20L);

        when(clientRepo.findById(20L)).thenReturn(Optional.empty());

        ServiceResult result = clientService.update(dto);

        assertFalse(result.isSuccess());
        assertEquals("Error while updating client: Client not found.", result.getMessage());
    }
    
    /**
     * Test of getClient method, of class ClientService.
     */
    @Test
    public void getClient_found_success() {
        Client c = new Client();
        c.setIdClient(3L);

        when(clientRepo.findById(3L)).thenReturn(Optional.of(c));

        ServiceResult result = clientService.getClient(3L);

        assertTrue(result.isSuccess());
        assertEquals("Client found.", result.getMessage());
        assertNotNull(result.getData());
    }
    
    @Test
    public void getClient_notFound_returnsError() {
        when(clientRepo.findById(8L)).thenReturn(Optional.empty());

        ServiceResult result = clientService.getClient(8L);

        assertFalse(result.isSuccess());
        assertEquals("Client not found.", result.getMessage());
    }


    /**
     * Test of getAllClients method, of class ClientService.
     */
    @Test
    public void getAllClients_success() {
        when(clientRepo.findAll()).thenReturn(List.of(new Client()));

        ServiceResult result = clientService.getAllClients();

        assertTrue(result.isSuccess());
        assertEquals("All clients loaded.", result.getMessage());
        assertNotNull(result.getData());
    }
    
    /**
     * Test of getClientWithWatches method, of class ClientService.
     */
    @Test
    public void getClientWithWatches_found_success() {
        Client entity = new Client();
        entity.setIdClient(1L);
        
        when(clientRepo.findById(1L)).thenReturn(Optional.of(entity));

        ServiceResult result = clientService.getClientWithWatches(1L);

        assertTrue(result.isSuccess());
        assertEquals("Client with watches found.", result.getMessage());
        assertNotNull(result.getData());
    }
    
    @Test
    public void getClientWithWatches_notFound_returnsError() {
        when(clientRepo.findById(4L)).thenReturn(Optional.empty());

        ServiceResult result = clientService.getClientWithWatches(4L);

        assertFalse(result.isSuccess());
        assertEquals("Error while fetching client: Client not found.", result.getMessage());
    }

    /**
     * Test of delete method, of class ClientService.
     */
    @Test
    public void delete_valid_success() {
        Client c = new Client();
        c.setIdClient(10L);

        when(clientRepo.findById(10L)).thenReturn(Optional.of(c));
        when(repairRepo.existsByIdClient(c)).thenReturn(false);

        ServiceResult result = clientService.delete(10L);

        assertTrue(result.isSuccess());
        assertEquals("Client deleted successfully.", result.getMessage());
    }
    
    @Test
    public void delete_clientHasRepairHistory_returnsError() {
        Client c = new Client();
        c.setIdClient(10L);

        when(clientRepo.findById(10L)).thenReturn(Optional.of(c));
        when(repairRepo.existsByIdClient(c)).thenReturn(true);

        ServiceResult result = clientService.delete(10L);

        assertFalse(result.isSuccess());
        assertEquals("Cannot delete client that was part of repair history.", result.getMessage());
    }
    
    @Test
    public void delete_notFound_returnsError() {
        when(clientRepo.findById(22L)).thenReturn(Optional.empty());

        ServiceResult result = clientService.delete(22L);

        assertFalse(result.isSuccess());
        assertEquals("Error while deleting client: Client not found.", result.getMessage());
    }




    /**
     * Test of getClientWatches method, of class ClientService.
     */
    @Test
    public void getClientWatches_success() {
        Client c = new Client();
        c.setIdClient(2L);

        Watch w = new Watch();
        w.setIdWatch(6L); 

        when(clientRepo.findById(2L)).thenReturn(Optional.of(c));
        when(watchRepo.findByIdClient(c)).thenReturn(List.of(w));

        ServiceResult result = clientService.getClientWatches(2L);

        assertTrue(result.isSuccess());
        assertEquals("Client's watches loaded.", result.getMessage());
        assertNotNull(result.getData());
    }

    @Test
    public void getClientWatches_notFound_returnsError() {
        when(clientRepo.findById(5L)).thenReturn(Optional.empty());

        ServiceResult result = clientService.getClientWatches(5L);

        assertFalse(result.isSuccess());
        assertEquals("Client not found.", result.getMessage());
    }

}
