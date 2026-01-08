package com.example.employee.specification;

import com.example.employee.model.entity.Employee;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    public static Specification<Employee> globalSearch(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;
            }

            String likePattern = "%" + keyword.toLowerCase() + "%";

            Join<Object, Object> departmentJoin =
                    root.join("department", JoinType.LEFT);

            return cb.or(
                    cb.like(cb.lower(root.get("firstName")), likePattern),
                    cb.like(cb.lower(root.get("lastName")), likePattern),
                    cb.like(cb.lower(root.get("email")), likePattern),
                    cb.like(cb.lower(root.get("phoneNumber")), likePattern),
                    cb.like(cb.lower(root.get("jobTitle")), likePattern),
                    cb.like(cb.lower(departmentJoin.get("name")), likePattern)
            );
        };
    }

    public static Specification<Employee> isNotDeleted() {
        return (root, query, cb) ->
                cb.isFalse(root.get("isDeleted"));
    }

}
