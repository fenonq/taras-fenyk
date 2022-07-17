package com.epam.spring.homework3.service;

import com.epam.spring.homework3.dto.ReceiptDto;

public interface ReceiptService extends CrudService<ReceiptDto, Long> {

    ReceiptDto makeOrder(Long userId);

    ReceiptDto nextStatus(Long receiptId, Long managerId);

    ReceiptDto cancelOrRenewReceipt(Long receiptId, Long managerId);

}
