package com.aaredo.app.gateway.filters.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class ExampleGatewayFilterFactory extends AbstractGatewayFilterFactory<ExampleGatewayFilterFactory.Configuracion> {
    private final Logger LOGGER = LoggerFactory.getLogger(ExampleGatewayFilterFactory.class);

    public ExampleGatewayFilterFactory(){
        super(Configuracion.class);
    }

    @Override
    public String name() {
        return "Example";
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("mensaje", "cookieName", "cookieValue");
    }

    public static class Configuracion {
        private String mensaje;
        private String cookieName;
        private String cookieValue;

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public String getCookieValue() {
            return cookieValue;
        }

        public void setCookieValue(String cookieValue) {
            this.cookieValue = cookieValue;
        }

        public String getCookieName() {
            return cookieName;
        }

        public void setCookieName(String cookieName) {
            this.cookieName = cookieName;
        }
    }

    @Override
    public GatewayFilter apply(Configuracion config) {
        return new OrderedGatewayFilter(((exchange, chain) -> {
            LOGGER.info("< PRE-FILTER FACTORY {} >", config.mensaje);
            return chain.filter(exchange).then(Mono.fromRunnable(()-> {
                Optional.ofNullable(config.cookieValue).ifPresent( cookie -> {
                    exchange.getResponse().addCookie(ResponseCookie.from(config.cookieName, cookie).build());
                });
                LOGGER.info("< POST-FILTER FACTORY {} > ", config.mensaje);
            }));
        }),2 );
    }
}
