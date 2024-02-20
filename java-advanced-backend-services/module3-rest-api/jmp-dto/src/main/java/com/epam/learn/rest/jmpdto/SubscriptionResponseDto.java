package com.epam.learn.rest.jmpdto;

import lombok.Data;

@Data
public class SubscriptionResponseDto {
    private Long id;
    private Long userId;
    private String startDate;
}
