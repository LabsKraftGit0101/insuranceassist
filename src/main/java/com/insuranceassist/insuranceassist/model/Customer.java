package com.insuranceassist.insuranceassist.model;

import lombok.Data;

@Data
public class Customer {
    // Customer attributes are customerId, name, email, age ,password
    private Long customerId;
    private String name;
    private String email;
    private int age;
    private String password;

}
