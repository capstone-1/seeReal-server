package com.seereal.algi.dto.donation;

import com.seereal.algi.config.constant.S3Constants;
import com.seereal.algi.model.category.Category;
import com.seereal.algi.model.donation.Donation;
import com.seereal.algi.service.util.S3Util;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.seereal.algi.config.constant.S3Constants.DONATION_IMAGE;

@Getter
@NoArgsConstructor
public class SimpleDonationResponseDto {
    private Long id;
    private String name;
    private String registrant;
    private String shortIntroduction;
    private String profileUrl;
    private String target;
    private List<String> categories;

    public SimpleDonationResponseDto(Donation donation, String profileUrl) {
        this.id = donation.getId();
        this.name = donation.getName();
        this.registrant = donation.getRegistrant();
        this.shortIntroduction = donation.getShortIntroduction();
        this.profileUrl = profileUrl;
        this.target = donation.getTarget();
        this.categories = donation.getCategories().stream().map(Category::getName).collect(Collectors.toList());
    }

    public SimpleDonationResponseDto(Donation donation) {
        this.id = donation.getId();
        this.name = donation.getName();
        this.registrant = donation.getRegistrant();
        this.shortIntroduction = donation.getShortIntroduction();
        this.profileUrl = donation.getProfileUrl();
        this.target = donation.getTarget();
        this.categories = donation.getCategories().stream().map(Category::getName).collect(Collectors.toList());
    }
}
