package com.api.MeteorologicalData.security.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Slf4j
@Service
@Transactional
public class AuthService {

    @Cacheable(value = "limiterApi", key = "#username")
    public Bucket limiterApi(String username) {
        Bandwidth limit = Bandwidth.classic(100, Refill.intervally(100, Duration.ofHours(1)));
        return Bucket.builder().addLimit(limit).build();
    }
}
