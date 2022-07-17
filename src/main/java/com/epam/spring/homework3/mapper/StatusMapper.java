package com.epam.spring.homework3.mapper;

import com.epam.spring.homework3.dto.StatusDto;
import com.epam.spring.homework3.model.Status;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StatusMapper {

    StatusMapper INSTANCE = Mappers.getMapper(StatusMapper.class);

    Status mapStatus(StatusDto statusDto);

    StatusDto mapStatusDto(Status status);

}
