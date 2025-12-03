package com.sou.eCom.model.dto;

public record CustomerResponse(
        long customerId,
        String name,
        String email,
        String phoneNumber,
        String address,
        int pinCode

) {
}
