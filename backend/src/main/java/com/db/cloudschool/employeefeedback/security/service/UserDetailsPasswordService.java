package com.db.cloudschool.employeefeedback.security.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * This service implementation uses default spring boot security password validation behaviour to check access tokens
 */
public class UserDetailsPasswordService implements org.springframework.security.core.userdetails.UserDetailsPasswordService {
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }
}
