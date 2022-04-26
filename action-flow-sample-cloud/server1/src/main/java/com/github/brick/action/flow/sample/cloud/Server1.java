package com.github.brick.action.flow.sample.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Server1 {
    public static void main(String[] args) {
        SpringApplication.run(Server1.class, args);
    }

}
