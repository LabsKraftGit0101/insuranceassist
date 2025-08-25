package com.insuranceassist.insuranceassist.controller;

import com.insuranceassist.insuranceassist.entity.Insurance;
import com.insuranceassist.insuranceassist.service.InsuranceService;
import jakarta.servlet.http.HttpSession;
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

class InsuranceModelControllerTest {

    @Mock
    private InsuranceService insuranceService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private InsuranceController insuranceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private void mockLoggedIn() {
        when(session.getAttribute("customerId")).thenReturn(1L);
    }

    @Test
    void testCreateInsurance_Unauthorized() {
        when(session.getAttribute("customerId")).thenReturn(null);
        Insurance insurance = new Insurance();

        ResponseEntity<?> response = insuranceController.createInsurance(insurance, session);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Unauthorized", response.getBody());
    }

    @Test
    void testCreateInsurance_Authorized() {
        mockLoggedIn();
        Insurance insurance = new Insurance();
        when(insuranceService.saveInsurance(insurance)).thenReturn(insurance);

        ResponseEntity<?> response = insuranceController.createInsurance(insurance, session);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(insurance, response.getBody());
    }

    @Test
    void testGetInsurancesByCustomerId_Unauthorized() {
        when(session.getAttribute("customerId")).thenReturn(null);

        ResponseEntity<?> response = insuranceController.getInsurancesByCustomerId(1L, session);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Unauthorized", response.getBody());
    }

    @Test
    void testGetInsurancesByCustomerId_Found() {
        mockLoggedIn();
        List<Insurance> insurances = Arrays.asList(new Insurance(), new Insurance());
        when(insuranceService.getInsurancesByCustomerId(1L)).thenReturn(insurances);

        ResponseEntity<?> response = insuranceController.getInsurancesByCustomerId(1L, session);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(insurances, response.getBody());
    }

    @Test
    void testGetInsurancesByCustomerId_NotFound() {
        mockLoggedIn();
        when(insuranceService.getInsurancesByCustomerId(2L)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = insuranceController.getInsurancesByCustomerId(2L, session);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testGetInsuranceById_Unauthorized() {
        when(session.getAttribute("customerId")).thenReturn(null);

        ResponseEntity<?> response = insuranceController.getInsuranceById(1L, session);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Unauthorized", response.getBody());
    }

    @Test
    void testGetInsuranceById_Found() {
        mockLoggedIn();
        Insurance insurance = new Insurance();
        when(insuranceService.getInsuranceById(1L)).thenReturn(Optional.of(insurance));

        ResponseEntity<?> response = insuranceController.getInsuranceById(1L, session);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(insurance, response.getBody());
    }

    @Test
    void testGetInsuranceById_NotFound() {
        mockLoggedIn();
        when(insuranceService.getInsuranceById(2L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = insuranceController.getInsuranceById(2L, session);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteInsurance_Unauthorized() {
        when(session.getAttribute("customerId")).thenReturn(null);

        ResponseEntity<?> response = insuranceController.deleteInsurance(1L, session);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Unauthorized", response.getBody());
    }

    @Test
    void testDeleteInsurance_Authorized() {
        mockLoggedIn();
        doNothing().when(insuranceService).deleteInsurance(1L);

        ResponseEntity<?> response = insuranceController.deleteInsurance(1L, session);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Insurance deleted", response.getBody());
        verify(insuranceService, times(1)).deleteInsurance(1L);
    }

    @Test
    void testGetAllInsurances_Unauthorized() {
        when(session.getAttribute("customerId")).thenReturn(null);

        ResponseEntity<?> response = insuranceController.getAllInsurances(session);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Unauthorized", response.getBody());
    }

    @Test
    void testGetAllInsurances_Authorized() {
        mockLoggedIn();
        List<Insurance> insurances = Arrays.asList(new Insurance(), new Insurance());
        when(insuranceService.getAllInsurances()).thenReturn(insurances);

        ResponseEntity<?> response = insuranceController.getAllInsurances(session);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(insurances, response.getBody());
    }
}