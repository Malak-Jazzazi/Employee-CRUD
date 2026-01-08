package com.example.employee.shared.controller;

import com.example.employee.shared.service.BaseCrudService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public abstract class BaseController<ID, Req, Res> {

    protected abstract BaseCrudService<?, ID, Req, Res> getService();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Res create(@RequestBody @Valid Req request) {
        return getService().create(request);
    }

    @GetMapping("/{id}")
    public Res getById(@PathVariable ID id) {
        return getService().getById(id);
    }

    @PutMapping("/{id}")
    public Res update(@PathVariable ID id,
                      @RequestBody @Valid Req request) {
        return getService().update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable ID id) {
        getService().delete(id);
    }
}
