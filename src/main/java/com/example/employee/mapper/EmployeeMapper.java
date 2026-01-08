package com.example.employee.mapper;

import com.example.employee.model.dto.request.EmployeeRequest;
import com.example.employee.model.dto.response.EmployeeResponse;
import com.example.employee.model.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

     Employee employeeRequestToEmployeeEntity(EmployeeRequest employeeRequest);

     EmployeeResponse employeeEntityToEmployeeResponse(Employee employee);
}
