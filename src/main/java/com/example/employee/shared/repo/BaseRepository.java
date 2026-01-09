package com.example.employee.shared.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<E, ID>
        extends JpaRepository<E, ID> {

    Optional<E> findByIdAndIsDeletedFalse(ID id);

    List<E> findAllByIsDeletedFalse();

    Page<E> findAllByIsDeletedFalse(Pageable pageable);

    boolean existsByIdAndIsDeletedFalse(ID id);

}