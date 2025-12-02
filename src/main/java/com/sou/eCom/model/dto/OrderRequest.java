package com.sou.eCom.model.dto;

import com.sou.eCom.model.Customer;

import java.util.List;

public record OrderRequest(
        long customerId,
        String email,
        List<OrderItemRequest> items
) {
}
