package com.seereal.algi.security.context;

import com.seereal.algi.model.organization.Organization;
import com.seereal.algi.model.user.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrganizationContext extends User {
    private Organization organization;
    public OrganizationContext(Organization organization, String registerNumber, String password, Collection<? extends GrantedAuthority> authorities) {
        super(registerNumber, password, authorities);
        this.organization = organization;
    }

    public static OrganizationContext fromOrganizationModel(Organization organization) {
        return new OrganizationContext(organization, organization.getRegisterNumber(), organization.getPassword(), generateAuthorities(Role.AGENCY));
    }

    public OrganizationContext(String registerNumber, String name, String role) {
        super(registerNumber, name, generateAuthorities(role));
    }

    private static List<SimpleGrantedAuthority> generateAuthorities(String role) {
        return generateAuthorities(Role.valueOf(role));
    }
    private static List<SimpleGrantedAuthority> generateAuthorities(Role role) {
        return Stream.of(role).map(r -> new SimpleGrantedAuthority(r.getKey()))
                              .collect(Collectors.toList());
    }

    public Organization getOrganization() {
        return organization;
    }
}
