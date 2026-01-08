package com.example.employee.controller;

import com.example.employee.model.dto.request.EmployeeRequest;
import com.example.employee.model.dto.response.EmployeeResponse;
import com.example.employee.model.entity.Employee;
import com.example.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse addEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) {
        return employeeService.createEmployee(employeeRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse getEmployeeById(@PathVariable UUID id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("{id}")
    public EmployeeResponse updateEmployee(@PathVariable UUID id,
                                           @RequestBody @Valid EmployeeRequest employeeRequest) {
        return employeeService.updateEmployee(id , employeeRequest);
    }

    @DeleteMapping("{id}")
    public Boolean deleteEmployee(@PathVariable UUID id) {
        return employeeService.deleteEmployee(id);
    }

    @GetMapping()
    public Page<Employee> searchEmployees(
            @RequestParam(required = false) String search,
            Pageable pageable
    ) {
        return employeeService.searchEmployees(search, pageable);}
}
