package com.epam.learn.rest.jmpdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {
    private Long id;
    @NotBlank(message = "User's name can't be empty")
    private String name;
    @NotBlank(message = "User's surname can't be empty")
    private String surname;
    @NotBlank(message = "User's date of birth can't be empty")
    private String birthday;
}
