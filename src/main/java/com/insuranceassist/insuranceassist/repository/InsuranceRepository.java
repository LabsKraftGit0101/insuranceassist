package com.insuranceassist.insuranceassist.repository;

import com.insuranceassist.insuranceassist.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    // Custom query to find all insurances by customer ID
    List<Insurance> findByCustomerId(Long customerId);
}
