package com.project.orderaccess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class OrderAccessApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderAccessApplication.class, args);
    }

}
