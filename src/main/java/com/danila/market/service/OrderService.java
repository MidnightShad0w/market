package com.danila.market.service;

import com.danila.market.dto.CartResponse;
import com.danila.market.dto.OrderRequest;
import com.danila.market.entity.*;
import com.danila.market.repository.OrderRepository;
import com.danila.market.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

//    public void createOrder(OrderRequest orderRequest) {
//        var userId = orderRequest.getUserId();
//        var cartResponse = cartService.getCart(userId);
//        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
//        Cart cart = user.getCart();
//        List<Details> detailsList = cart.getDetails();
//
////        List<OrderDetails> orderDetailsList = new ArrayList<>();
////        for (var cartDetails : cart.getDetails()) {
////            orderDetailsList.add(OrderDetails.builder()
////                    .product(cartDetails.getProduct())
////                    .amount(cartDetails.getAmount())
////                    .price(cartDetails.getPrice())
////                    .build()
////            );
////        }
//        Order order = Order.builder()
//                .user(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found")))
//                .address(orderRequest.getAddress())
//                .sum(calculateSum(cartResponse))
//                .status(OrderStatus.NEW)
////                .details(detailsList)
//                .build();
//
//        for (var details : detailsList) {
//            details.setOrder(order);
//            details.setCart(null);
//        }
//        cartService.deleteCart(userId); //todo: repair cart_id=null, order_id=1,2,3...
//        order.setDetails(detailsList);
//        orderRepository.save(order);
//    }
    private double calculateSum(CartResponse cartResponse) {
        double sum = 0;
        for (var product : cartResponse.getProducts()) {
            sum += product.getTotalPrice();
        }
        return sum;
    }
}
