package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.controller.assembler.ReceiptAssembler;
import com.epam.spring.homework3.controller.model.ReceiptModel;
import com.epam.spring.homework3.dto.ReceiptDto;
import com.epam.spring.homework3.service.ReceiptService;
import com.epam.spring.homework3.test.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.epam.spring.homework3.test.util.TestDataUtil.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReceiptController.class)
@Import(TestConfig.class)
class ReceiptControllerTest {

    public static final String RECEIPTS_URL = "/api/v1/receipts";

    @MockBean
    private ReceiptService receiptService;

    @MockBean
    private ReceiptAssembler receiptAssembler;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllReceiptsTest() throws Exception {
        ReceiptDto receiptDto = createReceiptDto();
        ReceiptModel receiptModel = new ReceiptModel(receiptDto);

        when(receiptService.findAll()).thenReturn(Collections.singletonList(receiptDto));
        when(receiptAssembler.toModel(receiptDto)).thenReturn(receiptModel);

        mockMvc.perform(get(RECEIPTS_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].totalPrice").value(receiptDto.getTotalPrice()));
        verify(receiptService).findAll();
    }

    @Test
    void getReceiptTest() throws Exception {
        ReceiptDto receiptDto = createReceiptDto();
        ReceiptModel receiptModel = new ReceiptModel(receiptDto);

        when(receiptService.findById(anyLong())).thenReturn(receiptDto);
        when(receiptAssembler.toModel(receiptDto)).thenReturn(receiptModel);

        mockMvc.perform(get(RECEIPTS_URL + "/" + ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.totalPrice").value(receiptDto.getTotalPrice()));
        verify(receiptService).findById(anyLong());
    }

    @Test
    void makeOrderTest() throws Exception {
        ReceiptDto receiptDto = createReceiptDto();
        ReceiptModel receiptModel = new ReceiptModel(receiptDto);

        when(receiptService.makeOrder(anyLong())).thenReturn(receiptDto);
        when(receiptAssembler.toModel(receiptDto)).thenReturn(receiptModel);

        mockMvc.perform(post(RECEIPTS_URL + "/" + ID))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.totalPrice").value(receiptDto.getTotalPrice()));
        verify(receiptService).makeOrder(anyLong());
    }

    @Test
    void nextStatusTest() throws Exception {
        ReceiptDto receiptDto = createReceiptDto();
        ReceiptModel receiptModel = new ReceiptModel(receiptDto);

        when(receiptService.nextStatus(anyLong(), anyLong())).thenReturn(receiptDto);
        when(receiptAssembler.toModel(receiptDto)).thenReturn(receiptModel);

        mockMvc.perform(patch(RECEIPTS_URL + "/next-status/1/manager/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPrice").value(receiptDto.getTotalPrice()));
        verify(receiptService).nextStatus(anyLong(), anyLong());
    }

    @Test
    void cancelOrRenewReceiptTest() throws Exception {
        ReceiptDto receiptDto = createReceiptDto();
        ReceiptModel receiptModel = new ReceiptModel(receiptDto);

        when(receiptService.cancelOrRenewReceipt(anyLong(), anyLong())).thenReturn(receiptDto);
        when(receiptAssembler.toModel(receiptDto)).thenReturn(receiptModel);

        mockMvc.perform(patch(RECEIPTS_URL + "/cancel/1/manager/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPrice").value(receiptDto.getTotalPrice()));
        verify(receiptService).cancelOrRenewReceipt(anyLong(), anyLong());
    }

}
