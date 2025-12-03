package com.sou.eCom.controller;

import com.sou.eCom.model.Customer;
import com.sou.eCom.model.Order;
import com.sou.eCom.model.dto.CommentResponse;
import com.sou.eCom.model.dto.CustomerRequest;
import com.sou.eCom.model.dto.CustomerResponse;
import com.sou.eCom.model.dto.OrderResponse;
import com.sou.eCom.repo.CustomerRepo;
import com.sou.eCom.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:5173")
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponse>> customers(){
        return new ResponseEntity<>(customerService.getAllCustomer(), HttpStatus.OK);
    }
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CustomerResponse> customer(@PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(customerService.getCustomer(customerId), HttpStatus.OK);
    }
    @GetMapping("/customer/{customerId}/orders")
    public ResponseEntity<List<OrderResponse>> orders(@PathVariable("customerId") Long customerId){
        return new ResponseEntity<>(customerService.getCustomerOrder(customerId),HttpStatus.OK);
    }
    @GetMapping("/customer/{customerId}/comments")
    public ResponseEntity<List<CommentResponse>> comments(@PathVariable("customerId") Long customerId){
        return new ResponseEntity<>(customerService.getCustomerComments(customerId),HttpStatus.OK);
    }
    @PostMapping("/customers")
    public ResponseEntity<CustomerResponse> addCustomer(@RequestPart CustomerRequest customer ,@RequestPart MultipartFile picture) throws IOException {
        return new ResponseEntity<>(customerService.addCustomer(customer, picture),HttpStatus.CREATED);
    }
    @PutMapping("/customer/{customerId}")
    public  ResponseEntity<CustomerResponse> updateCustomer(@RequestBody CustomerRequest customer, @RequestPart MultipartFile picture, @PathVariable("customerId") Long customerId) throws IOException {
        return new ResponseEntity<>(customerService.updateCustomer(customer, picture,customerId),HttpStatus.OK);
    }
    @DeleteMapping("/customer/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId){
        customerService.deleteCustomer(customerId);
    }


}
