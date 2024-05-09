package com.gersimuca.Warehouse.Management.config;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@NoArgsConstructor
public class SwaggerDocumentationConfig implements WebMvcConfigurer {
    private String baseUrl = "/";

    public SwaggerDocumentationConfig(String baseUrl){ this.baseUrl = baseUrl; }
}

