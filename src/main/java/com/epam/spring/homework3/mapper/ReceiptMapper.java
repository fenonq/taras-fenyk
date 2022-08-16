package com.epam.spring.homework3.mapper;

import com.epam.spring.homework3.dto.ReceiptDto;
import com.epam.spring.homework3.model.Receipt;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReceiptMapper {

    ReceiptMapper INSTANCE = Mappers.getMapper(ReceiptMapper.class);

    Receipt mapReceipt(ReceiptDto receiptDto);

    ReceiptDto mapReceiptDto(Receipt receipt);

}
