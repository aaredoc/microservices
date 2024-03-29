package com.aaredo.app.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class Pro4SpringbootServicioConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(Pro4SpringbootServicioConfigApplication.class, args);
    }

}
