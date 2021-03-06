package com.seereal.algi.model.campaigncost;

import com.seereal.algi.model.campaign.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignCostRepository extends JpaRepository<CampaignCost, Long> {
}
