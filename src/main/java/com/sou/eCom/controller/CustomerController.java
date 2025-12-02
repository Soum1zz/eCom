package com.sou.eCom.controller;

import com.sou.eCom.model.Customer;
import com.sou.eCom.repo.CustomerRepo;
import com.sou.eCom.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:5173")
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> customers(){
        return new ResponseEntity<>(customerService.getAllCustomer(), HttpStatus.OK);
    }

}
