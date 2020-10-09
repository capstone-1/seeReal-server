package com.seereal.algi.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.seereal.algi.security.context.OrganizationContext;
import com.seereal.algi.security.context.UserContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;

import static com.seereal.algi.security.SecurityConstants.*;

@Component
public class JwtFactory {
    public String generateToken(UserContext context) {
        String token = null;
        try {
            token = JWT.create()
                    .withClaim("EMAIL", context.getUsername())
                    .withClaim("NAME", context.getPassword())
                    .withClaim("USER_ROLE", getUserRole(context.getAuthorities()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(generateAlgorithm());
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return token;
    }

    public String generateToken(OrganizationContext context) {
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim("ID", context.getUsername())
                    .withClaim("NAME", context.getOrganization().getName())
                    .withClaim("USER_ROLE", getUserRole(context.getAuthorities()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(generateAlgorithm());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return token;
    }

    private Algorithm generateAlgorithm() throws UnsupportedEncodingException {
        return Algorithm.HMAC256(SECRET_KEY);
    }

    private String getUserRole(Collection<GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).findFirst().orElse("UNKNOWN");
    }
}
