package com.iv1201.project.recruitment.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Used as a caching abstraction to improve the performance of the system.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * A method called at runtime which allows for retrieving named Cache regions.
     * @return is a <>CacheManager</> implementation that lazily builds ConcurrentMapCache
     *         instances for each getCache request in the application.
     */
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("users");
    }
}
