package com.lorarch.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LocalizadorMotos {

    public static void main(String[] args) {
        SpringApplication.run(LocalizadorMotos.class, args);
    }
}
