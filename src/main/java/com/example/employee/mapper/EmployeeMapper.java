package com.example.employee.mapper;

import com.example.employee.model.dto.request.EmployeeRequest;
import com.example.employee.model.dto.response.EmployeeResponse;
import com.example.employee.model.entity.Department;
import com.example.employee.model.entity.Employee;
import com.example.employee.shared.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EmployeeMapper
        extends BaseMapper<Employee, EmployeeRequest, EmployeeResponse> {

    @Mapping(source = "departmentId", target = "department")
    Employee toEntity(EmployeeRequest employeeRequest);

     default Department map(UUID departmentId) {
          if (departmentId == null) return null;
          Department department = new Department();
          department.setId(departmentId);
          return department;
     }
}
