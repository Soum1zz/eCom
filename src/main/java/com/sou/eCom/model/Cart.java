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
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    @OneToOne
    private Customer customer;
    @OneToMany(mappedBy = "Cart" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;

}
