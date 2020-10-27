package com.seereal.algi.model.category;

import com.seereal.algi.model.registeredCampaign.RegisteredCampaign;
import com.seereal.algi.model.donation.Donation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    private List<RegisteredCampaign> registeredCampaigns = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    private List<Donation> donations = new ArrayList<>();

    public void addRegisteredCampaign(RegisteredCampaign registeredCampaign){
        this.registeredCampaigns.add(registeredCampaign);
    }
    public void addRegularDonation(Donation donation) {
        this.donations.add(donation);
    }
}
