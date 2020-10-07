package com.seereal.algi.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seereal.algi.security.context.OrganizationContext;
import com.seereal.algi.security.context.TokenContext;
import com.seereal.algi.security.jwt.JwtFactory;
import com.seereal.algi.security.token.LoginPostAuthorizationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtFactory jwtFactory;
    private final ObjectMapper objectMapper;

    public LoginAuthenticationSuccessHandler(JwtFactory jwtFactory, ObjectMapper objectMapper) {
        this.jwtFactory = jwtFactory;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginPostAuthorizationToken token = (LoginPostAuthorizationToken) authentication;
        OrganizationContext context = (OrganizationContext) token.getPrincipal();
        String tokenString = jwtFactory.generateToken(context);
        processResponse(response, (TokenContext) writeToDto(tokenString));
    }


    private void processResponse(HttpServletResponse response, TokenContext tokenContext) {
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Authorization", generateTokenValue(tokenContext.getToken()));
    }

    private String generateTokenValue(String token) {
        return "Bearer " + token;
    }

    private Object writeToDto(String tokenString) {
        return new TokenContext(tokenString);
    }
}
