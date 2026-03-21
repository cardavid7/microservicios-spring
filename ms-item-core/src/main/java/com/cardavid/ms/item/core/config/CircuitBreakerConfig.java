package com.cardavid.ms.item.core.config;
import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class CircuitBreakerConfig {

	//The YAML configuration takes precedence over this configuration
	@Bean
	Customizer<Resilience4JCircuitBreakerFactory> customizerCircuitBreaker(){
		return (factory) -> factory.configureDefault(id -> {
			return new Resilience4JConfigBuilder(id).circuitBreakerConfig(
					io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
						.slidingWindowSize(20)
						.failureRateThreshold(50)
						.waitDurationInOpenState(Duration.ofSeconds(20L))
					.build())
					
					//time request
					.timeLimiterConfig(TimeLimiterConfig.custom()
						.timeoutDuration(Duration.ofSeconds(10L))
					.build()
				).build();
		});
	}
}
