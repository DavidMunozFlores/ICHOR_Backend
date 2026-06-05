package com.erguidos.ichor.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    Random random() {
        return new Random();
    }
}
