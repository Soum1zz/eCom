package com.sou.eCom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String customerName;
    private String email;
    private String phoneNumber;
    private String address;
    private int pinCode;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,orphanRemoval = true)
    List<Order> orders;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,orphanRemoval = true)
    List<Comment> comments;
    //image
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;
}
