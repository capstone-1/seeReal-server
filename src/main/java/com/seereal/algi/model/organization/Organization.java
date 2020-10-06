package com.seereal.algi.model.organization;

import com.seereal.algi.model.taxincome.TaxIncomeSummary;
import com.seereal.algi.model.taxoutcome.TaxOutcomeSummary;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
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
    private String businessReportLink;
    private String taxReportLink;

    @OneToOne
    @JoinColumn(name = "tax_income_summary_id")
    private TaxIncomeSummary taxIncomeSummary;

    @OneToOne
    @JoinColumn(name = "tax_outcome_summary_id")
    private TaxOutcomeSummary taxOutcomeSummaryId;

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
        this.taxOutcomeSummaryId = null;
    }

    public void setTaxIncomeSummary(TaxIncomeSummary taxIncomeSummary) {
        this.taxIncomeSummary = taxIncomeSummary;
    }
    public void setTaxOutcomeSummaryId(TaxOutcomeSummary taxOutcomeSummaryId) {
        this.taxOutcomeSummaryId = taxOutcomeSummaryId;
    }
    public void setBusinessReportLink(String businessReportLink) {
        this.businessReportLink = businessReportLink;
    }
    public void setTaxReportLink(String taxReportLink) {
        this.taxReportLink = taxReportLink;
    }
}

