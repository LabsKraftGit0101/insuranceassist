package com.insuranceassist.insuranceassist.service;

import com.insuranceassist.insuranceassist.entity.Insurance;
import com.insuranceassist.insuranceassist.model.InsuranceModel;
import com.insuranceassist.insuranceassist.repository.InsuranceRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InsuranceServiceTest {

    @Mock
    private InsuranceRepository insuranceRepository;

    @Mock
    private HttpSession session;

    @InjectMocks
    private InsuranceService insuranceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveInsurance() {
        InsuranceModel insuranceModel = new InsuranceModel();
        insuranceModel.setDetails("Test Details");
        when(session.getAttribute("customerId")).thenReturn(10L);

        Insurance insuranceEntity = new Insurance();
        insuranceEntity.setCustomerId(10L);
        insuranceEntity.setDetails("Test Details");

        when(insuranceRepository.save(any(Insurance.class))).thenReturn(insuranceEntity);

        Insurance result = insuranceService.saveInsurance(insuranceModel, session);

        assertNotNull(result);
        assertEquals(10L, result.getCustomerId());
        assertEquals("Test Details", result.getDetails());
        verify(insuranceRepository, times(1)).save(any(Insurance.class));
    }

    @Test
    void testGetInsurancesByCustomerId() {
        List<Insurance> insurances = Arrays.asList(new Insurance(), new Insurance());
        when(insuranceRepository.findByCustomerId(5L)).thenReturn(insurances);

        List<Insurance> result = insuranceService.getInsurancesByCustomerId(5L);

        assertEquals(2, result.size());
        verify(insuranceRepository, times(1)).findByCustomerId(5L);
    }

    @Test
    void testGetInsuranceById_Found() {
        Insurance insurance = new Insurance();
        when(insuranceRepository.findById(7L)).thenReturn(Optional.of(insurance));

        Optional<Insurance> result = insuranceService.getInsuranceById(7L);

        assertTrue(result.isPresent());
        assertEquals(insurance, result.get());
        verify(insuranceRepository, times(1)).findById(7L);
    }

    @Test
    void testGetInsuranceById_NotFound() {
        when(insuranceRepository.findById(8L)).thenReturn(Optional.empty());

        Optional<Insurance> result = insuranceService.getInsuranceById(8L);

        assertFalse(result.isPresent());
        verify(insuranceRepository, times(1)).findById(8L);
    }

    @Test
    void testDeleteInsurance() {
        doNothing().when(insuranceRepository).deleteById(12L);

        insuranceService.deleteInsurance(12L);

        verify(insuranceRepository, times(1)).deleteById(12L);
    }

    @Test
    void testGetAllInsurances() {
        List<Insurance> insurances = Arrays.asList(new Insurance(), new Insurance(), new Insurance());
        when(insuranceRepository.findAll()).thenReturn(insurances);

        List<Insurance> result = insuranceService.getAllInsurances();

        assertEquals(3, result.size());
        verify(insuranceRepository, times(1)).findAll();
    }
}