package com.aaredo.app.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class GlobalFilterExample implements GlobalFilter, Ordered {
    private final Logger LOGGER = LoggerFactory.getLogger(GlobalFilterExample.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOGGER.info("< PRE-FILTER >");
        exchange.getRequest().mutate().headers(header -> {header.add("token", "drusek");});

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            LOGGER.info("< POST-FILTER >");
            Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(value -> exchange.getResponse().getHeaders().add("token", value));
            exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "rojo").build());
            //exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        }));
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
