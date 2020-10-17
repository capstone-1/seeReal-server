package com.seereal.algi.model.campaignReview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalCampaignReviewRepository extends JpaRepository<PersonalCampaignReview, Long> {
}
