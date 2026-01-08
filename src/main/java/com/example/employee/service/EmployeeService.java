package com.example.employee.service;

import com.example.employee.exception.EmailAlreadyExistsException;
import com.example.employee.exception.ResourceNotFoundException;
import com.example.employee.mapper.EmployeeMapper;
import com.example.employee.model.dto.request.EmployeeRequest;
import com.example.employee.model.dto.response.EmployeeResponse;
import com.example.employee.model.entity.Employee;
import com.example.employee.repo.DepartmentRepository;
import com.example.employee.repo.EmployeeRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);


    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository,
                           EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeMapper = employeeMapper;
    }

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        log.info("Creating employee with email: {}", employeeRequest.getEmail());

        if (employeeRepository.existsByEmail(employeeRequest.getEmail())) {
            log.warn("Employee creation failed. Email already exists: {}", employeeRequest.getEmail());
            throw new EmailAlreadyExistsException(employeeRequest.getEmail());
        }

        departmentRepository.findById(employeeRequest.getDepartmentId())
                .orElseThrow(() -> {
                    log.warn("Department not found with id: {}", employeeRequest.getDepartmentId());
                    return new ResourceNotFoundException(
                            "Department with Id not Found : ",
                            employeeRequest.getDepartmentId()
                    );
                });

        Employee employee = employeeMapper.employeeRequestToEmployeeEntity(employeeRequest);
        employee.setIsDeleted(false);
        employeeRepository.save(employee);
        log.info("Employee created successfully with id: {}", employee.getId());

        return employeeMapper.employeeEntityToEmployeeResponse(employee);
    }

    public EmployeeResponse getEmployeeById(UUID id) {
        log.debug("Fetching employee with id: {}", id);
        Employee employee = employeeRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee with Id not Found : ", id);
                });

        return employeeMapper.employeeEntityToEmployeeResponse(employee);
    }

    public EmployeeResponse updateEmployee(UUID id, EmployeeRequest employeeRequest) {
        log.info("Updating employee with id: {}", id);

        Employee employee = employeeRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("Employee update failed. Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: ", id);
                });

        Employee updatedEmployee = employeeMapper.employeeRequestToEmployeeEntity(employeeRequest);
        updatedEmployee.setId(id);
        updatedEmployee.setIsDeleted(employee.getIsDeleted());
        employeeRepository.save(updatedEmployee);
        log.info("Employee updated successfully with id: {}", id);

        return employeeMapper.employeeEntityToEmployeeResponse(updatedEmployee);
    }

    public Boolean deleteEmployee(UUID id) {
        log.info("Deleting employee (soft delete) with id: {}", id);
        Employee employee = employeeRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("Employee delete failed. Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: ", id);
                });

        employee.setIsDeleted(true);
        employeeRepository.save(employee);
        log.info("Employee soft-deleted successfully with id: {}", id);
        return true;
    }

    public Page<EmployeeResponse> getAllEmployees(Pageable pageable) {
        log.debug(
                "Fetching employees page={}, size={}, sort={}",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort()
        );

        Page<Employee> employeesPage =
                employeeRepository.findAllByIsDeletedFalse(pageable);

        log.info(
                "Employees fetched successfully. page={}, size={}, totalElements={}",
                employeesPage.getNumber(),
                employeesPage.getSize(),
                employeesPage.getTotalElements()
        );

        return employeesPage.map(employeeMapper::employeeEntityToEmployeeResponse);
    }
}
