package com.seereal.algi.model.campaign;

import com.seereal.algi.model.activity.Activity;
import com.seereal.algi.model.activity.ActivityRepository;
import com.seereal.algi.model.organization.Organization;
import com.seereal.algi.model.organization.OrganizationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CampaignRepositoryTest {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @BeforeEach
    void setUp() {
        Organization testOrganization = Organization.builder()
                .name("test")
                .businessReportLink("www1")
                .taxReportLink("www2")
                .build();
        organizationRepository.save(testOrganization);

        Campaign testCampaign = Campaign.builder()
                .name("campaign")
                .start("st1")
                .end("ed1")
                .build();
        testCampaign.setOrganization(testOrganization);
        campaignRepository.save(testCampaign);
    }

    @AfterEach
    void tearDown() {
        campaignRepository.deleteAll();
        organizationRepository.deleteAll();
    }

    @Test
    void saveCampaignTest(){
        Campaign campaign = campaignRepository.findAll().get(0);
        assertEquals(organizationRepository.findAll().get(0).getId(),
                campaign.getOrganization().getId());
        assertEquals(campaign.getName(), "campaign");
    }
}