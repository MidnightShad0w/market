package com.danila.market.controller;

import com.danila.market.dto.OrderRequest;
import com.danila.market.entity.Order;
import com.danila.market.service.EmailService;
import com.danila.market.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final EmailService emailService;

    @RequestMapping("/create")
    public void createOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);
        emailService.send("samsonchik2406@mail.ru", "new order", order.toString());
    }

    @RequestMapping("/sendmail")
    public void sendMail() {
        emailService.send("samsonchik2406@mail.ru", "new test", "new test1111");
    }
}
