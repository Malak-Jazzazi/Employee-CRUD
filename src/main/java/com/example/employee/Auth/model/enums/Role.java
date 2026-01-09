package com.example.employee.Auth.model.enums;

import java.util.Set;

public enum Role {

    EMPLOYEE(Set.of(Permission.READ)),

    EDITOR(Set.of(
            Permission.READ,
            Permission.UPDATE
    )),

    MANAGER(Set.of(
            Permission.READ,
            Permission.WRITE,
            Permission.UPDATE
    )),

    ADMIN(Set.of(
            Permission.READ,
            Permission.WRITE,
            Permission.UPDATE,
            Permission.DELETE
    ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}