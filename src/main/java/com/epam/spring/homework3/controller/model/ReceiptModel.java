package com.epam.spring.homework3.controller.model;

import com.epam.spring.homework3.dto.ReceiptDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReceiptModel extends RepresentationModel<ReceiptModel> {

    @JsonUnwrapped
    private ReceiptDto receiptDto;

}
