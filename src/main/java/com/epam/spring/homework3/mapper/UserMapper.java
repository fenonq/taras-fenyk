package com.epam.spring.homework3.mapper;

import com.epam.spring.homework3.dto.UserDto;
import com.epam.spring.homework3.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapUser(UserDto userDto);

    UserDto mapUserDto(User user);

}
