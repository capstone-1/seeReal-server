package com.seereal.algi.dto.organization;

import com.seereal.algi.model.organization.Organization;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class OrganizationSignUpRequestDto {
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private String registerNumber;
    private String account;

    public Organization toEntity() {
        return Organization.builder()
                .name(this.name)
                .password(this.password)
                .account(this.account)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .registerNumber(this.registerNumber)
                .build();
    }
}
