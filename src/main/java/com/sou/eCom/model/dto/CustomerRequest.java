package com.sou.eCom.model.dto;

import com.sou.eCom.model.Comment;
import com.sou.eCom.model.Order;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

public record CustomerRequest(
         String name,
         String email,
         String phoneNumber,
         String address,
         int pinCode

) {
}
