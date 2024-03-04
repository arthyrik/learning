package com.epam.learn.rest.jmpservicerest;

import com.epam.learn.rest.jmpdto.*;
import com.epam.learn.rest.jmpserviceapi.SubscriptionService;
import com.epam.learn.rest.jmpserviceapi.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    private final SubscriptionMapper mapper;

    @PostMapping
    public SubscriptionResponseDto createSubscription(@RequestBody @Valid SubscriptionRequestDto subscriptionRequestDto) {
        var user = verifyUser(subscriptionRequestDto.getUserId());
        var subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStartDate(LocalDate.now());

        var createdSubscription = subscriptionService.createSubscription(subscription);

        return mapper.toSubscriptionResponseDto(createdSubscription);
    }

    @PutMapping("/{subscriptionId}")
    public SubscriptionResponseDto updateSubscription(@PathVariable Long subscriptionId,
                                                      @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        var subscription = verifySubscription(subscriptionId);
        var newUser = verifyUser(subscriptionRequestDto.getUserId());

        subscription.setUser(newUser);

        var updatedSubscription = subscriptionService.updateSubscription(subscription);

        return mapper.toSubscriptionResponseDto(updatedSubscription);
    }

    @DeleteMapping("/{subscriptionId}")
    public void deleteSubscription(@PathVariable Long subscriptionId) {
        subscriptionService.deleteSubscription(subscriptionId);
    }

    @GetMapping("/{subscriptionId}")
    public SubscriptionResponseDto getSubscription(@PathVariable Long subscriptionId) {
        var subscription = verifySubscription(subscriptionId);

        return mapper.toSubscriptionResponseDto(subscription);
    }

    @GetMapping
    public List<SubscriptionResponseDto> getAllSubscription() {
        return StreamSupport.stream(subscriptionService.getAllSubscription().spliterator(), false)
                .map(mapper::toSubscriptionResponseDto).toList();
    }

    private Subscription verifySubscription(Long subscriptionId) {
        return subscriptionService.getSubscription(subscriptionId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "There is no subscription with id " + subscriptionId));
    }

    private User verifyUser(Long userId) {
        return userService.getUser(userId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "There is no user with id " + userId));
    }
}
