package com.seereal.algi.security.provider;

import com.seereal.algi.model.organization.Organization;
import com.seereal.algi.model.organization.OrganizationRepository;
import com.seereal.algi.security.context.OrganizationContext;
import com.seereal.algi.security.token.LoginPostAuthorizationToken;
import com.seereal.algi.security.token.LoginPreAuthorizationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class OrganizationLoginProvider implements AuthenticationProvider {

    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginPreAuthorizationToken token = (LoginPreAuthorizationToken) authentication;
        String registerNumber = token.getUser();
        String password = passwordEncoder.encode(token.getPassword());

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
        return passwordEncoder.matches(password, organization.getPassword());
    }
}
