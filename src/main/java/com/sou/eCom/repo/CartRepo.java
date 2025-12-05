package com.sou.eCom.repo;

import com.sou.eCom.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,Long> {

    void deleteByCustomerCustomerId(Long customerId);

    Optional<Cart> findByCustomerCustomerId(Long customerId);
}
