package com.epam.spring.homework3.repository;

import com.epam.spring.homework3.model.Status;

public interface StatusRepository extends CrudRepository<Status, Long> {

    Status findByName(String name);

}
