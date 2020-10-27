package com.seereal.algi.model.regularDonation;

import com.seereal.algi.model.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class RegularDonation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "regular_donation_id")
    private Long id;
    @Column(unique = true)
    private String name;
    private String registrant;
    private String shortIntroduction;
    private String profileUrl;
    private String target;
    private String content;
    private String introduction;
    private String plan;

    @OneToMany(mappedBy = "regularDonation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DonationCostPreview> costPreviews = new ArrayList<>();

    @OneToMany(mappedBy = "regularDonation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DonationResult> results = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "regular_donation_category",
            joinColumns = @JoinColumn(name = "regular_donation_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    @Builder
    public RegularDonation(String name, String registrant, String shortIntroduction, String profileUrl, String target, String content, String introduction, String plan) {
        this.name = name;
        this.registrant = registrant;
        this.shortIntroduction = shortIntroduction;
        this.profileUrl = profileUrl;
        this.target = target;
        this.content = content;
        this.introduction = introduction;
        this.plan = plan;
    }
    public void addCategory(Category category) {
        this.categories.add(category);
    }
    public void setProfileUrl(String url) {
        this.profileUrl = url;
    }
}