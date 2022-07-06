package com.db.cloudschool.employeefeedback.security.filter;

import com.db.cloudschool.employeefeedback.security.JwtResolverAuthenticationProvider;
import com.db.cloudschool.employeefeedback.security.decorator.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Order(1)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtResolverAuthenticationProvider jwtResolverAuthenticationProvider;
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken) {
            SecurityContextHolder.getContext().setAuthentication(
                    jwtResolverAuthenticationProvider.authenticate(SecurityContextHolder.getContext().getAuthentication())
            );

        }
//
        filterChain.doFilter(request, response);
    }
}
