package com.seereal.algi.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.seereal.algi.security.context.OrganizationContext;
import com.seereal.algi.security.context.UserContext;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Optional;

import static com.seereal.algi.config.constant.SecurityConstants.SECRET_KEY;

@Component
public class JwtDecoder {
    public UserContext decodeJwtForUser(String token) {
        DecodedJWT decodedJWT = isValidJwtToken(token).orElseThrow(() -> new InvalidJwtException("Invalid JWT Token!"));
        String email = decodedJWT.getClaim("EMAIL").asString();
        String name = decodedJWT.getClaim("NAME").asString();
        String role = decodedJWT.getClaim("USER_ROLE").asString();
        return new UserContext(name, email, role);
    }

    public OrganizationContext decodeJwt(String token) {
        DecodedJWT decodedJWT = isValidJwtToken(token).orElseThrow(() -> new InvalidJwtException("Invalid JWT Token!"));
        String registerNumber = decodedJWT.getClaim("ID").asString();
        String name = decodedJWT.getClaim("NAME").asString();
        String role = decodedJWT.getClaim("USER_ROLE").asString();
        return new OrganizationContext(registerNumber, name, role);
    }

    private Optional<DecodedJWT> isValidJwtToken(String token) throws AuthenticationCredentialsNotFoundException {
        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
             throw new AuthenticationCredentialsNotFoundException("Invalid Token!");
        }
        return Optional.ofNullable(jwt);
    }
}
