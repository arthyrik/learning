package com.epam.learn.rest.jmpcloudserviceimpl;

import com.epam.learn.rest.jmpdto.Subscription;
import com.epam.learn.rest.jmpserviceapi.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription createSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription updateSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Override
    public void deleteSubscription(Long subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
    }

    @Override
    public Optional<Subscription> getSubscription(Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId);
    }

    @Override
    public Iterable<Subscription> getAllSubscription() {
        return subscriptionRepository.findAll();
    }
}
