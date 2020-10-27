package com.seereal.algi.model.donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationCostResultRepository extends JpaRepository<DonationCostResult, Long> {
}
