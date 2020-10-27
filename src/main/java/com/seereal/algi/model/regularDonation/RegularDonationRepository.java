package com.seereal.algi.model.regularDonation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegularDonationRepository extends JpaRepository<RegularDonation, Long> {
    Optional<RegularDonation> findByName(String name);
}
