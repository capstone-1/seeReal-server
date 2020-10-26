package com.seereal.algi.dto.regularDonation;

import com.seereal.algi.model.regularDonation.DonationCostPreview;
import com.seereal.algi.model.regularDonation.RegularDonation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class RegularDonationSaveRequestDto {
    private String name;
    private String registrant;
    private String shortIntroduction;
    private String target;
    private String content;
    private String introduction;
    private String plan;
    private List<DonationCostPreviewRequestDto> costPreviews = new ArrayList<>();

    public static RegularDonation convertToEntity(RegularDonationSaveRequestDto dto) {
        return RegularDonation.builder()
                            .name(dto.getName())
                            .content(dto.getContent())
                            .introduction(dto.getIntroduction())
                            .plan(dto.getPlan())
                            .registrant(dto.getRegistrant())
                            .shortIntroduction(dto.getShortIntroduction())
                            .target(dto.getTarget())
                            .build();
    }
}
