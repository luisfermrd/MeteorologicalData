package com.api.MeteorologicalData.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Configuration class for the Rate Limiting Bucket
 */
@Configuration
public class BucketConfig {

    /**
     * Creates a new Rate Limiting Bucket with a capacity of 1000 tokens and a refill speed of 1000 tokens per second
     * for a period of 1 day
     *
     * @return a new Rate Limiting Bucket
     */
    @Bean
    public Bucket bucket() {
        Bandwidth limit = Bandwidth.classic(1000, Refill.intervally(1000, Duration.ofDays(1)));
        return Bucket.builder().addLimit(limit).build();
    }
}
