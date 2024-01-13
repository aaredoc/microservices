package com.aaredo.app.items;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {
    @Bean("clienteRest")
    public RestTemplate registrarRestTemplate(){
        return new RestTemplate();
    }

    //CONFIGURAMOS LA CANTIDAD DE REQUEST CON LOS QUE DEBEN ABRIR O CERRAR EL CORTOCIRCUITO
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory>defaultCustomizer(){
        return factory -> factory.configureDefault( id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.custom()
                        .slidingWindowSize(10)
                        .failureRateThreshold(50)
                        .waitDurationInOpenState(Duration.ofSeconds(10L))
                        .permittedNumberOfCallsInHalfOpenState(5)
                        .slowCallRateThreshold(50)
                        .slowCallDurationThreshold(Duration.ofSeconds(2L))
                        .build())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(3L)).build())
                .build());
    }
}
