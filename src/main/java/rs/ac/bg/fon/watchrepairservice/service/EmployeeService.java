/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.watchrepairservice.communication.ServiceResult;
import rs.ac.bg.fon.watchrepairservice.dto.EmployeeDTO;
import rs.ac.bg.fon.watchrepairservice.dto.RepairDTO;
import rs.ac.bg.fon.watchrepairservice.entity.Employee;
import rs.ac.bg.fon.watchrepairservice.entity.Repair;
import rs.ac.bg.fon.watchrepairservice.mapper.EmployeeMapper;
import rs.ac.bg.fon.watchrepairservice.mapper.RepairMapper;
import rs.ac.bg.fon.watchrepairservice.repository.EmployeeRepository;
import rs.ac.bg.fon.watchrepairservice.repository.RepairRepository;

/**
 *
 * @author Marko
 */
@Service
@Transactional
public class EmployeeService {
    private final RepairRepository repairRepo;
    private final EmployeeRepository employeeRepo;

    public EmployeeService(RepairRepository repairRepo, EmployeeRepository employeeRepo) {
        this.repairRepo = repairRepo;
        this.employeeRepo = employeeRepo;
    }
    
    public ServiceResult add(EmployeeDTO dto) {
        try {
            if (!EmployeeMapper.isValidDTO(dto)) {
                return ServiceResult.errorMessage("Invalid employee data. All data is required.");
            }
            
            // toEntity without watches!! Check toEntity method
            Employee employee = EmployeeMapper.toEntity(dto);
            
            Employee saved = employeeRepo.save(employee);
            
            return ServiceResult.successMessageData(
                "Watch added successfully.", 
                EmployeeMapper.toDTO(saved)
            );
            
        } catch (EntityNotFoundException e) {
            return ServiceResult.errorMessage(e.getMessage());
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while adding employee: " + e.getMessage());
        }
    }
    
    public ServiceResult update(EmployeeDTO dto) {
        try {
            Employee existing = employeeRepo.findById(dto.getIdEmployee())
                    .orElseThrow(() -> new EntityNotFoundException("Watch not employee."));
            
            if (!EmployeeMapper.isValidDTO(dto)) {
                return ServiceResult.errorMessage("Invalid employee data.");
            }
            
            if (!dto.getUsername().equals(existing.getUsername()) && 
                employeeRepo.existsByUsername(dto.getUsername())) {
                return ServiceResult.errorMessage("Employee with this username already exists.");
            }
            
            EmployeeMapper.updateEntityFromDTO(dto, existing);
            
            Employee updated = employeeRepo.save(existing);
            
            return ServiceResult.successMessageData(
                "Client updated successfully.", 
                EmployeeMapper.toDTO(updated)
            );
            
        }catch (Exception e) {
            return ServiceResult.errorMessage("Error while updating client: " + e.getMessage());
        }
    }
    
    public ServiceResult getEmployee(Long id) {
        try {
            Employee employee = employeeRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found."));
            
            return ServiceResult.successMessageData(
                "Employee found.", 
                EmployeeMapper.toDTO(employee)
            );
            
        } catch (EntityNotFoundException e) {
            return ServiceResult.errorMessage(e.getMessage());
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while fetching employee: " + e.getMessage());
        }
    }
    
    public ServiceResult getAllEmployees() {
        List<EmployeeDTO> employeesDTO = EmployeeMapper.toDTOList(employeeRepo.findAll());
        return ServiceResult.successMessageData("All employees loaded.", employeesDTO);
    }
    
    public ServiceResult delete(Long id) {
        try {
            Employee employee = employeeRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found."));
            
            if (repairRepo.existsByIdEmployee(employee)) {
                return ServiceResult.errorMessage("Cannot delete employee that was part of repair history.");
            }
            
            employeeRepo.deleteById(id);
            
            return ServiceResult.successMessage("Employee deleted successfully.");
            
        } catch (DataIntegrityViolationException e) {
            return ServiceResult.errorMessage("Cannot delete employee that was part of repair history.");
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while deleting employee: " + e.getMessage());
        }
    }
    
    public ServiceResult getEmployeeRepairs(Long id) {
        try {
            Employee employee = employeeRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found."));
            
            List<Repair> repairs = repairRepo.findByIdEmployee(employee);
            List<RepairDTO> repairsDTO = RepairMapper.toDTOList(repairs);
            
            return ServiceResult.successMessageData(
                "Employee's repairs loaded.", 
                repairsDTO
            );
            
        } catch (EntityNotFoundException e) {
            return ServiceResult.errorMessage(e.getMessage());
        } catch (Exception e) {
            return ServiceResult.errorMessage("Error while fetching repairs: " + e.getMessage());
        }
    }
    
}
