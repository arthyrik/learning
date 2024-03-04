package com.epam.learn.rest.jmpdto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionRequestDto {
    private Long id;
    @NotNull
    private Long userId;
}
