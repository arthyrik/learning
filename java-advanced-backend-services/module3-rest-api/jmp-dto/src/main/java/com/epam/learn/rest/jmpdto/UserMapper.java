package com.epam.learn.rest.jmpdto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "birthday", expression = "java(java.time.LocalDate.parse(userRequestDto.getBirthday()))")
    User toUser(UserRequestDto userRequestDto);

    UserResponseDto toUserResponseDto(User user);
}
