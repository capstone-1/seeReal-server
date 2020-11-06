package com.seereal.algi.model.organization;

import com.seereal.algi.model.activity.Activity;
import com.seereal.algi.model.campaign.Campaign;
import com.seereal.algi.model.campaigncost.CampaignCost;
import com.seereal.algi.model.taxincome.TaxIncomeSummary;
import com.seereal.algi.model.taxoutcome.TaxOutcomeSummary;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organization_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false, unique = true)
    private String registerNumber;
    @Column(nullable = false)
    private String account;
    @Column(length = 4000)
    private String businessReportLink;
    @Column(length = 4000)
    private String taxReportLink;

    @OneToOne
    @JoinColumn(name = "tax_income_summary_id")
    private TaxIncomeSummary taxIncomeSummary;

    @OneToOne
    @JoinColumn(name = "tax_outcome_summary_id")
    private TaxOutcomeSummary taxOutcomeSummary;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    private List<Activity> activityList = new ArrayList<Activity>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    private List<Campaign> campaignList = new ArrayList<Campaign>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    private List<CampaignCost> campaignCostList = new ArrayList<CampaignCost>();

    @Builder
    public Organization(String name, String password, String email, String phoneNumber, String registerNumber, String account) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.registerNumber = registerNumber;
        this.account = account;
        this.businessReportLink = null;
        this.taxReportLink = null;
        this.taxIncomeSummary = null;
        this.taxOutcomeSummary = null;
    }

    public void setTaxIncomeSummary(TaxIncomeSummary taxIncomeSummary) {
        this.taxIncomeSummary = taxIncomeSummary; // --------------------
    }

    public void setTaxOutcomeSummary(TaxOutcomeSummary taxOutcomeSummaryId) {
        this.taxOutcomeSummary = taxOutcomeSummaryId;
    }

    public void setBusinessReportLink(String businessReportLink) {
        this.businessReportLink = businessReportLink;
    }
    public void setTaxReportLink(String taxReportLink) {
        this.taxReportLink = taxReportLink;
    }

    public void passwordEncode(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }
}

