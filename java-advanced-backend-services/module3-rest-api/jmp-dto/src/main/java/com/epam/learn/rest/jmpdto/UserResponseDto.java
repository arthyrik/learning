package com.epam.learn.rest.jmpdto;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String surname;
    private String birthday;
}
