package com.epam.spring.homework3.repository;

import java.util.List;

public interface CrudRepository<T, ID> {

    List<T> findAll();

    T findById(ID id);

    T save(T object);

    T update(ID id, T object);

    void deleteById(ID id);

}
