package com.seereal.algi.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seereal.algi.security.context.OrganizationContext;
import com.seereal.algi.security.context.TokenContext;
import com.seereal.algi.security.jwt.JwtFactory;
import com.seereal.algi.security.token.LoginPostAuthorizationToken;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtFactory jwtFactory;

    @NoArgsConstructor
    @Getter
    private static class LoginSuccessDto {
        private String name;

        public LoginSuccessDto(String name) {
            this.name = name;
        }
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        LoginPostAuthorizationToken token = (LoginPostAuthorizationToken) authentication;
        OrganizationContext context = (OrganizationContext) token.getPrincipal();
        String tokenString = jwtFactory.generateToken(context);
        processResponse(response, writeToDto(tokenString), token.getOrganizationContext().getOrganization().getName());
    }

    private void processResponse(HttpServletResponse response, TokenContext tokenContext, String name) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Authorization", generateTokenValue(tokenContext.getToken()));
        response.setHeader(HttpHeaders.CONTENT_TYPE,"application/json");
        response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(new LoginSuccessDto(name)));
    }
    private String generateTokenValue(String token) {
        return "Bearer " + token;
    }

    private TokenContext writeToDto(String tokenString) {
        return new TokenContext(tokenString);
    }
}
