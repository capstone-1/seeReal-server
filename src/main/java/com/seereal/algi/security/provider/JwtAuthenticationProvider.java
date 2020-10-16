package com.seereal.algi.security.provider;

import com.seereal.algi.security.context.OrganizationContext;
import com.seereal.algi.security.jwt.JwtDecoder;
import com.seereal.algi.security.token.JwtPostAuthorizationToken;
import com.seereal.algi.security.token.JwtPreAuthorizationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String)authentication.getPrincipal();
        OrganizationContext context = jwtDecoder.decodeJwt(token);
        return JwtPostAuthorizationToken.getTokenFromOrganizationContext(context);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtPreAuthorizationToken.class.isAssignableFrom(authentication);
    }
}
