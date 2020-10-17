package com.seereal.algi.model.campaignReview;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class PersonalCampaignReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_campaign_review_id")
    private Long id;
    private String content;

    public PersonalCampaignReview(String content) {
        this.content = content;
    }
}
