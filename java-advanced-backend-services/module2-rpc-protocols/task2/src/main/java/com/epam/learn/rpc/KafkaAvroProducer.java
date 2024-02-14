package com.epam.learn.rpc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaAvroProducer {

    private final KafkaTemplate<String, User> kafkaTemplate;

    @Value("${topic.name}")
    private String topicName;

    public void send(User user) {
        var future = kafkaTemplate.send(topicName, user);

        future.whenComplete(((result, throwable) -> {
            if (throwable == null) {
                log.info("Sent message: " + user);
            } else {
                log.error("Failed to send message: " + user, throwable);
            }
        }));
    }
}
