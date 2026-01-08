package com.example.employee.mapper;

import com.example.employee.model.dto.request.EmployeeRequest;
import com.example.employee.model.dto.response.EmployeeResponse;
import com.example.employee.model.entity.Department;
import com.example.employee.model.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

     @Mapping(source = "departmentId", target = "department")
     Employee employeeRequestToEmployeeEntity(EmployeeRequest employeeRequest);

     @Mapping(source = "department.id", target = "department.id")
     @Mapping(source = "department.name", target = "department.name")
     EmployeeResponse employeeEntityToEmployeeResponse(Employee employee);

     default Department map(UUID departmentId) {
          if (departmentId == null) return null;
          Department department = new Department();
          department.setId(departmentId);
          return department;
     }
}
