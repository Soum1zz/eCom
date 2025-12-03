package com.sou.eCom.service;

import com.sou.eCom.model.Comment;
import com.sou.eCom.model.Customer;
import com.sou.eCom.model.Order;
import com.sou.eCom.model.OrderItem;
import com.sou.eCom.model.dto.*;
import com.sou.eCom.repo.CommentRepo;
import com.sou.eCom.repo.CustomerRepo;
import com.sou.eCom.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    CommentRepo commentRepo;
    @Autowired
    OrderRepo orderRepo;
    private CustomerResponse toCustomerResponse(Customer  customer){
        CustomerResponse customerResponse = new CustomerResponse(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getAddress(),
                customer.getPinCode()
        );
        return customerResponse;
    }



    public List<CustomerResponse> getAllCustomer() {
        List<CustomerResponse> customers = new ArrayList<>();
        List<Customer> customerList = customerRepo.findAll();
        for (Customer customer : customerList) {
            customers.add(toCustomerResponse(customer));
        }
        return customers;
    }

    public CustomerResponse getCustomer(Long customerId) {
        Customer customer= customerRepo.findByCustomerId(customerId).orElseThrow(()->new RuntimeException("Customer Not Found"));
        return toCustomerResponse(customer);
    }

    public List<OrderResponse> getCustomerOrder(Long customerId) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        List<Order>orders= orderRepo.findByCustomerCustomerId(customerId);
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

    public CustomerResponse addCustomer(CustomerRequest customer, MultipartFile picture) throws IOException {
        Customer customer1= new Customer();
        customer1.setCustomerName(customer.name());
        customer1.setEmail(customer.email());
        customer1.setPinCode(customer.pinCode());
        customer1.setAddress(customer.address());
        customer1.setPhoneNumber(customer.phoneNumber());

        if(picture!=null){
            customer1.setImageName(picture.getOriginalFilename());
            customer1.setImageType(picture.getContentType());
            customer1.setImageData(picture.getBytes());
        }
        Customer savedCustomer=customerRepo.save(customer1);


        return toCustomerResponse(savedCustomer);
    }

    public CustomerResponse updateCustomer(CustomerRequest customer, MultipartFile picture, Long customerId) throws IOException {
        Customer customer1= customerRepo.findByCustomerId(customerId).orElseThrow(()->new RuntimeException("Customer Not Found"));
        customer1.setCustomerName(customer.name());
        customer1.setEmail(customer.email());
        customer1.setPinCode(customer.pinCode());
        customer1.setAddress(customer.address());
        customer1.setPhoneNumber(customer.phoneNumber());

        if(picture!=null){
            customer1.setImageName(picture.getOriginalFilename());
            customer1.setImageType(picture.getContentType());
            customer1.setImageData(picture.getBytes());
        }
        Customer updatedCustomer=customerRepo.save(customer1);
        return toCustomerResponse(updatedCustomer);
    }

    public void deleteCustomer(Long customerId) {
        customerRepo.deleteById(customerId);
    }

    public List<CommentResponse> getCustomerComments(Long customerId) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        List<Comment>comments= commentRepo.findByCustomerCustomerId(customerId);
        for (Comment comment : comments) {
            commentResponses.add(new CommentResponse(
                    comment.getId(),
                    comment.getCreatedDate(),
                    comment.getCommentBody(),
                    comment.getRating(),
                    comment.getCustomer().getCustomerId(),
                    comment.getCustomer().getCustomerName(),
                    comment.getImageName(),
                    comment.getImageType(),
                    comment.getImageData()
            ));

        }
        return commentResponses;
    }
}
