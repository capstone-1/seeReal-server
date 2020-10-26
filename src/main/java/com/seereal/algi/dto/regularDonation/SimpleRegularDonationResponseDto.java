package com.seereal.algi.dto.regularDonation;

import com.seereal.algi.model.regularDonation.RegularDonation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleRegularDonationResponseDto {
    private String name;
    private String registrant;

    @Builder
    public SimpleRegularDonationResponseDto(String name, String registrant) {
        this.name = name;
        this.registrant = registrant;
    }
}
