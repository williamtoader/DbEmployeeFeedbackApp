package com.db.cloudschool.employeefeedback.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable cross-site request forgery protection.
        http.csrf().disable();
        http.authorizeRequests()
                // Allow all Swagger URLs including OpenApi v3 docs
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v3/**").permitAll()
                // Allow login
                .antMatchers("/login").permitAll()
                .antMatchers("/").permitAll()
                // Allow the rest of URLs to be accessed only by authenticated users.
                .antMatchers("/**").authenticated();
        return http.build();
    }
}