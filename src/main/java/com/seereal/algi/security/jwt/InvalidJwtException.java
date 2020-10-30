package com.seereal.algi.security.jwt;

import org.springframework.security.authentication.AuthenticationServiceException;

public class InvalidJwtException extends AuthenticationServiceException {
    public InvalidJwtException(String message) {
        super(message);
    }
}
