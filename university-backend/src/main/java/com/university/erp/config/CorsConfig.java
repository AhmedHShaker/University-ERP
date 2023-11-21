package com.university.erp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    private String allowedOrigin = "http://localhost:3000";
    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry
//                .addMapping("/api/**")
//                .allowedOrigins(allowedOrigin)
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowCredentials(true)
//                .maxAge(3600); // Max age of the pre-flight request in seconds
//        registry
//                .addMapping("/auth/**")
//                .allowedOrigins(allowedOrigin)
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowCredentials(true)
//                .maxAge(3600); // Max age of the pre-flight request in seconds
        registry
                .addMapping("/**")
                .allowedOrigins(allowedOrigin)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true)
                .maxAge(3600); // Max age of the pre-flight request in seconds
    }
}