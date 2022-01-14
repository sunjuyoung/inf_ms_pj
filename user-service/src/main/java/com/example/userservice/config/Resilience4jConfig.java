package com.example.userservice.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfig(){

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(4) //default 50
                .waitDurationInOpenState(Duration.ofMillis(1000))//CircuitBreaker 유지시간
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) //default   결과기록 카운트기반
                .slidingWindowSize(2)
                .build();

        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(4)) //4초동안 응답이없을경우 CircuitBreaker
                .build();

        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                            .timeLimiterConfig(timeLimiterConfig)
                            .circuitBreakerConfig(circuitBreakerConfig)
                            .build()
        );
    }
}
