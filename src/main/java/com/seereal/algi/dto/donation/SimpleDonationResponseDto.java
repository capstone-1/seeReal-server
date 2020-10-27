package com.seereal.algi.dto.donation;

import com.seereal.algi.model.donation.Donation;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleDonationResponseDto {
    private Long id;
    private String name;
    private String registrant;

    public SimpleDonationResponseDto(Donation donation) {
        this.id = donation.getId();
        this.name = donation.getName();
        this.registrant = donation.getRegistrant();
    }
}
