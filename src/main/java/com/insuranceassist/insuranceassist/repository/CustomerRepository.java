package com.insuranceassist.insuranceassist.repository;

import com.insuranceassist.insuranceassist.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Add custom queries if needed, for example, finding a customer by email or username.
    Customer findByEmail(String email);
}
