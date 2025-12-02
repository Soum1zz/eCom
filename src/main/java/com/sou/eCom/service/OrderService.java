package com.sou.eCom.service;

import com.sou.eCom.model.Customer;
import com.sou.eCom.model.Order;
import com.sou.eCom.model.OrderItem;
import com.sou.eCom.model.Product;
import com.sou.eCom.model.dto.OrderItemRequest;
import com.sou.eCom.model.dto.OrderItemResponse;
import com.sou.eCom.model.dto.OrderRequest;
import com.sou.eCom.model.dto.OrderResponse;
import com.sou.eCom.repo.CustomerRepo;
import com.sou.eCom.repo.OrderRepo;
import com.sou.eCom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private ProductRepo productRepo;
    private OrderRepo orderRepo;
    private CustomerRepo customerRepo;

    public List<OrderResponse> getAllOrderResponses() {

        List<Order> orders= orderRepo.findAll();

        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            List<OrderItemResponse>itemResponses = new ArrayList<>();
            for(OrderItem orderItem : order.getOrderItems()) {
                OrderItemResponse orderItemResponse= new OrderItemResponse(
                        orderItem.getProduct().getName(),
                        orderItem.getQuantity(),
                        orderItem.getTotalPrice()
                );
                itemResponses.add(orderItemResponse);
            }
            OrderResponse orderResponse = new OrderResponse(
                    order.getOrderId(),
                    order.getCustomer().getCustomerId(),
                    order.getEmail(),
                    order.getStatus(),
                    order.getOrderDate(),
                    itemResponses
            );
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }

    public OrderResponse placeOrder(OrderRequest req) {
        Customer customer = customerRepo.findByCustomerId(req.customerId())
                .orElseThrow(()->new RuntimeException("Customer Not Found"));
        Order order = new Order();
        String orderId="ORD"+ UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        order.setOrderId(orderId);
        order.setCustomer(customer);
        order.setEmail(req.email());
        order.setStatus("Placed");
        order.setOrderDate(LocalDate.now());

        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemRequest itemReq : req.items()) {
            Product product = productRepo.findByProductId(itemReq.productId())
                    .orElseThrow(()->new RuntimeException("Product not found"));
            product.setStock(product.getStock()-itemReq.productQuantity());
            productRepo.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemReq.productQuantity())
                    .totalPrice(product.getPrice()*itemReq.productQuantity())
                    .order(order)
                    .build();
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepo.save(order);

        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for(OrderItem orderItem : savedOrder.getOrderItems()) {
            OrderItemResponse orderItemResponse=new OrderItemResponse(
                    orderItem.getProduct().getName(),
                    orderItem.getQuantity(),
                    orderItem.getTotalPrice()
            );
            orderItemResponses.add(orderItemResponse);

        }
        OrderResponse orderResponse =new OrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getCustomer().getCustomerId(),
                savedOrder.getEmail(),
                savedOrder.getStatus(),
                savedOrder.getOrderDate(),
                orderItemResponses
                );
        return orderResponse;

    }
}
