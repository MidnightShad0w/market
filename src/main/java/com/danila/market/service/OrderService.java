package com.danila.market.service;

import com.danila.market.dto.CartResponse;
import com.danila.market.dto.OrderRequest;
import com.danila.market.entity.*;
import com.danila.market.repository.OrderRepository;
import com.danila.market.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

    public void createOrder(OrderRequest orderRequest) {
        var cartResponse = cartService.getCart(orderRequest.getUserId());
        User user = userRepository.findById(orderRequest.getUserId()).orElseThrow(() -> new EntityNotFoundException("User with id " + orderRequest.getUserId() + " not found"));
        Cart cart = user.getCart();
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (var cartDetails : cart.getDetails()) {
            orderDetailsList.add(OrderDetails.builder()
                    .product(cartDetails.getProduct())
                    .amount(cartDetails.getAmount())
                    .price(cartDetails.getPrice())
                    .build()
            );
        }
        Order order = Order.builder()
                .user(userRepository.findById(orderRequest.getUserId()).orElseThrow(() -> new EntityNotFoundException("User with id " + orderRequest.getUserId() + " not found")))
                .address(orderRequest.getAddress())
                .sum(calculateSum(cartResponse))
                .status(OrderStatus.NEW)
                .details(orderDetailsList)
                .build();
        for (var orderDetails : orderDetailsList) {
            orderDetails.setOrder(order);
            cartService.deleteCart(orderRequest.getUserId());
        }
        orderRepository.save(order);
    }
    private double calculateSum(CartResponse cartResponse) {
        double sum = 0;
        for (var product : cartResponse.getProducts()) {
            sum += product.getPrice();
        }
        return sum;
    }
}
