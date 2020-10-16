package com.seereal.algi.model.campaign;

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
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_id")
    private Long id;
    private String name;
    private String start;
    private String end;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Builder
    public Campaign(String name, String start, String end){
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public void setOrganization(Organization organization){
        this.organization = organization;
    }
}
