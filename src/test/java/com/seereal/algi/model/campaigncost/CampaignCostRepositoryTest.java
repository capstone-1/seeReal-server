package com.seereal.algi.model.campaigncost;

import com.seereal.algi.model.campaign.Campaign;
import com.seereal.algi.model.campaign.CampaignRepository;
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
class CampaignCostRepositoryTest {
    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private CampaignCostRepository campaignCostRepository;

    @BeforeEach
    void setUp() {
        Organization testOrganization = Organization.builder()
                .name("test")
                .businessReportLink("www1")
                .taxReportLink("www2")
                .build();
        organizationRepository.save(testOrganization);

        CampaignCost campaignCost = CampaignCost.builder()
                .useDate("useDate")
                .content("content")
                .cost(10000)
                .build();
        campaignCost.setOrganization(testOrganization);
        campaignCostRepository.save(campaignCost);
    }

    @AfterEach
    void tearDown() {
        campaignCostRepository.deleteAll();
        organizationRepository.deleteAll();
    }

    @Test
    void saveCampaignCost(){
        CampaignCost campaignCost = campaignCostRepository.findAll().get(0);
        assertEquals(organizationRepository.findAll().get(0).getId(),
                campaignCost.getOrganization().getId());
        assertEquals(campaignCost.getCost(), 10000);
    }
}