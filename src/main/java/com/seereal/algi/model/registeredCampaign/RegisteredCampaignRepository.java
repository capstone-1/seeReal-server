package com.seereal.algi.model.registeredCampaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegisteredCampaignRepository extends JpaRepository<RegisteredCampaign, Long> {
    Optional<RegisteredCampaign> findByCampaignName(String name);

    @Query("SELECT r FROM RegisteredCampaign as r where r.isApprove = false")
    List<RegisteredCampaign> findAllNotApproved();
}
