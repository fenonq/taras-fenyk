package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.ReceiptDto;
import com.epam.spring.homework3.model.*;
import com.epam.spring.homework3.repository.ReceiptRepository;
import com.epam.spring.homework3.repository.StatusRepository;
import com.epam.spring.homework3.repository.UserRepository;
import com.epam.spring.homework3.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;

    @Override
    public List<ReceiptDto> findAll() {
        log.info("find all receipts");

        List<Receipt> receipts = receiptRepository.findAll();
        List<ReceiptDto> toReturn = new ArrayList<>();

        ReceiptDto target;
        for (Receipt source : receipts) {
            target = new ReceiptDto();
            BeanUtils.copyProperties(source, target);
            toReturn.add(target);
        }
        return toReturn;
    }

    @Override
    public ReceiptDto findById(Long id) {
        log.info("find receipt with id {}", id);
        Receipt source = receiptRepository.findById(id);
        ReceiptDto target = new ReceiptDto();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    @Override
    public ReceiptDto save(ReceiptDto receiptDto) {
        log.info("save receipt with id {}", receiptDto.getId());
        Receipt receipt = new Receipt();
        BeanUtils.copyProperties(receiptDto, receipt);
        receipt = receiptRepository.save(receipt);
        BeanUtils.copyProperties(receipt, receiptDto);
        return receiptDto;
    }

    @Override
    public ReceiptDto update(Long id, ReceiptDto receiptDto) {
        log.info("update receipt with id {}", id);
        Receipt receipt = new Receipt();
        BeanUtils.copyProperties(receiptDto, receipt);
        receipt = receiptRepository.update(id, receipt);
        BeanUtils.copyProperties(receipt, receiptDto);
        return receiptDto;
    }

    @Override
    public void deleteById(Long id) {
        log.info("delete receipt with id {}", id);
        receiptRepository.deleteById(id);
    }

    @Override
    public ReceiptDto makeOrder(Long customerId) {
        log.info("make an order");
        User user = userRepository.findById(customerId);
        int totalPrice =
                user.getCart()
                        .stream()
                        .mapToInt(Dish::getPrice)
                        .sum();

        Receipt receipt =
                Receipt
                        .builder()
                        .customer(user)
                        .status(Status
                                .builder()
                                .id(1L)
                                .name("New")
                                .build())
                        .createDate(LocalDateTime.now())
                        .totalPrice(totalPrice)
                        .dishes(new ArrayList<>(user.getCart()))
                        .build();

        receiptRepository.save(receipt);

        user.getCart().clear();

        ReceiptDto receiptDto = new ReceiptDto();
        BeanUtils.copyProperties(receipt, receiptDto);

        return receiptDto;
    }

    @Override
    public ReceiptDto nextStatus(Long receiptId, Long managerId) {
        log.info("change status in receipt with id {}", receiptId);

        User manager = userRepository.findById(managerId);
        if (!manager.getRole().equals(Roles.MANAGER)) {
            throw new RuntimeException("User is not a manager!");
        }

        Receipt receipt = receiptRepository.findById(receiptId);
        Status statusDone = statusRepository.findByName("Done");
        Status statusCanceled = statusRepository.findByName("Canceled");
        ReceiptDto receiptDto = new ReceiptDto();

        if (receipt.getManager() == null) {
            receipt.setManager(manager);
        }

        if (!receipt.getStatus().getId().equals(statusDone.getId()) &&
                !receipt.getStatus().getId().equals(statusCanceled.getId()) &&
                receipt.getManager().getId().equals(managerId)) {

            receipt.setStatus(statusRepository.findById(receipt.getStatus().getId() + 1));

            receiptRepository.update(receiptId, receipt);
        }

        BeanUtils.copyProperties(receipt, receiptDto);
        return receiptDto;
    }

    @Override
    public ReceiptDto cancelOrRenewReceipt(Long receiptId, Long managerId) {
        log.info("cancel/renew receipt with id {}", receiptId);

        User manager = userRepository.findById(managerId);
        if (!manager.getRole().equals(Roles.MANAGER)) {
            throw new RuntimeException("User is not a manager!");
        }

        Receipt receipt = receiptRepository.findById(receiptId);
        Status accepted = statusRepository.findByName("Accepted");
        Status canceled = statusRepository.findByName("Canceled");
        ReceiptDto receiptDto = new ReceiptDto();

        if (receipt.getManager() == null) {
            receipt.setManager(manager);
        }

        if (receipt.getManager().getId().equals(managerId)) {
            if (!receipt.getStatus().getId().equals(canceled.getId())) {
                receipt.setStatus(canceled);
            } else {
                receipt.setStatus(accepted);
            }
        }

        receiptRepository.update(receiptId, receipt);
        BeanUtils.copyProperties(receipt, receiptDto);
        return receiptDto;
    }
}
