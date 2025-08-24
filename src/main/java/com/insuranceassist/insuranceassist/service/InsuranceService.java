package com.insuranceassist.insuranceassist.service;

import com.insuranceassist.insuranceassist.entity.Insurance;
import com.insuranceassist.insuranceassist.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;

    @Autowired
    public InsuranceService(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    // Create or update an insurance
    public Insurance saveInsurance(Insurance insurance) {
        return insuranceRepository.save(insurance);
    }

    // Get insurances by customer ID
    public List<Insurance> getInsurancesByCustomerId(Long customerId) {
        return insuranceRepository.findByCustomerId(customerId);
    }

    // Get insurance by ID
    public Optional<Insurance> getInsuranceById(Long insuranceId) {
        return insuranceRepository.findById(insuranceId);
    }

    // Delete insurance by ID
    public void deleteInsurance(Long insuranceId) {
        insuranceRepository.deleteById(insuranceId);
    }

    // Get all insurances
    public List<Insurance> getAllInsurances() {
        return insuranceRepository.findAll();
    }
}
