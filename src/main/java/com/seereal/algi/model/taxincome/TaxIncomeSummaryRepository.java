package com.seereal.algi.model.taxincome;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxIncomeSummaryRepository extends JpaRepository<TaxIncomeSummary,Long> {
}
