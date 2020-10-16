package com.seereal.algi.model.activity;

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
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long id;
    private String name;
    private String performance;
    private String limitation;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Builder
    public Activity(String name, String performance, String limitation){
        this.name = name;
        this.performance = performance;
        this.limitation = limitation;
    }

    public void setOrganization(Organization organization){
        this.organization = organization;
    }
}
