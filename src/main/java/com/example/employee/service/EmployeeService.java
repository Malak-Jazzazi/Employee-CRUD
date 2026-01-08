package com.example.employee.service;

import com.example.employee.exception.EmailAlreadyExistsException;
import com.example.employee.exception.ResourceNotFoundException;
import com.example.employee.mapper.EmployeeMapper;
import com.example.employee.model.dto.request.EmployeeRequest;
import com.example.employee.model.dto.response.EmployeeResponse;
import com.example.employee.model.entity.Department;
import com.example.employee.model.entity.Employee;
import com.example.employee.repo.DepartmentRepository;
import com.example.employee.repo.EmployeeRepository;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new ResourceNotFoundException(employeeRequest.getDepartmentId()));


        Employee employee = employeeMapper.employeeRequestToEmployeeEntity(employeeRequest);
        employee.setIsDeleted(false);
        employeeRepository.save(employee);
        return employeeMapper.employeeEntityToEmployeeResponse(employee);
    }
}
