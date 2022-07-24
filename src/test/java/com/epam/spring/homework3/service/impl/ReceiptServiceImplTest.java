package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.ReceiptDto;
import com.epam.spring.homework3.exception.EntityNotFoundException;
import com.epam.spring.homework3.mapper.DishMapper;
import com.epam.spring.homework3.mapper.UserMapper;
import com.epam.spring.homework3.model.Dish;
import com.epam.spring.homework3.model.Receipt;
import com.epam.spring.homework3.model.Status;
import com.epam.spring.homework3.model.User;
import com.epam.spring.homework3.repository.ReceiptRepository;
import com.epam.spring.homework3.repository.StatusRepository;
import com.epam.spring.homework3.repository.UserRepository;
import com.epam.spring.homework3.test.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceImplTest {

    @InjectMocks
    private ReceiptServiceImpl receiptService;

    @Mock
    private ReceiptRepository receiptRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StatusRepository statusRepository;

    @Test
    void findAllTest() {
        List<Receipt> receipts = List.of(
                TestDataUtil.createReceipt(),
                TestDataUtil.createReceipt()
        );
        when(receiptRepository.findAll()).thenReturn(receipts);

        List<ReceiptDto> returnedReceipts = receiptService.findAll();

        verify(receiptRepository).findAll();
        assertThat(returnedReceipts, hasSize(receipts.size()));
    }

    @Test
    void findByIdTest() {
        Receipt receipt = TestDataUtil.createReceipt();
        when(receiptRepository.findById(anyLong())).thenReturn(Optional.of(receipt));

        ReceiptDto returnedReceipt = receiptService.findById(anyLong());

        verify(receiptRepository).findById(anyLong());
        assertThat(returnedReceipt, allOf(
                hasProperty("id", equalTo(receipt.getId())),
                hasProperty("createDate", equalTo(receipt.getCreateDate()))
        ));
    }

    @Test
    void findByIdNotFoundTest() {
        when(receiptRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> receiptService.findById(anyLong()));
    }

    @Test
    void saveTest() {
        Receipt receipt = TestDataUtil.createReceipt();
        ReceiptDto receiptDto = TestDataUtil.createReceiptDto();
        when(receiptRepository.save(any())).thenReturn(receipt);

        ReceiptDto returnedReceipt = receiptService.save(receiptDto);

        assertThat(returnedReceipt, allOf(
                hasProperty("id", equalTo(receiptDto.getId())),
                hasProperty("customer", equalTo(receiptDto.getCustomer()))
        ));
    }

    @Test
    void updateTest() {
        Receipt receipt = TestDataUtil.createReceipt();
        ReceiptDto receiptDto = TestDataUtil.createReceiptDto();
        when(receiptRepository.findById(anyLong())).thenReturn(Optional.of(receipt));
        when(receiptRepository.save(any())).thenReturn(receipt);

        ReceiptDto returnedReceipt = receiptService.update(receipt.getId(), receiptDto);

        assertThat(returnedReceipt, allOf(
                hasProperty("id", equalTo(receiptDto.getId())),
                hasProperty("customer", equalTo(receiptDto.getCustomer()))
        ));
    }

    @Test
    void updateNotFoundTest() {
        ReceiptDto receiptDto = TestDataUtil.createReceiptDto();

        when(receiptRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> receiptService.update(receiptDto.getId(), receiptDto));
    }

    @Test
    void deleteByIdTest() {
        Receipt receipt = TestDataUtil.createReceipt();
        when(receiptRepository.findById(anyLong())).thenReturn(Optional.of(receipt));

        receiptService.deleteById(anyLong());

        verify(receiptRepository).delete(receipt);
    }

    @Test
    void deleteByIdNotFoundTest() {
        when(receiptRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> receiptService.deleteById(anyLong()));
    }

    @Test
    void makeOrderTest() {
        User customer = TestDataUtil.createUserCustomer();
        Dish dish = TestDataUtil.createDish();
        customer.getCart().add(dish);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        ReceiptDto returnedReceipt = receiptService.makeOrder(customer.getId());

        verify(receiptRepository).save(any());
        assertThat(returnedReceipt, allOf(
                hasProperty("dishes", hasItem(DishMapper.INSTANCE.mapDishDto(dish))),
                hasProperty("customer", equalTo(UserMapper.INSTANCE.mapUserDto(customer)))
        ));
    }

    @Test
    void makeOrderEmptyCartTest() {
        User customer = TestDataUtil.createUserCustomer();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        assertThrows(EntityNotFoundException.class,
                () -> receiptService.makeOrder(customer.getId()));
    }

    @Test
    void nextStatusTest() {
        User manager = TestDataUtil.createUserManager();
        Receipt receipt = TestDataUtil.createReceipt();
        Status nextStatus = TestDataUtil.createStatus(2L, "Accepted");
        Status statusDone = TestDataUtil.createStatus(3L, "Done");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(manager));
        when(receiptRepository.findById(anyLong())).thenReturn(Optional.of(receipt));
        when(statusRepository.findByName(anyString())).thenReturn(statusDone);
        when(statusRepository.findById(anyLong())).thenReturn(Optional.of(nextStatus));

        assertNotEquals(nextStatus, receipt.getStatus());

        receiptService.nextStatus(receipt.getId(), manager.getId());

        verify(receiptRepository).save(any());
        assertThat(receipt, hasProperty("status", equalTo(nextStatus)));
    }

    @Test
    void nextStatusManagerNotFoundTest() {
        Receipt receipt = TestDataUtil.createReceipt();

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> receiptService.nextStatus(receipt.getId(), anyLong()));
    }

    @Test
    void nextStatusReceiptNotFoundTest() {
        User manager = TestDataUtil.createUserManager();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(manager));
        when(receiptRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> receiptService.nextStatus(anyLong(), manager.getId()));
    }


    @Test
    void nextStatusUserNotManagerTest() {
        User user = TestDataUtil.createUserCustomer();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        assertThrows(EntityNotFoundException.class,
                () -> receiptService.nextStatus(anyLong(), user.getId()));
    }

    @Test
    void cancelOrRenewReceiptTest() {
        String accepted = "Accepted";
        String canceled = "Canceled";

        User manager = TestDataUtil.createUserManager();
        Receipt receipt = TestDataUtil.createReceipt();
        Status statusAccepted = TestDataUtil.createStatus(2L, accepted);
        Status statusCanceled = TestDataUtil.createStatus(3L, canceled);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(manager));
        when(receiptRepository.findById(anyLong())).thenReturn(Optional.of(receipt));
        when(statusRepository.findByName(accepted)).thenReturn(statusAccepted);
        when(statusRepository.findByName(canceled)).thenReturn(statusCanceled);

        receiptService.cancelOrRenewReceipt(receipt.getId(), manager.getId());
        assertThat(receipt, hasProperty("status", equalTo(statusCanceled)));

        receiptService.cancelOrRenewReceipt(receipt.getId(), manager.getId());
        assertThat(receipt, hasProperty("status", equalTo(statusAccepted)));
    }

    @Test
    void cancelOrRenewReceiptManagerNotFoundTest() {
        Receipt receipt = TestDataUtil.createReceipt();

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> receiptService.cancelOrRenewReceipt(receipt.getId(), anyLong()));
    }

    @Test
    void cancelOrRenewReceiptReceiptNotFoundTest() {
        User manager = TestDataUtil.createUserManager();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(manager));
        when(receiptRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> receiptService.cancelOrRenewReceipt(anyLong(), manager.getId()));
    }

    @Test
    void cancelOrRenewReceiptUserNotManagerTest() {
        User user = TestDataUtil.createUserCustomer();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        assertThrows(EntityNotFoundException.class,
                () -> receiptService.cancelOrRenewReceipt(anyLong(), user.getId()));
    }

}
