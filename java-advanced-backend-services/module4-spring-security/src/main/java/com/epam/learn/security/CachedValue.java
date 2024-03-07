package com.epam.learn.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CachedValue {

    private int attempts;
    private LocalDateTime blockedTimestamp;
    private LocalDateTime blockedUntilTimestamp;
    public void registerAttempt() {
        this.attempts++;
    }
}
