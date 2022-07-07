package com.epam.spring.homework3.repository.impl;

import com.epam.spring.homework3.model.Receipt;
import com.epam.spring.homework3.repository.ReceiptRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ReceiptRepositoryImpl implements ReceiptRepository {

    private static Long id = 0L;
    private final List<Receipt> list = new ArrayList<>();

    @Override
    public List<Receipt> findAll() {
        log.info("find all receipts");
        return new ArrayList<>(list);
    }

    @Override
    public Receipt findById(Long id) {
        log.info("find receipt with id {}", id);
        return list.stream()
                .filter(receipt -> receipt.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Receipt is not found!"));
    }

    @Override
    public Receipt save(Receipt receipt) {
        log.info("save receipt with id {}", receipt.getId());
        receipt.setId(++id);
        list.add(receipt);
        return receipt;
    }

    @Override
    public Receipt update(Long id, Receipt receipt) {
        log.info("update receipt with id {}", id);
        boolean isDeleted = list.removeIf(r -> r.getId().equals(id));
        if (isDeleted) {
            list.add(receipt);
        } else {
            throw new RuntimeException("Receipt is not found!");
        }
        return receipt;
    }

    @Override
    public void deleteById(Long id) {
        log.info("delete receipt with id {}", id);
        list.removeIf(receipt -> receipt.getId().equals(id));
    }
}
