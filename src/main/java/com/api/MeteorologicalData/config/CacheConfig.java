package com.api.MeteorologicalData.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Configuration class for cache management.
 */
@Configuration
public class CacheConfig {

    /**
     * Creates a new instance of Caffeine with a default expiration time of 1 hour.
     *
     * @return a new instance of Caffeine
     */
    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.HOURS);
    }

    /**
     * Creates a new instance of CaffeineCacheManager and sets the Caffeine instance created in caffeineConfig as its cache manager.
     *
     * @param caffeine the Caffeine instance created in caffeineConfig
     * @return a new instance of CaffeineCacheManager
     */
    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

}
