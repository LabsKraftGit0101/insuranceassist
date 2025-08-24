package com.insuranceassist.insuranceassist.controller;

import com.insuranceassist.insuranceassist.entity.Insurance;
import com.insuranceassist.insuranceassist.service.InsuranceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InsuranceControllerTest {

    @Mock
    private InsuranceService insuranceService;

    @InjectMocks
    private InsuranceController insuranceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateInsurance() {
        Insurance insurance = new Insurance();
        when(insuranceService.saveInsurance(any(Insurance.class))).thenReturn(insurance);

        ResponseEntity<Insurance> response = insuranceController.createInsurance(insurance);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(insurance, response.getBody());
    }

    @Test
    void testGetInsurancesByCustomerId_Found() {
        List<Insurance> insurances = Arrays.asList(new Insurance(), new Insurance());
        when(insuranceService.getInsurancesByCustomerId(1L)).thenReturn(insurances);

        ResponseEntity<List<Insurance>> response = insuranceController.getInsurancesByCustomerId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(insurances, response.getBody());
    }

    @Test
    void testGetInsurancesByCustomerId_NotFound() {
        when(insuranceService.getInsurancesByCustomerId(2L)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Insurance>> response = insuranceController.getInsurancesByCustomerId(2L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testGetInsuranceById_Found() {
        Insurance insurance = new Insurance();
        when(insuranceService.getInsuranceById(1L)).thenReturn(Optional.of(insurance));

        ResponseEntity<Insurance> response = insuranceController.getInsuranceById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(insurance, response.getBody());
    }

    @Test
    void testGetInsuranceById_NotFound() {
        when(insuranceService.getInsuranceById(2L)).thenReturn(Optional.empty());

        ResponseEntity<Insurance> response = insuranceController.getInsuranceById(2L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteInsurance() {
        doNothing().when(insuranceService).deleteInsurance(1L);

        ResponseEntity<Void> response = insuranceController.deleteInsurance(1L);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(insuranceService, times(1)).deleteInsurance(1L);
    }

    @Test
    void testGetAllInsurances() {
        List<Insurance> insurances = Arrays.asList(new Insurance(), new Insurance());
        when(insuranceService.getAllInsurances()).thenReturn(insurances);

        ResponseEntity<List<Insurance>> response = insuranceController.getAllInsurances();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(insurances, response.getBody());
    }
}