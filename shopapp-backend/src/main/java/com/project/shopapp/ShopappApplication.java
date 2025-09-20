package com.project.shopapp;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopappApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopappApplication.class, args);
    }

    @Value("${jwt.secretKey}")
    private String jwtKey;

    @PostConstruct
    public void Test() {
        System.out.println("jwtKey: " + jwtKey);
    }

}
