package com.example.demo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

// Alternatively, you can use @Value annotation:

@Getter
@Configuration
public class APIKeyConfig {
    @Value("${apiKey}")
    private String key;
}
