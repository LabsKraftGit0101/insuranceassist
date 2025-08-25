package com.insuranceassist.insuranceassist.service;
import com.insuranceassist.insuranceassist.entity.Customer;
import com.insuranceassist.insuranceassist.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerModelServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCustomer() {
        Customer customer = new Customer();
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.saveCustomer(customer);

        assertEquals(customer, result);
    }

    @Test
    void testFindCustomerByEmail_Found() {
        Customer customer = new Customer();
        when(customerRepository.findByEmail("test@example.com")).thenReturn(customer);

        Customer result = customerService.findCustomerByEmail("test@example.com");

        assertEquals(customer, result);
    }

    @Test
    void testFindCustomerByEmail_NotFound() {
        when(customerRepository.findByEmail("notfound@example.com")).thenReturn(null);

        Customer result = customerService.findCustomerByEmail("notfound@example.com");

        assertNull(result);
    }

    @Test
    void testFindCustomerById_Found() {
        Customer customer = new Customer();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.findCustomerById(1L);

        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
    }

    @Test
    void testFindCustomerById_NotFound() {
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Customer> result = customerService.findCustomerById(2L);

        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerRepository).deleteById(1L);

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).deleteById(1L);
    }
}