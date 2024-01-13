package com.aaredo.app.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class Pro1SpringbootServicioProductosApplication {

    public static void main(String[] args) {
        SpringApplication.run(Pro1SpringbootServicioProductosApplication.class, args);
    }

}
