package com.epam.learn.spring.task4;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Endpoint(id = "custom")
@RequiredArgsConstructor
public class CustomActuatorEndpoint {

    private final Environment environment;

    @ReadOperation
    public Map<String, String> getCustomInfo() {
        return Map.of(
                "activeProfile", String.join(", ", environment.getActiveProfiles()),
                "dbUrl", environment.getProperty("spring.datasource.url", "")
        );
    }
}
