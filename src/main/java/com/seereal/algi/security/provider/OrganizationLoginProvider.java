package com.seereal.algi.security.provider;

import com.seereal.algi.model.organization.Organization;
import com.seereal.algi.model.organization.OrganizationRepository;
import com.seereal.algi.security.context.OrganizationContext;
import com.seereal.algi.security.token.LoginPostAuthorizationToken;
import com.seereal.algi.security.token.LoginPreAuthorizationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class OrganizationLoginProvider implements AuthenticationProvider {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginPreAuthorizationToken token = (LoginPreAuthorizationToken) authentication;
        String registerNumber = token.getUser();
        String password = token.getPassword();

        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                                                            .orElseThrow(() -> new NoSuchElementException("Register Number Not Found!"));
        if (isCorrectPassword(password, organization)) {
            return LoginPostAuthorizationToken.getTokenFromOrganizationContext(OrganizationContext.fromOrganizationModel(organization));
        }
        throw new NoSuchElementException("Authentication isn't allowed!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return LoginPreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private boolean isCorrectPassword(String password, Organization organization) {
        return organization.getPassword().equals(password);
    }
}
