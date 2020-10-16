package com.seereal.algi.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtPreAuthorizationToken extends UsernamePasswordAuthenticationToken {
    private JwtPreAuthorizationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public JwtPreAuthorizationToken(String token) {
        this(token, token.length());
    }
}
