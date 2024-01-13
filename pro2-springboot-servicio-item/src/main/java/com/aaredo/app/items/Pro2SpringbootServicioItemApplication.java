package com.aaredo.app.items;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients
public class Pro2SpringbootServicioItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(Pro2SpringbootServicioItemApplication.class, args);
    }

}
