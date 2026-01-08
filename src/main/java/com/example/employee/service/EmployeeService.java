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
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository ,
                           EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeMapper = employeeMapper;
    }

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        if (employeeRepository.existsByEmail(employeeRequest.getEmail())) {
            throw new EmailAlreadyExistsException(employeeRequest.getEmail());
        }

        departmentRepository.findById(employeeRequest.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department with Id not Found : "  ,employeeRequest.getDepartmentId()));


        Employee employee = employeeMapper.employeeRequestToEmployeeEntity(employeeRequest);
        employee.setIsDeleted(false);
        employeeRepository.save(employee);
        return employeeMapper.employeeEntityToEmployeeResponse(employee);
    }

    public EmployeeResponse getEmployeeById(UUID id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(employeeMapper::employeeEntityToEmployeeResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with Id not Found : "  , id));
    }

    public EmployeeResponse updateEmployee(UUID id, EmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: ",
                                UUID.fromString(id.toString())
                        )
                );

        Employee updatedEmployee = employeeMapper.employeeRequestToEmployeeEntity(employeeRequest);
        updatedEmployee.setId(id);
        employeeRepository.save(updatedEmployee);
        return employeeMapper.employeeEntityToEmployeeResponse(updatedEmployee);

    }
}
