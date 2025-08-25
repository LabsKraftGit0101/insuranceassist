package com.insuranceassist.insuranceassist.controller;

import com.insuranceassist.insuranceassist.entity.Customer;
import com.insuranceassist.insuranceassist.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerModelControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterCustomer_NewCustomer() {
        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        when(customerService.findCustomerByEmail("test@example.com")).thenReturn(null);
        when(customerService.saveCustomer(customer)).thenReturn(customer);

        ResponseEntity<?> response = customerController.registerCustomer(customer);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(customer, response.getBody());
    }

    @Test
    void testRegisterCustomer_ExistingCustomer() {
        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        when(customerService.findCustomerByEmail("test@example.com")).thenReturn(customer);

        ResponseEntity<?> response = customerController.registerCustomer(customer);

        assertEquals(409, response.getStatusCodeValue());
        assertEquals("Customer already exists", response.getBody());
    }

    @Test
    void testLogin_Success() {
        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        customer.setPassword("pass");
        customer.setCustomerId(1L);
        when(customerService.findCustomerByEmail("test@example.com")).thenReturn(customer);

        ResponseEntity<String> response = customerController.login("test@example.com", "pass", session);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Login successful", response.getBody());
        verify(session).setAttribute("customerId", 1L);
    }

    @Test
    void testLogin_Failure() {
        when(customerService.findCustomerByEmail("test@example.com")).thenReturn(null);

        ResponseEntity<String> response = customerController.login("test@example.com", "wrong", session);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody());
    }
}