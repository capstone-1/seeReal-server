package com.seereal.algi.security.token;

import com.seereal.algi.dto.organization.OrganizationLoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginPreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    private LoginPreAuthorizationToken(String user, String password) {
        super(user, password);
    }

    public LoginPreAuthorizationToken(OrganizationLoginDto loginDto) {
        this(loginDto.getRegisterNumber(), loginDto.getPassword());
    }

    public String getUser() {
        return (String)super.getPrincipal();
    }

    public String getPassword() {
        return (String)super.getCredentials();
    }
}
