package com.epam.learn.rest.jmpservicerest;

import com.epam.learn.rest.jmpdto.Subscription;
import com.epam.learn.rest.jmpdto.SubscriptionRequestDto;
import com.epam.learn.rest.jmpdto.SubscriptionResponseDto;
import com.epam.learn.rest.jmpdto.User;
import com.epam.learn.rest.jmpserviceapi.SubscriptionService;
import com.epam.learn.rest.jmpserviceapi.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final UserService userService;

    @PostMapping
    public SubscriptionResponseDto createSubscription(@RequestBody @Valid SubscriptionRequestDto subscriptionRequestDto) {
        var subscription = fromDto(subscriptionRequestDto);

        return toDto(subscriptionService.createSubscription(subscription));
    }

    @PutMapping("/{subscriptionId}")
    public SubscriptionResponseDto updateSubscription(@PathVariable Long subscriptionId,
                                                      @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        var subscription = verifySubscription(subscriptionId);
        var newUser = verifyUser(subscriptionRequestDto.getUserId());

        subscription.setUser(newUser);

        return toDto(subscriptionService.updateSubscription(subscription));
    }

    @DeleteMapping("/{subscriptionId}")
    public void deleteSubscription(@PathVariable Long subscriptionId) {
        subscriptionService.deleteSubscription(subscriptionId);
    }

    @GetMapping("/{subscriptionId}")
    public SubscriptionResponseDto getSubscription(@PathVariable Long subscriptionId) {
        return toDto(verifySubscription(subscriptionId));
    }

    @GetMapping
    public List<SubscriptionResponseDto> getAllSubscription() {
        return StreamSupport.stream(subscriptionService.getAllSubscription().spliterator(), false)
                .map(this::toDto).toList();
    }

    private Subscription verifySubscription(Long subscriptionId) {
        return subscriptionService.getSubscription(subscriptionId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "There is no subscription with id " + subscriptionId));
    }

    private User verifyUser(Long userId) {
        return userService.getUser(userId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "There is no user with id " + userId));
    }

    private SubscriptionResponseDto toDto(Subscription subscription) {
        var dto = new SubscriptionResponseDto();

        dto.setId(subscription.getId());
        dto.setUserId(subscription.getUser().getId());
        dto.setStartDate(subscription.getStartDate().toString());

        return dto;
    }

    private Subscription fromDto(SubscriptionRequestDto dto) {
        var user = verifyUser(dto.getUserId());
        var subscription = new Subscription();

        subscription.setUser(user);
        subscription.setStartDate(LocalDate.now());

        return subscription;
    }
}
