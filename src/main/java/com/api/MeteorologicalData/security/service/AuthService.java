package com.api.MeteorologicalData.security.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

/**
 * Service that manages authentication operations and API limits.
 */
@Slf4j
@Service
@Transactional
public class AuthService {

    /**
     * Configures and returns a Bucket object to apply limits to API calls.
     *
     * @param username The username associated with the limit setting.
     * @return A Bucket object configured with limits for API calls.
     */
    @Cacheable(value = "limiterApi", key = "#username")
    public Bucket limiterApi(String username) {
        Bandwidth limit = Bandwidth.classic(100, Refill.intervally(100, Duration.ofHours(1)));
        return Bucket.builder().addLimit(limit).build();
    }
}
