package com.epam.spring.homework3.repository;

import com.epam.spring.homework3.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
