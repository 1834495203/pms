package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author GLaDOS
 * @date 2023/3/25 19:50
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://www.mahiro.com", "http://localhost:8080")
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(3600);
    }
}
