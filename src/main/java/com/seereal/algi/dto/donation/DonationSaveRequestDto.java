package com.seereal.algi.dto.donation;

import com.seereal.algi.model.donation.Donation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class DonationSaveRequestDto {
    private String name;
    private String registrant;
    private String shortIntroduction;
    private String target;
    private String content;
    private String introduction;
    private String plan;
    private List<String> categories = new ArrayList<>();
    private List<DonationCostPreviewRequestDto> costPreviews = new ArrayList<>();

    public static Donation convertToEntity(DonationSaveRequestDto dto) {
        return Donation.builder()
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
