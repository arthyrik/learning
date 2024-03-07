package com.epam.learn.security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 2;
    private static final int BLOCK_DURATION_SECONDS = 20;
    
    private final LoadingCache<String, CachedValue> attemptsCache;

    public LoginAttemptService() {
        attemptsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(BLOCK_DURATION_SECONDS, TimeUnit.SECONDS)
                .build(new CacheLoader<>() {
                    @Override
                    public CachedValue load(String key) {
                        return new CachedValue(0, null, null);
                    }
                });
    }

    @SneakyThrows
    public void loginFailed(final String username) {
        var cachedValue = attemptsCache.get(username);
        cachedValue.registerAttempt();

        if (isBlocked(username)) {
            cachedValue.setBlockedTimestamp(LocalDateTime.now());
            cachedValue.setBlockedUntilTimestamp(cachedValue.getBlockedTimestamp().plusSeconds(BLOCK_DURATION_SECONDS));
        }

        attemptsCache.put(username, cachedValue);
    }

    @SneakyThrows
    public boolean isBlocked(final String username) {
        return attemptsCache.get(username).getAttempts() >= MAX_ATTEMPTS;
    }

   public void loginSuccess(final String username) {
        CachedValue cachedValue = new CachedValue(0, null, null);
        attemptsCache.put(username, cachedValue);
   }

    public CachedValue getCachedValue(String username) {
        return attemptsCache.getUnchecked(username);
    }
}
