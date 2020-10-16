package com.seereal.algi.model.campaigncost;
import com.seereal.algi.model.organization.Organization;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CampaignCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_cost_id")
    private Long id;
    private String useDate;
    private String content;
    private Integer cost;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Builder
    public CampaignCost(String useDate, String content, Integer cost){
        this.useDate = useDate;
        this.content = content;
        this.cost = cost;
    }

    public void setOrganization(Organization organization){
        this.organization = organization;
    }
}