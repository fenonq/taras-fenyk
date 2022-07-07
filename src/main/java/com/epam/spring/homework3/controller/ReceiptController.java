package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.api.ReceiptApi;
import com.epam.spring.homework3.dto.ReceiptDto;
import com.epam.spring.homework3.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReceiptController implements ReceiptApi {

    private final ReceiptService receiptService;

    @Override
    public List<ReceiptDto> getAllReceipts() {
        log.info("find all receipts");
        return receiptService.findAll();
    }

    @Override
    public ReceiptDto getReceipt(Long id) {
        log.info("find receipt with id {}", id);
        return receiptService.findById(id);
    }

    @Override
    public ReceiptDto makeOrder(Long userId) {
        log.info("make an order");
        return receiptService.makeOrder(userId);
    }

    @Override
    public ResponseEntity<Void> deleteReceipt(Long id) {
        log.info("delete receipt with id {}", id);
        receiptService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ReceiptDto nextStatus(Long receiptId, Long managerId) {
        log.info("change status in receipt with id {}", receiptId);
        return receiptService.nextStatus(receiptId, managerId);
    }

    @Override
    public ReceiptDto cancelOrRenewReceipt(Long receiptId, Long managerId) {
        log.info("cancel/renew receipt with id {}", receiptId);
        return receiptService.cancelOrRenewReceipt(receiptId, managerId);
    }

}
