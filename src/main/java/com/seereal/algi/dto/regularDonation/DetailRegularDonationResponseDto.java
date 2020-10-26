package com.seereal.algi.dto.regularDonation;

import com.seereal.algi.model.category.Category;
import com.seereal.algi.model.regularDonation.RegularDonation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class DetailRegularDonationResponseDto {
    private String name;
    private String registrant;
    private String shortIntroduction;
    private String profileUrl;
    private String target;
    private String content;
    private String introduction;
    private String plan;
    private List<String> categories = new ArrayList<>();

    @Builder
    public DetailRegularDonationResponseDto(String name, String registrant, String shortIntroduction, String profileUrl, String target, String content, String introduction, String plan, List<String> categories) {
        this.name = name;
        this.registrant = registrant;
        this.shortIntroduction = shortIntroduction;
        this.profileUrl = profileUrl;
        this.target = target;
        this.content = content;
        this.introduction = introduction;
        this.plan = plan;
        this.categories = categories;
    }

    public static DetailRegularDonationResponseDto convertToDto(RegularDonation regularDonation) {
        return DetailRegularDonationResponseDto.builder()
                .categories(regularDonation.getCategories().stream().map(Category::getName).collect(Collectors.toList()))
                .content(regularDonation.getContent())
                .introduction(regularDonation.getIntroduction())
                .name(regularDonation.getName())
                .plan(regularDonation.getPlan())
                .profileUrl(regularDonation.getProfileUrl())
                .registrant(regularDonation.getRegistrant())
                .shortIntroduction(regularDonation.getShortIntroduction())
                .target(regularDonation.getTarget())
                .build();
    }
}
