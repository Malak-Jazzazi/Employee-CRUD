package com.example.employee.shared.controller;

import com.example.employee.shared.service.BaseCrudService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("api/v1/")
public abstract class BaseController<ID, Req, Res> {

    protected abstract BaseCrudService<?, ID, Req, Res> getService();

    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Res create(@RequestBody @Valid Req request) {
        return getService().create(request);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/{id}")
    public Res getById(@PathVariable ID id) {
        return getService().getById(id);
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/{id}")
    public Res update(@PathVariable ID id,
                      @RequestBody @Valid Req request) {
        return getService().update(id, request);
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable ID id) {
        getService().delete(id);
    }
}
