package com.insuranceassist.insuranceassist.controller;

import com.insuranceassist.insuranceassist.entity.Customer;
import com.insuranceassist.insuranceassist.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Register a new customer
     *
     * @param customer
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) {
        Customer existingCustomer = customerService.findCustomerByEmail(customer.getEmail());
        if (existingCustomer != null) {
            return new ResponseEntity<>("Customer already exists", HttpStatus.CONFLICT);
        }
        Customer savedCustomer = customerService.saveCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    /**
     * Login a customer
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        Customer customer = customerService.findCustomerByEmail(username);
        if (customer != null && customer.getPassword().equals(password)) {
            session.setAttribute("customerId", customer.getCustomerId());
            return ResponseEntity.ok("Login successful");
        }
        return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
    }

}
