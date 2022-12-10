package com.tinnitussounds.cms.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ExternalBasicAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    AdminRepository authRepository;

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials().toString();

        List<Admin> admins = authRepository.findAll();

        for(Admin admin : admins ) {
            if(admin.getUser().equals(username)) {
                if(admin.getPassword().equals(password)) {
                    return new UsernamePasswordAuthenticationToken
                            (username, password, Collections.emptyList());
                } else {
                    throw new BadCredentialsException("Invalid password");
                }
            }   
        }

        throw new BadCredentialsException("User not found");
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
