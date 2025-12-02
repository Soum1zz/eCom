package com.sou.eCom.model.dto;

import com.sou.eCom.model.Customer;

import java.time.LocalDate;
import java.util.List;

public record OrderResponse(
        String orderId,
        long customerId,
        String email,
        String status,
        LocalDate date,
        List<OrderItemResponse> orderItemResponseList
) {
}
