package com.seereal.algi.security.handler;

import com.seereal.algi.security.context.TokenContext;
import com.seereal.algi.security.context.UserContext;
import com.seereal.algi.security.jwt.JwtFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class  OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtFactory jwtFactory;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        UserContext userContext = UserContext.fromToken(token);
        String tokenString = jwtFactory.generateToken(userContext);
        processResponse(response, writeToDto(tokenString));
    }


    private void processResponse(HttpServletResponse response, TokenContext tokenContext) throws IOException {
        response.setHeader("Authorization", tokenContext.getToken());
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

    }

    private String generateTokenValue(String token) {
        return "Bearer " + token;
    }

    private TokenContext writeToDto(String tokenString) {
        return new TokenContext(tokenString);
    }
}

