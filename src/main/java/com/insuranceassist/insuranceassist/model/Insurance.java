package com.insuranceassist.insuranceassist.model;

import lombok.Data;

@Data
public class Insurance {
    // Insurance(insuranceid,customerid, details)
    private Long insuranceId;
    private Long customerId;
    private String details;

}
