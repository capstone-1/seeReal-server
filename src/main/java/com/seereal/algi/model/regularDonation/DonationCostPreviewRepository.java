package com.seereal.algi.model.regularDonation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationCostPreviewRepository extends JpaRepository<DonationCostPreview, Long> {
}
