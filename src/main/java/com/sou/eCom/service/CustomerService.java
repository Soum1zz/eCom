package com.sou.eCom.service;

import com.sou.eCom.model.Customer;
import com.sou.eCom.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    public List<Customer> getAllCustomer() {
        return customerRepo.findAll();
    }
}
