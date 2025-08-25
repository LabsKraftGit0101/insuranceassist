package com.insuranceassist.insuranceassist.service;

import com.insuranceassist.insuranceassist.entity.Insurance;
import com.insuranceassist.insuranceassist.repository.InsuranceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InsuranceModelServiceTest {

    @Mock
    private InsuranceRepository insuranceRepository;

    @InjectMocks
    private InsuranceService insuranceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveInsurance() {
        Insurance insurance = new Insurance();
        when(insuranceRepository.save(any(Insurance.class))).thenReturn(insurance);

        Insurance result = insuranceService.saveInsurance(insurance);

        assertEquals(insurance, result);
    }

    @Test
    void testGetInsurancesByCustomerId_Found() {
        List<Insurance> insurances = Arrays.asList(new Insurance(), new Insurance());
        when(insuranceRepository.findByCustomerId(1L)).thenReturn(insurances);

        List<Insurance> result = insuranceService.getInsurancesByCustomerId(1L);

        assertEquals(2, result.size());
    }

    @Test
    void testGetInsurancesByCustomerId_NotFound() {
        when(insuranceRepository.findByCustomerId(2L)).thenReturn(Collections.emptyList());

        List<Insurance> result = insuranceService.getInsurancesByCustomerId(2L);

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetInsuranceById_Found() {
        Insurance insurance = new Insurance();
        when(insuranceRepository.findById(1L)).thenReturn(Optional.of(insurance));

        Optional<Insurance> result = insuranceService.getInsuranceById(1L);

        assertTrue(result.isPresent());
        assertEquals(insurance, result.get());
    }

    @Test
    void testGetInsuranceById_NotFound() {
        when(insuranceRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Insurance> result = insuranceService.getInsuranceById(2L);

        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteInsurance() {
        doNothing().when(insuranceRepository).deleteById(1L);

        insuranceService.deleteInsurance(1L);

        verify(insuranceRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllInsurances() {
        List<Insurance> insurances = Arrays.asList(new Insurance(), new Insurance());
        when(insuranceRepository.findAll()).thenReturn(insurances);

        List<Insurance> result = insuranceService.getAllInsurances();

        assertEquals(insurances, result);
    }
}