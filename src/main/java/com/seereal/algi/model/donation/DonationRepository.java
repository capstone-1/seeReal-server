package com.seereal.algi.model.donation;

import com.seereal.algi.model.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    Optional<Donation> findByName(String name);
    Page<Donation> findAll(Pageable pageable);

    @Query(value = "select dnt from Donation as dnt where dnt.name like %?1%")
    Page<Donation> findSearchName(String name, Pageable pageable);

    @Query(value = "select d from Category c inner join c.donations d where c.name = ?1")
    Page<Donation> findSearchCategory(String name, Pageable pageable);
}
