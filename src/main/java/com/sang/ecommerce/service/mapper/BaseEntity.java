package com.sang.ecommerce.service.mapper;

public interface BaseEntity<D, E> {
    E toEntity(D dto);
    D toDto (E entity);
}
