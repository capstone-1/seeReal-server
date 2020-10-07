package com.seereal.algi.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.seereal.algi.security.context.OrganizationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

@Component
public class JwtFactory {

    //TODO: Change To application.properties
    private static String signedKey = "jwttest";
    private static String issuer = "capstone";


    public String generateToken(OrganizationContext context) {
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer(issuer)
                    .withClaim("ID", context.getUsername())
                    .withClaim("NAME", context.getOrganization().getName())
                    .withClaim("USER_ROLE", getUserRole(context.getAuthorities()))
                    .sign(generateAlgorithm());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return token;
    }

    private Algorithm generateAlgorithm() throws UnsupportedEncodingException {
        return Algorithm.HMAC256(signedKey);
    }

    private String getUserRole(Collection<GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).findFirst().orElse("UNeKNOWN");
    }
}
