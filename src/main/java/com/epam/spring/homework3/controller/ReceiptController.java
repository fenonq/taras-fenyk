package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.dto.ReceiptDto;
import com.epam.spring.homework3.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/receipts")
    public List<ReceiptDto> getAllReceipts() {
        log.info("find all receipts");
        return receiptService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/receipt/{id}")
    public ReceiptDto getReceipt(@PathVariable Long id) {
        log.info("find receipt with id {}", id);
        return receiptService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/receipt/user/{userId}")
    public ReceiptDto makeOrder(@PathVariable Long userId) {
        log.info("make an order");
        return receiptService.makeOrder(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/receipt/{id}")
    public ReceiptDto updateReceipt(@PathVariable Long id, @RequestBody ReceiptDto receiptDto) {
        log.info("update receipt with id {}", id);
        return receiptService.update(id, receiptDto);
    }

    @DeleteMapping(value = "/receipt/{id}")
    public ResponseEntity<Void> deleteReceipt(@PathVariable Long id) {
        log.info("delete receipt with id {}", id);
        receiptService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/receipt/next-status/{receiptId}/manager/{managerId}")
    public ReceiptDto nextStatus(@PathVariable Long receiptId, @PathVariable Long managerId) {
        log.info("change status in receipt with id {}", receiptId);
        return receiptService.nextStatus(receiptId, managerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/receipt/cancel/{receiptId}/manager/{managerId}")
    public ReceiptDto cancelOrRenewReceipt(@PathVariable Long receiptId, @PathVariable Long managerId) {
        log.info("cancel/renew receipt with id {}", receiptId);
        return receiptService.cancelOrRenewReceipt(receiptId, managerId);
    }
}
