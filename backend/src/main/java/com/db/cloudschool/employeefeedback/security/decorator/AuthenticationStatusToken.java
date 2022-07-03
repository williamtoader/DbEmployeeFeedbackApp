package com.db.cloudschool.employeefeedback.security.decorator;

import com.db.cloudschool.employeefeedback.model.Identity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticationStatusToken extends UsernamePasswordAuthenticationToken {
    public AuthenticationStatusToken(Identity principal, Boolean isAuthenticated) {
        super(principal, null);
        if(isAuthenticated == null) isAuthenticated = false;
        super.setAuthenticated(isAuthenticated);
    }

    public AuthenticationStatusToken(Identity principal, Boolean isAuthenticated, Collection<? extends GrantedAuthority> authorities) {
        super(principal, null, authorities);
        if(isAuthenticated == null) isAuthenticated = false;
        super.setAuthenticated(isAuthenticated);
    }
}
