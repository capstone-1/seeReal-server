package com.seereal.algi.security.handler;

import com.seereal.algi.security.context.OrganizationContext;
import com.seereal.algi.security.context.TokenContext;
import com.seereal.algi.security.jwt.JwtFactory;
import com.seereal.algi.security.token.LoginPostAuthorizationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtFactory jwtFactory;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LoginPostAuthorizationToken token = (LoginPostAuthorizationToken) authentication;
        OrganizationContext context = (OrganizationContext) token.getPrincipal();
        String tokenString = jwtFactory.generateToken(context);
        processResponse(response, writeToDto(tokenString));
    }


    private void processResponse(HttpServletResponse response, TokenContext tokenContext) {
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Authorization", generateTokenValue(tokenContext.getToken()));
    }

    private String generateTokenValue(String token) {
        return "Bearer " + token;
    }

    private TokenContext writeToDto(String tokenString) {
        return new TokenContext(tokenString);
    }
}
