package com.seereal.algi.model.donation;

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
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id")
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

    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DonationCostPreview> costPreviews = new ArrayList<>();

    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DonationResult> results = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "donation_category",
            joinColumns = @JoinColumn(name = "donation_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    @Builder
    public Donation(String name, String registrant, String shortIntroduction, String profileUrl, String target, String content, String introduction, String plan) {
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