package com.db.cloudschool.employeefeedback.security.decorator;

import com.db.cloudschool.employeefeedback.model.Identity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AuthenticationStatusToken extends UsernamePasswordAuthenticationToken {
    @Override
    public Identity getPrincipal() {
        if(super.getPrincipal() instanceof Identity) return (Identity) super.getPrincipal();
        else return null;
    };

    @Override
    public Object getCredentials() {
        return null;
    };

    /**
     * Unauthenticated
     * @param principal
     */
    public AuthenticationStatusToken(Identity principal) {
        super(principal, null);
    }

    /**
     * Authenticated
     * @param principal
     * @param authorities
     */
    public AuthenticationStatusToken(Identity principal, Set<? extends GrantedAuthority> authorities) {
        super(principal, null, authorities);
        //if(isAuthenticated == null) isAuthenticated = false;
        //super.setAuthenticated(isAuthenticated);
    }
}
