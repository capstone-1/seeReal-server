package com.seereal.algi.security.context;

import com.seereal.algi.model.user.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserContext extends User {
    private UserContext(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    public static UserContext fromToken(OAuth2AuthenticationToken token) {
        return new UserContext(token.getPrincipal().getAttributes().get("name").toString(), token.getPrincipal().getAttributes().get("email").toString(), generateAuthorities("GUEST"));
    }
    private static List<SimpleGrantedAuthority> generateAuthorities(String role) {
        return generateAuthorities(Role.valueOf(role));
    }
    private static List<SimpleGrantedAuthority> generateAuthorities(Role role) {
        return Stream.of(role).map(r -> new SimpleGrantedAuthority(r.getKey()))
                .collect(Collectors.toList());
    }
}