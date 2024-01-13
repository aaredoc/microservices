package com.aaredo.app.erureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class Pro3SpringbootServicioEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Pro3SpringbootServicioEurekaServerApplication.class, args);
    }

}
