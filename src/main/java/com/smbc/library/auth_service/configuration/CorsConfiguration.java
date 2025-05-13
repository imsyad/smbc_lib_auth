package com.smbc.library.auth_service.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfiguration {

    @Value("${smbc.properties.allowed-method}")
    private String ALLOWED_METHODS;

    @Value("${smbc.properties.allowed-origin}")
    private String ALLOWED_ORIGINS;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
        configuration.setAllowedMethods(List.of(ALLOWED_METHODS.split(",")));
        configuration.setAllowedOrigins(List.of(ALLOWED_ORIGINS.split(",")));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }
}
