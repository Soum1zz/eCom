package com.sou.eCom.service;

import com.sou.eCom.model.Cart;
import com.sou.eCom.model.CartItem;
import com.sou.eCom.model.Product;
import com.sou.eCom.model.dto.CartDto.CartItemResponse;
import com.sou.eCom.model.dto.CartDto.CartResponse;
import com.sou.eCom.model.dto.CartDto.UpdateToCartRequest;
import com.sou.eCom.repo.CartItemRepo;
import com.sou.eCom.repo.CartRepo;
import com.sou.eCom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepo cartRepo;
    @Autowired
    ProductRepo productRepo;
    public void deleteCart(Long customerId) {
        cartRepo.delete(cartRepo.findByCustomerCustomerId(customerId).orElseThrow(()->new RuntimeException("cart not found")));
    }


    private CartResponse toCartResponse(Cart cart) {
        List<CartItem> cartItems= cart.getItems();
        List<CartItemResponse> cartItemResponses = new ArrayList<>();
        double totalPrice= 0.0;
        for (CartItem cartItem : cartItems) {
            CartItemResponse cartItemResponse= new CartItemResponse(
                    cartItem.getProduct().getId(),
                    cartItem.getProduct().getName(),
                    cartItem.getProduct().getPrice(),
                    cartItem.getQuantity(),
                    cartItem.getProduct().getPrice()*
                            cartItem.getQuantity()

            );

            cartItemResponses.add(cartItemResponse);
            totalPrice+=cartItem.getQuantity()*cartItem.getProduct().getPrice();
        }

        return new CartResponse(
                cart.getCustomer().getCustomerId(),
                cartItemResponses,
                totalPrice
        );
    }


    public CartResponse cart(Long customerId) {
        Cart cart= cartRepo.findByCustomerCustomerId(customerId).orElseThrow(()-> new RuntimeException("cart not found"));
        return toCartResponse(cart);

    }

    public CartResponse updateCart(Long customerId, UpdateToCartRequest request) {

        Cart cart= cartRepo.findByCustomerCustomerId(customerId).orElseThrow(()-> new RuntimeException("cart not found"));

        Product product= productRepo.findByProductId(request.productId()).orElseThrow(()-> new RuntimeException("product not found"));

        CartItem existingItem= cart.getItems().stream()
                        .filter(item -> item.getProduct().getId().equals(product.getId())).findFirst().orElse(null);
         if(request.quantity()==0&& existingItem!=null){
            cart.getItems().remove(existingItem);
        }
        else if(existingItem!=null){
            existingItem.setQuantity(request.quantity());
        }
        else{
            cart.getItems().add(CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.quantity())
                    .build()
            );
        }
        cartRepo.save(cart);
        return toCartResponse(cart);

    }


    public void deleteCartItem(Long customerId, Long productId) {
        Cart cart = cartRepo.findByCustomerCustomerId(customerId).orElseThrow(()->new RuntimeException("cart not found"));
        CartItem cartItem= cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst().orElseThrow(()->new RuntimeException("product not found"));
        cart.getItems().remove(cartItem);
        cartRepo.save(cart);
    }
}
