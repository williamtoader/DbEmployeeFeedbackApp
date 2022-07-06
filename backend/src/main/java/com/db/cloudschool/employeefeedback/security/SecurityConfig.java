package com.db.cloudschool.employeefeedback.security;

import com.db.cloudschool.employeefeedback.payload.response.ErrorResponse;
import com.db.cloudschool.employeefeedback.security.filter.JwtAuthenticationFilter;
import com.db.cloudschool.employeefeedback.security.filter.JwtRequestFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

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
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtAuthenticationFilter, JwtRequestFilter.class)
                .cors()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, accessDeniedException) -> {
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write(convertObjectToJson(
                            new ErrorResponse(
                                    "Authentication failure",
                                    accessDeniedException.toString()
                            )
                    ));
//                    throw accessDeniedException;
                });

        return http.build();
    }

    private static String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }


}