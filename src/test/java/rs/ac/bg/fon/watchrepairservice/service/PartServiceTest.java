/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.service;

import jakarta.persistence.EntityNotFoundException;
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
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.watchrepairservice.dto.PartDTO;
import rs.ac.bg.fon.watchrepairservice.entity.Part;
import rs.ac.bg.fon.watchrepairservice.mapper.PartMapper;
import rs.ac.bg.fon.watchrepairservice.repository.PartRepository;
import static org.mockito.Mockito.*;
import org.springframework.dao.DataIntegrityViolationException;
import rs.ac.bg.fon.watchrepairservice.communication.ServiceResult;

/**
 *
 * @author Marko
 */
@ExtendWith(MockitoExtension.class)
public class PartServiceTest {
    @Mock
    private PartRepository partRepo;

    @InjectMocks
    private PartService partService;
    
    private Part part;
    private PartDTO partDTO;

    
    public PartServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        part = new Part();
        part.setIdPart(1L);
        part.setName("Spring");
        
        partDTO = new PartDTO();
        partDTO.setIdPart(1L);
        partDTO.setName("Spring");
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addPart method, of class PartService.
     */
    @Test
    public void testAddPart_Valid_Success() {
        try (MockedStatic<PartMapper> mock = mockStatic(PartMapper.class)) {

            mock.when(() -> PartMapper.isValidDTO(any())).thenReturn(true);
            mock.when(() -> PartMapper.toEntity(partDTO)).thenReturn(part);
            mock.when(() -> PartMapper.toDTO(part)).thenReturn(partDTO);

            when(partRepo.existsByName(partDTO.getName())).thenReturn(false);
            when(partRepo.save(part)).thenReturn(part);

            ServiceResult result = partService.addPart(partDTO);

            assertTrue(result.isSuccess());
            assertEquals(partDTO, result.getData());
        }
    }
    
    @Test
    public void testAddPart_NameExists_Error() {
        try (MockedStatic<PartMapper> mock = mockStatic(PartMapper.class)) {

            mock.when(() -> PartMapper.isValidDTO(any())).thenReturn(true);
            when(partRepo.existsByName(partDTO.getName())).thenReturn(true);

            ServiceResult result = partService.addPart(partDTO);

            assertFalse(result.isSuccess());
            assertTrue(result.getMessage().contains("already exists"));
        }
    }
    
    @Test
    public void testAddPart_InvalidDTO_Error() {
        try (MockedStatic<PartMapper> mock = mockStatic(PartMapper.class)) {

            mock.when(() -> PartMapper.isValidDTO(any())).thenReturn(false);

            ServiceResult result = partService.addPart(partDTO);

            assertFalse(result.isSuccess());
            assertTrue(result.getMessage().contains("Invalid"));
        }
    }

    /**
     * Test of updatePart method, of class PartService.
     */
    @Test
    public void testUpdatePart_Valid_Success() {
        partDTO.setName("Gear");
        part.setName("Spring");
        try (MockedStatic<PartMapper> mock = mockStatic(PartMapper.class)) {
            when(partRepo.findById(partDTO.getIdPart())).thenReturn(Optional.of(part));
            mock.when(() -> PartMapper.isValidDTO(any())).thenReturn(true);
            when(partRepo.existsByName("Gear")).thenReturn(false);
            mock.when(() -> PartMapper.toDTO(part)).thenReturn(partDTO);
            when(partRepo.save(part)).thenReturn(part);
            
            ServiceResult result = partService.updatePart(partDTO);
            
            assertTrue(result.isSuccess());
            assertEquals(partDTO, result.getData());
        }
    }
    
    @Test
    public void testUpdatePart_NameExists_Error() {
        partDTO.setName("Gear");
        part.setName("Spring");
        try (MockedStatic<PartMapper> mock = mockStatic(PartMapper.class)) {
            
            when(partRepo.findById(partDTO.getIdPart())).thenReturn(Optional.of(part));
            mock.when(() -> PartMapper.isValidDTO(any())).thenReturn(true);
            
            when(partRepo.existsByName("Gear")).thenReturn(true);
            
            ServiceResult result = partService.updatePart(partDTO);
            
            assertFalse(result.isSuccess());
            assertTrue(result.getMessage().contains("already exists"));
        }
    }
    
    @Test
    public void testUpdatePart_InvalidDTO_Error() {
        try (MockedStatic<PartMapper> mock = mockStatic(PartMapper.class)) {
            
            when(partRepo.findById(partDTO.getIdPart())).thenReturn(Optional.of(part));
            mock.when(() -> PartMapper.isValidDTO(any())).thenReturn(false);
            
            ServiceResult result = partService.updatePart(partDTO);
            
            assertFalse(result.isSuccess());
            assertTrue(result.getMessage().contains("Invalid"));
        }
    }
    
    @Test
    public void testUpdatePart_NotFound_Error() {
        when(partRepo.findById(partDTO.getIdPart())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> partService.updatePart(partDTO));
    }

    /**
     * Test of getPart method, of class PartService.
     */
    @Test
    public void testGetPart_Success() {
        try (MockedStatic<PartMapper> mock = mockStatic(PartMapper.class)) {
            
            when(partRepo.findById(part.getIdPart())).thenReturn(Optional.of(part));
            mock.when(() -> PartMapper.toDTO(part)).thenReturn(partDTO);
            
            ServiceResult result = partService.getPart(part.getIdPart());
            
            assertTrue(result.isSuccess());
            assertEquals(partDTO, result.getData());
        }
    }
    
    @Test
    public void testGetPart_NotFound_Error() {
        when(partRepo.findById(part.getIdPart())).thenReturn(Optional.empty());
        
        ServiceResult result = partService.getPart(part.getIdPart());
        
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("not found"));
    }

    /**
     * Test of getPartByName method, of class PartService.
     */
    @Test
    public void testGetPartByName_Success() {
        try (MockedStatic<PartMapper> mock = mockStatic(PartMapper.class)) {
            when(partRepo.findByName(part.getName())).thenReturn(Optional.of(part));
            mock.when(() -> PartMapper.toDTO(part)).thenReturn(partDTO);
            
            ServiceResult result = partService.getPartByName(part.getName());
            
            assertTrue(result.isSuccess());
            assertEquals(partDTO, result.getData());
        }
    }
    
    @Test
    public void testGetPartByName_NotFound_Error() {
        when(partRepo.findByName(part.getName())).thenReturn(Optional.empty());
        
        ServiceResult result = partService.getPartByName(part.getName());
        
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("not found"));
    }

    /**
     * Test of deletePartById method, of class PartService.
     */
    @Test
    public void testDeletePart_Success() {
        when(partRepo.findById(part.getIdPart())).thenReturn(Optional.of(part));

        ServiceResult result = partService.deletePartById(part.getIdPart());

        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().contains("deleted"));
    }
    
    @Test
    void testDeletePart_ConstraintViolation_Error() {
        when(partRepo.findById(part.getIdPart())).thenReturn(Optional.of(part));
        doThrow(DataIntegrityViolationException.class)
                .when(partRepo).deleteById(part.getIdPart());
        
        ServiceResult result = partService.deletePartById(part.getIdPart());
        
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("used in repairs"));
    }
    
    @Test
    public void testDeletePart_NotFound_Error() {
        when(partRepo.findById(part.getIdPart())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> partService.deletePartById(part.getIdPart()));
    }

    /**
     * Test of getAllParts method, of class PartService.
     */
    @Test
    public void testGetAllParts_Success() {
        try (MockedStatic<PartMapper> mock = mockStatic(PartMapper.class)) {

            when(partRepo.findAll()).thenReturn(List.of(part));
            mock.when(() -> PartMapper.toDTO(part)).thenReturn(partDTO);

            ServiceResult result = partService.getAllParts();

            assertTrue(result.isSuccess());
            assertEquals(1, ((List<?>) result.getData()).size());
        }
    }
    
}
