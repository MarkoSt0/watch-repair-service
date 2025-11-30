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
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.watchrepairservice.dto.EmployeeDTO;
import rs.ac.bg.fon.watchrepairservice.entity.Employee;
import rs.ac.bg.fon.watchrepairservice.repository.EmployeeRepository;
import rs.ac.bg.fon.watchrepairservice.repository.RepairRepository;
import static org.mockito.Mockito.*;
import rs.ac.bg.fon.watchrepairservice.communication.ServiceResult;
import rs.ac.bg.fon.watchrepairservice.mapper.EmployeeMapper;

/**
 *
 * @author Marko
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepo;

    @Mock
    private RepairRepository repairRepo;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private EmployeeDTO employeeDTO;
    
    public EmployeeServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        employee = new Employee();
        employee.setIdEmployee(1L);
        employee.setUsername("stewie");

        employeeDTO = new EmployeeDTO();
        employeeDTO.setIdEmployee(1L);
        employeeDTO.setUsername("stewie");
        employeeDTO.setFirstName("Stewie");
        employeeDTO.setLastName("Griffin");
        employeeDTO.setPassword("stewie123");
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of add method, of class EmployeeService.
     */
    @Test
    public void testAdd_ValidDTO_Success(){
        when(employeeRepo.save(any(Employee.class))).thenReturn(employee);
        
        ServiceResult result = employeeService.add(employeeDTO);
        
        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
    }
    
    @Test
    public void testAdd_InvalidDTO_Error(){
        try (MockedStatic<EmployeeMapper> mock = mockStatic(EmployeeMapper.class)) {

            mock.when(() -> EmployeeMapper.isValidDTO(any())).thenReturn(false);

            ServiceResult result = employeeService.add(employeeDTO);
        
            assertFalse(result.isSuccess());
        }
    }
    
    @Test
    public void testAdd_Exception_Error(){
        when(employeeRepo.save(any())).thenThrow(new RuntimeException("DB error"));
        
        ServiceResult result = employeeService.add(employeeDTO);
        
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Error while adding employee"));
    }




    /**
     * Test of update method, of class EmployeeService.
     */
    @Test
    public void testUpdate_Valid_Success(){
        when(employeeRepo.findById(1L)).thenReturn(Optional.of(employee));
        lenient().when(employeeRepo.existsByUsername(any(String.class))).thenReturn(false);
        when(employeeRepo.save(any())).thenReturn(employee);
        
        ServiceResult result = employeeService.update(employeeDTO);
        
        assertTrue(result.isSuccess());
    }
    
    @Test
    public void testUpdate_NotFound_Error() {
        when(employeeRepo.findById(1L)).thenReturn(Optional.empty());
        
        ServiceResult result = employeeService.update(employeeDTO);
        
        assertFalse(result.isSuccess());
    }
    
    @Test
    public void testUpdate_UsernameExists_Error() {
        employeeDTO.setUsername("newUser");
        
        when(employeeRepo.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepo.existsByUsername("newUser")).thenReturn(true);
        
        ServiceResult result = employeeService.update(employeeDTO);
        
        assertFalse(result.isSuccess());
    }




    /**
     * Test of getEmployee method, of class EmployeeService.
     */
    @Test
    public void testGetEmployee_Success() {
        when(employeeRepo.findById(1L)).thenReturn(Optional.of(employee));
        
        ServiceResult result = employeeService.getEmployee(1L);
        
        assertTrue(result.isSuccess());
    }
    
    @Test
    public void testGetEmployee_NotFound() {
        when(employeeRepo.findById(1L)).thenReturn(Optional.empty());
        
        ServiceResult result = employeeService.getEmployee(1L);
        
        assertFalse(result.isSuccess());
    }



    /**
     * Test of getAllEmployees method, of class EmployeeService.
     */
    @Test
    public void testGetAllEmployees_Success() {
        when(employeeRepo.findAll()).thenReturn(List.of(employee));
        
        ServiceResult result = employeeService.getAllEmployees();
        
        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
    }


    /**
     * Test of delete method, of class EmployeeService.
     */
    @Test
    public void testDelete_Success() {
        when(employeeRepo.findById(1L)).thenReturn(Optional.of(employee));
        when(repairRepo.existsByIdEmployee(employee)).thenReturn(false);
        
        ServiceResult result = employeeService.delete(1L);
        
        assertTrue(result.isSuccess());
    }
    
    @Test
    public void testDelete_HasRepairs_Error() {
        when(employeeRepo.findById(1L)).thenReturn(Optional.of(employee));
        when(repairRepo.existsByIdEmployee(employee)).thenReturn(true);
        
        ServiceResult result = employeeService.delete(1L);
        
        assertFalse(result.isSuccess());
    }
    
    /**
     * Test of getEmployeeRepairs method, of class EmployeeService.
     */
    @Test
    public void testGetEmployeeRepairs_Success() {
        when(employeeRepo.findById(1L)).thenReturn(Optional.of(employee));
        when(repairRepo.findByIdEmployee(employee)).thenReturn(List.of());
        
        ServiceResult result = employeeService.getEmployeeRepairs(1L);
        
        assertTrue(result.isSuccess());
    }
    
    @Test
    public void testGetEmployeeRepairs_NotFound() {
        when(employeeRepo.findById(1L)).thenReturn(Optional.empty());
        
        ServiceResult result = employeeService.getEmployeeRepairs(1L);
        
        assertFalse(result.isSuccess());
    }


    
}
