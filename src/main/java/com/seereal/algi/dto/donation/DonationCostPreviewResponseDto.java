package com.seereal.algi.dto.donation;

import com.seereal.algi.model.donation.DonationCostPreview;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DonationCostPreviewResponseDto {
    private String name;
    private Integer targetNum;
    private Integer cost;
    private String etc;

    @Builder
    public DonationCostPreviewResponseDto(String name, Integer targetNum, Integer cost, String etc) {
        this.name = name;
        this.targetNum = targetNum;
        this.cost = cost;
        this.etc = etc;
    }

    public static DonationCostPreviewResponseDto convertToDto(DonationCostPreview costPreview) {
        return DonationCostPreviewResponseDto.builder()
                .name(costPreview.getName())
                .targetNum(costPreview.getTargetNum())
                .cost(costPreview.getCost())
                .etc(costPreview.getEtc())
                .build();
    }
}
