// src/main/java/com/insuranceassist/insuranceassist/controller/InsuranceController.java
package com.insuranceassist.insuranceassist.controller;

import com.insuranceassist.insuranceassist.entity.Insurance;
import com.insuranceassist.insuranceassist.service.InsuranceService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/insurances")
public class InsuranceController {

    private final InsuranceService insuranceService;

    /**
     * Check if user is logged in
     * @param session
     * @return
     */
    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("customerId") != null;
    }

    /**
     * Create a new insurance
     *
     * @param insurance
     * @param session
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createInsurance(@RequestBody Insurance insurance, HttpSession session) {
        if (!isLoggedIn(session)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        Insurance savedInsurance = insuranceService.saveInsurance(insurance);
        return new ResponseEntity<>(savedInsurance, HttpStatus.CREATED);
    }

    /**
     * Get insurances by customer ID
     *
     * @param customerId
     * @param session
     * @return
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getInsurancesByCustomerId(@PathVariable Long customerId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        List<Insurance> insurances = insuranceService.getInsurancesByCustomerId(customerId);
        if (!insurances.isEmpty()) {
            return new ResponseEntity<>(insurances, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Get insurance by ID
     *
     * @param insuranceId
     * @param session
     * @return
     */
    @GetMapping("/{insuranceId}")
    public ResponseEntity<?> getInsuranceById(@PathVariable Long insuranceId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        Optional<Insurance> insurance = insuranceService.getInsuranceById(insuranceId);
        return insurance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Delete insurance by ID
     *
     * @param insuranceId
     * @param session
     * @return
     */
    @DeleteMapping("/{insuranceId}")
    public ResponseEntity<?> deleteInsurance(@PathVariable Long insuranceId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        insuranceService.deleteInsurance(insuranceId);
        return ResponseEntity.ok("Insurance deleted");
    }

    /**
     * Get all insurances
     *
     * @param session
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getAllInsurances(HttpSession session) {
        if (!isLoggedIn(session)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        List<Insurance> insurances = insuranceService.getAllInsurances();
        return new ResponseEntity<>(insurances, HttpStatus.OK);
    }
}