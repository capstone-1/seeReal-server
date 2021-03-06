package com.seereal.algi.model.organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByName(String name);
    Optional<Organization> findByRegisterNumber(String registerNumber);
    Optional<Organization> findByEmailAndName(String email, String name);
}
