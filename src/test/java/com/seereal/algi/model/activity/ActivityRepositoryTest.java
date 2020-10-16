package com.seereal.algi.model.activity;
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
class ActivityRepositoryTest {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @BeforeEach
    void setUp() {
        Organization testOrganization = Organization.builder()
                .name("test")
                .businessReportLink("www1")
                .taxReportLink("www2")
                .build();
        organizationRepository.save(testOrganization);

        Activity testActivity = Activity.builder()
                .name("activity")
                .performance("performance")
                .limitation("limitation")
                .build();
        testActivity.setOrganization(testOrganization);
        activityRepository.save(testActivity);
    }

    @AfterEach
    void tearDown() {
        activityRepository.deleteAll();
        organizationRepository.deleteAll();
    }

    @Test
    void saveActivity(){
        Activity activity = activityRepository.findAll().get(0);
        assertEquals(organizationRepository.findAll().get(0).getId(),
                activity.getOrganization().getId());
        assertEquals(activity.getName(), "activity");
    }
}