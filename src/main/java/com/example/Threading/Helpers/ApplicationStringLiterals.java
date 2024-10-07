package com.example.Threading.Helpers;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "allowed.client")
public class ApplicationStringLiterals {
    private String url;
}