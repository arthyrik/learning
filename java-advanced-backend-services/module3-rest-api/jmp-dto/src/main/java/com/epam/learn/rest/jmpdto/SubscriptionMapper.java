package com.epam.learn.rest.jmpdto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubscriptionMapper {
    @Mapping(target = "userId", expression = "java(subscription.getUser().getId())")
    SubscriptionResponseDto toSubscriptionResponseDto(Subscription subscription);
}
