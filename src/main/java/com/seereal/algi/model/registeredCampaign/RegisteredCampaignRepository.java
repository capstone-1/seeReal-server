package com.seereal.algi.model.registeredCampaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredCampaignRepository extends JpaRepository<RegisteredCampaign, Long> {
}
