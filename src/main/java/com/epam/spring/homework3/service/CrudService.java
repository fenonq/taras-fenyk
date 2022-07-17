package com.epam.spring.homework3.service;

import java.util.List;

public interface CrudService<T, ID> {

    List<T> findAll();

    T findById(ID id);

    T save(T object);

    T update(ID id, T object);

    void deleteById(ID id);

}
