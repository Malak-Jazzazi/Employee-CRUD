package com.example.employee.shared.mapper;

import org.mapstruct.MappingTarget;

public interface BaseMapper<E, Req, Res> {

    E toEntity(Req request);

    Res toResponse(E entity);

    void updateEntity(Req request, @MappingTarget E entity);
}
