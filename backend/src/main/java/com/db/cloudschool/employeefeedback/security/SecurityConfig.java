package com.db.cloudschool.employeefeedback.security;

import com.db.cloudschool.employeefeedback.security.service.UserDetailsPasswordService;
import com.db.cloudschool.employeefeedback.security.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    final JwtResolverAuthenticationManager jwtResolverAuthenticationManager;

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
                // Allow app root
                .antMatchers("/").permitAll()
                .antMatchers("/testing/**").permitAll()
                // Allow the rest of URLs to be accessed only by authenticated users.
                .antMatchers("/**").authenticated();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return jwtResolverAuthenticationManager;
    }



}