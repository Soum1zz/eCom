package com.sou.eCom.controller;

import com.sou.eCom.model.dto.CartDto.CartResponse;
import com.sou.eCom.model.dto.CartDto.UpdateToCartRequest;
import com.sou.eCom.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("https://localhost:5173")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/customer/{customerId}/cart")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long customerId) {
       return new ResponseEntity<>(cartService.cart(customerId), HttpStatus.OK);
    }
    @PutMapping("/customer/{customerId}/cart/items")
    public ResponseEntity<CartResponse> updateCart(@PathVariable Long customerId,@RequestBody UpdateToCartRequest req) {
        return new ResponseEntity<> (cartService.updateCart(customerId, req), HttpStatus.OK);
    }
    @DeleteMapping("/customer/{customerId}/cart")
    public ResponseEntity<?> deleteCart(@PathVariable Long customerId) {
        cartService.deleteCart(customerId);
        return new ResponseEntity<>("Cart Deleted" ,HttpStatus.OK);
    }

    @DeleteMapping("/customer/{customerId}/cart/items/{productId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long customerId, @PathVariable Long productId) {
        cartService.deleteCartItem(customerId ,productId);
        return new ResponseEntity<>("Cart Item Deleted" ,HttpStatus.OK);
    }
}
