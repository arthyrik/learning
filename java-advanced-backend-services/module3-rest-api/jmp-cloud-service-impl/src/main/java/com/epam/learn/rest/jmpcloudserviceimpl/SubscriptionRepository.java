package com.epam.learn.rest.jmpcloudserviceimpl;

import com.epam.learn.rest.jmpdto.Subscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
}
