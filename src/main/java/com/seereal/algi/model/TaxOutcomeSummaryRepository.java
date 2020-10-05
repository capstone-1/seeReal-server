package com.seereal.algi.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxOutcomeSummaryRepository extends JpaRepository<TaxOutcomeSummary, Long> {
}
