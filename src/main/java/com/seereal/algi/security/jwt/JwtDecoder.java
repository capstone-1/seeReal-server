package com.seereal.algi.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.seereal.algi.security.context.OrganizationContext;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Component
public class JwtDecoder {
    //TODO: Change To application.properties
    private static String signedKey = "jwttest";
    private static String issuer = "capstone";

    public OrganizationContext decodeJwt(String token) {
        DecodedJWT decodedJWT = isValidJwtToken(token).orElseThrow(() -> new InvalidJwtException("Invalid JWT Token!"));
        String registerNumber = decodedJWT.getClaim("ID").asString();
        String name = decodedJWT.getClaim("NAME").asString();
        String role = decodedJWT.getClaim("USER_ROLE").asString();
        return new OrganizationContext(registerNumber, name, role);
    }

    private Optional<DecodedJWT> isValidJwtToken(String token) {
        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(signedKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            jwt = verifier.verify(token);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(jwt);
    }
}
