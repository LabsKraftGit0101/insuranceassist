package com.insuranceassist.insuranceassist.service;

import com.insuranceassist.insuranceassist.entity.Insurance;
import com.insuranceassist.insuranceassist.model.InsuranceModel;
import com.insuranceassist.insuranceassist.repository.InsuranceRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service to handle insurance-related operations
 */
@Service
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;

    @Autowired
    public InsuranceService(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    // Create or update an insurance
    public Insurance saveInsurance(InsuranceModel insurance, HttpSession session) {
        Insurance insuranceEntity = new Insurance();
        // teake customerId from session
        Long customerId = (Long) session.getAttribute("customerId");
        insuranceEntity.setCustomerId(customerId);
        insuranceEntity.setDetails(insurance.getDetails());
        return insuranceRepository.save(insuranceEntity);
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
