package com.danila.market.service;

import com.danila.market.dto.OrderRequest;
import com.danila.market.dto.ProductResponse;
import com.danila.market.entity.Cart;
import com.danila.market.entity.Order;
import com.danila.market.entity.OrderStatus;
import com.danila.market.entity.User;
import com.danila.market.repository.OrderRepository;
import com.danila.market.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final ObjectMapper objectMapper;

    public Order createOrder(OrderRequest orderRequest) {
        var userId = orderRequest.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        Cart cart = user.getCart();
        Map<String, Object> details = cart.getDetails();

        Order order = Order.builder()
                .user(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found")))
                .address(orderRequest.getAddress())
                .sum(calculateSum(details))
                .status(OrderStatus.NEW)
                .details(details)
                .build();

        cartService.deleteCart(userId);
        orderRepository.save(order);
        return order;
    }
    private double calculateSum(Map<String, Object> details) {
        double sum = 0;
        for (Map.Entry<String, Object> entry : details.entrySet()) {
            ProductResponse productResponse = objectMapper.convertValue(entry.getValue(), ProductResponse.class);
            sum += productResponse.getTotalPrice();
        }
        return sum;
    }
}
