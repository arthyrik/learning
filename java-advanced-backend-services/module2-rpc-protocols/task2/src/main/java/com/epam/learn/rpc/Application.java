package com.epam.learn.rpc;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner {

    private final KafkaAvroProducer producer;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            var user = com.epam.learn.rpc.User.newBuilder()
                    .setId(new Random().nextLong())
                    .setName("Tom")
                    .build();

            producer.send(user);

            sleep(TimeUnit.SECONDS.toMillis(2));
        }
    }
}