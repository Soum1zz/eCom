package com.sou.eCom.repo;

import com.sou.eCom.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    public Optional<Customer> findByCustomerId(long customerId);
}
