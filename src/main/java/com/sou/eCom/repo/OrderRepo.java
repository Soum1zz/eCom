package com.sou.eCom.repo;

import com.sou.eCom.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    public Optional<Order> findByOrderId(Long orderId) ;
}
