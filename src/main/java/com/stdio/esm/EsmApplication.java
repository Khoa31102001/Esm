package com.stdio.esm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication

public class EsmApplication {
    private static final Logger LOGGER= LoggerFactory.getLogger(EsmApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EsmApplication.class, args);
        LOGGER.info("Esm app starts running");
    }
}
