package com.seereal.algi.dto.donation;

import com.seereal.algi.model.category.Category;
import com.seereal.algi.model.donation.Donation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class DetailDonationResponseDto {
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
    public DetailDonationResponseDto(String name, String registrant, String shortIntroduction, String profileUrl, String target, String content, String introduction, String plan, List<String> categories) {
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

    public static DetailDonationResponseDto convertToDto(Donation donation) {
        return DetailDonationResponseDto.builder()
                .categories(donation.getCategories().stream().map(Category::getName).collect(Collectors.toList()))
                .content(donation.getContent())
                .introduction(donation.getIntroduction())
                .name(donation.getName())
                .plan(donation.getPlan())
                .profileUrl(donation.getProfileUrl())
                .registrant(donation.getRegistrant())
                .shortIntroduction(donation.getShortIntroduction())
                .target(donation.getTarget())
                .build();
    }
}
