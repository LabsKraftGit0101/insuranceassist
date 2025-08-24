package com.insuranceassist.insuranceassist.controller;

import com.insuranceassist.insuranceassist.entity.Customer;
import com.insuranceassist.insuranceassist.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCustomer() {
        Customer customer = new Customer();
        when(customerService.saveCustomer(any(Customer.class))).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.saveCustomer(customer);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(customer, response.getBody());
    }

    @Test
    void testGetCustomerByEmail_Found() {
        Customer customer = new Customer();
        when(customerService.findCustomerByEmail("test@example.com")).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.getCustomerByEmail("test@example.com");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(customer, response.getBody());
    }

    @Test
    void testGetCustomerByEmail_NotFound() {
        when(customerService.findCustomerByEmail("notfound@example.com")).thenReturn(null);

        ResponseEntity<Customer> response = customerController.getCustomerByEmail("notfound@example.com");

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testGetCustomerById_Found() {
        Customer customer = new Customer();
        when(customerService.findCustomerById(1L)).thenReturn(Optional.of(customer));

        ResponseEntity<Customer> response = customerController.getCustomerById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(customer, response.getBody());
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(customerService.findCustomerById(2L)).thenReturn(Optional.empty());

        ResponseEntity<Customer> response = customerController.getCustomerById(2L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerService).deleteCustomer(1L);

        ResponseEntity<Void> response = customerController.deleteCustomer(1L);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(customerService, times(1)).deleteCustomer(1L);
    }

    @Test
    void testGetAllCustomers() {
        Iterable<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerService.getAllCustomers()).thenReturn(customers);

        ResponseEntity<Iterable<Customer>> response = customerController.getAllCustomers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(customers, response.getBody());
    }
}