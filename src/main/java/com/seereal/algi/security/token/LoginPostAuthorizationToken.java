package com.seereal.algi.security.token;

import com.seereal.algi.security.context.OrganizationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class LoginPostAuthorizationToken extends UsernamePasswordAuthenticationToken {
    public LoginPostAuthorizationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static LoginPostAuthorizationToken getTokenFromOrganizationContext(OrganizationContext context) {
        return new LoginPostAuthorizationToken(context, context.getPassword(), context.getAuthorities());
    }

    public OrganizationContext getOrganizationContext() {
        return (OrganizationContext) this.getPrincipal();
    }
}
