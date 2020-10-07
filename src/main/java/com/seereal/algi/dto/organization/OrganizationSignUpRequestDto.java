package com.seereal.algi.dto.organization;

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
}
