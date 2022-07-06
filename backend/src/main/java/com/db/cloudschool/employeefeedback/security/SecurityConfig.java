package com.db.cloudschool.employeefeedback.security;

import com.db.cloudschool.employeefeedback.security.filter.JwtAuthenticationFilter;
import com.db.cloudschool.employeefeedback.security.filter.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable cross-site request forgery protection.
        http.csrf().disable();

        http

                .authorizeRequests()

                // Allow all Swagger URLs including OpenApi v3 docs
                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/**").permitAll()

                // Allow login
                .antMatchers("/login", "/register", "/auth/accesstoken", "/email/confirm/*").permitAll()

                // Allow app root
                .antMatchers("/").permitAll()

                .antMatchers("/testing/**").permitAll()

                // Allow the rest of URLs to be accessed only by authenticated users.
                .antMatchers("/**").authenticated()
                .and().exceptionHandling()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtAuthenticationFilter, JwtRequestFilter.class);

        return http.build();
    }

}