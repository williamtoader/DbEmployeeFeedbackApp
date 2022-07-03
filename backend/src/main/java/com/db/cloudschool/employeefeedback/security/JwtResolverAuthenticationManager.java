package com.db.cloudschool.employeefeedback.security;

import com.db.cloudschool.employeefeedback.model.Identity;
import com.db.cloudschool.employeefeedback.security.decorator.AuthenticationStatusToken;
import com.db.cloudschool.employeefeedback.security.decorator.JwtAuthenticationToken;
import com.db.cloudschool.employeefeedback.security.service.CredentialsService;
import com.db.cloudschool.employeefeedback.security.service.SecretsService;
import com.db.cloudschool.employeefeedback.service.IdentityService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Wiliam Toader
 */
@Component
@RequiredArgsConstructor
public class JwtResolverAuthenticationManager implements org.springframework.security.authentication.AuthenticationManager {
    private final SecretsService secretsService;
    private final IdentityService identityService;
    private final CredentialsService credentialsService;

    @Override
    public AuthenticationStatusToken authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof JwtAuthenticationToken)) throw new BadCredentialsException("1000");
        String jwt = (String) authentication.getCredentials();
        try {
            Identity identity = credentialsService.validateAccessToken(jwt);
            return new AuthenticationStatusToken(
                    identity,
                    true,
                    identity.getRoles()
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList())
            );

        } catch (JwtException e) {
            // JWT check failed
            throw new BadCredentialsException("Jwt validation failed");
        }

    }
}
