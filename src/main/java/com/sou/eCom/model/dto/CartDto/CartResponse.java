package com.sou.eCom.model.dto.CartDto;

import java.util.List;

public record CartResponse(

        Long customerId,
        List<CartItemResponse> items,
        double totalPrice

) {
}
