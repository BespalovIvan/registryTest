package com.respak.registryTest;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
public class RegistryApp {
    public static void main(String[] args) {
        SpringApplication.run(RegistryApp.class);
    }
}
