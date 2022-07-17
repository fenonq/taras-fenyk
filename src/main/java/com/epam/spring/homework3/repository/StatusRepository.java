package com.epam.spring.homework3.repository;

import com.epam.spring.homework3.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {

    Status findByName(String name);

}
