package com.seereal.algi.model.suggestedCampaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestedCampaignRepository extends JpaRepository<SuggestedCampaign, Long> {
}
