package com.seereal.algi.model.category;

import com.seereal.algi.model.registeredCampaign.RegisteredCampaign;
import com.seereal.algi.model.regularDonation.RegularDonation;
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
    private List<RegularDonation> regularDonations = new ArrayList<>();

    public void addRegisteredCampaign(RegisteredCampaign registeredCampaign){
        this.registeredCampaigns.add(registeredCampaign);
    }
    public void addRegularDonation(RegularDonation regularDonation) {
        this.regularDonations.add(regularDonation);
    }
}
