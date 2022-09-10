package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.ReceiptDto;
import com.epam.spring.homework3.exception.EntityNotFoundException;
import com.epam.spring.homework3.mapper.ReceiptMapper;
import com.epam.spring.homework3.model.*;
import com.epam.spring.homework3.model.enums.Roles;
import com.epam.spring.homework3.repository.ReceiptRepository;
import com.epam.spring.homework3.repository.StatusRepository;
import com.epam.spring.homework3.repository.UserRepository;
import com.epam.spring.homework3.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        return receiptRepository.findAll()
                .stream()
                .map(ReceiptMapper.INSTANCE::mapReceiptDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReceiptDto findById(Long id) {
        log.info("find receipt with id {}", id);
        Receipt receipt = receiptRepository.findById(id);
        return ReceiptMapper.INSTANCE.mapReceiptDto(receipt);
    }

    @Override
    public ReceiptDto save(ReceiptDto receiptDto) {
        log.info("save receipt");
        Receipt receipt = receiptRepository.save(ReceiptMapper.INSTANCE.mapReceipt(receiptDto));
        return ReceiptMapper.INSTANCE.mapReceiptDto(receipt);
    }

    @Override
    public ReceiptDto update(Long id, ReceiptDto receiptDto) {
        log.info("update receipt with id {}", id);
        Receipt receipt = receiptRepository.update(id,
                ReceiptMapper.INSTANCE.mapReceipt(receiptDto));
        return ReceiptMapper.INSTANCE.mapReceiptDto(receipt);
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

        if (user.getCart().isEmpty()) {
            throw new EntityNotFoundException("User cart is empty!");
        }

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

        return ReceiptMapper.INSTANCE.mapReceiptDto(receipt);
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

        if (receipt.getManager() == null) {
            receipt.setManager(manager);
        }

        if (!receipt.getStatus().getId().equals(statusDone.getId()) &&
                !receipt.getStatus().getId().equals(statusCanceled.getId()) &&
                receipt.getManager().getId().equals(managerId)) {

            receipt.setStatus(statusRepository.findById(receipt.getStatus().getId() + 1));

            receiptRepository.update(receiptId, receipt);
        }
        return ReceiptMapper.INSTANCE.mapReceiptDto(receipt);
    }

    @Override
    public ReceiptDto cancelOrRenewReceipt(Long receiptId, Long managerId) {
        log.info("cancel/renew receipt with id {}", receiptId);

        User manager = userRepository.findById(managerId);
        if (!manager.getRole().equals(Roles.MANAGER)) {
            throw new EntityNotFoundException();
        }

        Receipt receipt = receiptRepository.findById(receiptId);
        Status accepted = statusRepository.findByName("Accepted");
        Status canceled = statusRepository.findByName("Canceled");

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
        return ReceiptMapper.INSTANCE.mapReceiptDto(receipt);
    }
}
