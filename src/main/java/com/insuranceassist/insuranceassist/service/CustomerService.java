package com.insuranceassist.insuranceassist.service;

import com.insuranceassist.insuranceassist.entity.Customer;
import com.insuranceassist.insuranceassist.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service to handle customer-related operations
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Create or update a customer
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Find a customer by email
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    // Find a customer by their ID
    public Optional<Customer> findCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    // Delete a customer by their ID
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    // List all customers (if needed for some use case)
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
