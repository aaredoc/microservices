package com.aaredo.app.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Pro4SpringbootServicioGatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Pro4SpringbootServicioGatewayServerApplication.class, args);
    }

}
