package com.seereal.algi.dto.regularDonation;

import com.seereal.algi.model.regularDonation.DonationCostPreview;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DonationCostPreviewRequestDto {
    private String name;
    private Integer targetNum;
    private Integer cost;
    private String etc;

    public static DonationCostPreview convertToentity(DonationCostPreviewRequestDto dto) {
        return DonationCostPreview.builder()
                .cost(dto.getCost())
                .name(dto.getName())
                .etc(dto.getEtc())
                .targetNum(dto.getTargetNum())
                .build();
    }
}
