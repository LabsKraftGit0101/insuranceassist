package com.insuranceassist.insuranceassist.controller;

import com.insuranceassist.insuranceassist.entity.Insurance;
import com.insuranceassist.insuranceassist.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/insurances")
public class InsuranceController {

    private final InsuranceService insuranceService;

    // Create or update an insurance
    @PostMapping
    public ResponseEntity<Insurance> createInsurance(@RequestBody Insurance insurance) {
        Insurance savedInsurance = insuranceService.saveInsurance(insurance);
        return new ResponseEntity<>(savedInsurance, HttpStatus.CREATED);
    }

    // Get insurances by customer ID
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Insurance>> getInsurancesByCustomerId(@PathVariable Long customerId) {
        List<Insurance> insurances = insuranceService.getInsurancesByCustomerId(customerId);
        if (!insurances.isEmpty()) {
            return new ResponseEntity<>(insurances, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Get insurance by ID
    @GetMapping("/{insuranceId}")
    public ResponseEntity<Insurance> getInsuranceById(@PathVariable Long insuranceId) {
        Optional<Insurance> insurance = insuranceService.getInsuranceById(insuranceId);
        return insurance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete insurance by ID
    @DeleteMapping("/{insuranceId}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable Long insuranceId) {
        insuranceService.deleteInsurance(insuranceId);
        return ResponseEntity.noContent().build();
    }

    // Get all insurances
    @GetMapping
    public ResponseEntity<List<Insurance>> getAllInsurances() {
        List<Insurance> insurances = insuranceService.getAllInsurances();
        return new ResponseEntity<>(insurances, HttpStatus.OK);
    }
}
