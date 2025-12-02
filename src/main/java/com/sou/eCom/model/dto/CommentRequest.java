package com.sou.eCom.model.dto;

import com.sou.eCom.model.Customer;
import com.sou.eCom.model.Product;

public record CommentRequest(
        long customerId,
        String desc,
        double rating
) {
}
