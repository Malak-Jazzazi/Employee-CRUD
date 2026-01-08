package com.example.employee.shared.service;

import com.example.employee.shared.exception.ResourceNotFoundException;
import com.example.employee.shared.mapper.BaseMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public abstract class BaseCrudService<E, ID, Req, Res> {

    protected abstract JpaRepository<E, ID> getRepository();
    protected abstract BaseMapper<E, Req, Res> getMapper();

    protected abstract Optional<E> findActiveById(ID id);

    public Res create(Req request) {
        E entity = getMapper().toEntity(request);
        return getMapper().toResponse(getRepository().save(entity));
    }

    public Res getById(ID id) {
        return findActiveById(id)
                .map(getMapper()::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found", id));
    }

    public Res update(ID id, Req request) {
        E entity = findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found", id));

        getMapper().updateEntity(request, entity);
        return getMapper().toResponse(getRepository().save(entity));
    }

    public void delete(ID id) {
        E entity = findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found", id));

        softDelete(entity);
        getRepository().save(entity);
    }

    protected abstract void softDelete(E entity);
}