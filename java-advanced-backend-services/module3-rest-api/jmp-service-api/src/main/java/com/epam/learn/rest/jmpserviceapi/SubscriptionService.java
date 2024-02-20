package com.epam.learn.rest.jmpserviceapi;

import com.epam.learn.rest.jmpdto.Subscription;

import java.util.Optional;

public interface SubscriptionService {
    Subscription createSubscription(Subscription subscription);

    Subscription updateSubscription(Subscription subscription);

    void deleteSubscription(Long subscriptionId);

    Optional<Subscription> getSubscription(Long subscriptionId);

    Iterable<Subscription> getAllSubscription();
}
