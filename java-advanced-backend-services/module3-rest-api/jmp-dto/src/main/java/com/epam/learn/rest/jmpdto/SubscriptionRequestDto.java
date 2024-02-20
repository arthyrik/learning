package com.epam.learn.rest.jmpdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubscriptionRequestDto {
    private Long id;
    @NotBlank
    private Long userId;
}
