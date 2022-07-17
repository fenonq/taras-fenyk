package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.api.ReceiptApi;
import com.epam.spring.homework3.controller.assembler.ReceiptAssembler;
import com.epam.spring.homework3.controller.model.ReceiptModel;
import com.epam.spring.homework3.dto.ReceiptDto;
import com.epam.spring.homework3.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReceiptController implements ReceiptApi {

    private final ReceiptService receiptService;
    private final ReceiptAssembler receiptAssembler;

    @Override
    public List<ReceiptModel> getAllReceipts() {
        log.info("find all receipts");
        List<ReceiptDto> outReceiptDtoList = receiptService.findAll();
        return outReceiptDtoList.stream().map(receiptAssembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public ReceiptModel getReceipt(Long id) {
        log.info("find receipt with id {}", id);
        ReceiptDto outReceiptDto = receiptService.findById(id);
        return receiptAssembler.toModel(outReceiptDto);
    }

    @Override
    public ReceiptModel makeOrder(Long userId) {
        log.info("make an order");
        ReceiptDto outReceiptDto = receiptService.makeOrder(userId);
        return receiptAssembler.toModel(outReceiptDto);
    }

    @Override
    public ResponseEntity<Void> deleteReceipt(Long id) {
        log.info("delete receipt with id {}", id);
        receiptService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ReceiptModel nextStatus(Long receiptId, Long managerId) {
        log.info("change status in receipt with id {}", receiptId);
        ReceiptDto outReceiptDto = receiptService.nextStatus(receiptId, managerId);
        return receiptAssembler.toModel(outReceiptDto);
    }

    @Override
    public ReceiptModel cancelOrRenewReceipt(Long receiptId, Long managerId) {
        log.info("cancel/renew receipt with id {}", receiptId);
        ReceiptDto outReceiptDto = receiptService.cancelOrRenewReceipt(receiptId, managerId);
        return receiptAssembler.toModel(outReceiptDto);
    }

}
