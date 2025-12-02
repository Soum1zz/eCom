package com.sou.eCom.controller;

import com.sou.eCom.model.dto.OrderRequest;
import com.sou.eCom.model.dto.OrderResponse;
import com.sou.eCom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:5173")
public class OrderController {
    @Autowired
    private OrderService service;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orderResponseList=service.getAllOrderResponses();
        return new ResponseEntity<>(orderResponseList, HttpStatus.OK);
    }
    @PostMapping("/orders/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest req){
        OrderResponse orderResponse =service.placeOrder(req);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }


}
