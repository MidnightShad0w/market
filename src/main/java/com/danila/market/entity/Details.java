package com.danila.market.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//@Table(name = "details")
//public class Details {
//    private static final String SEQ_NAME = "details_seq";
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
//    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
//    private int id;
//    @ManyToOne
//    @JoinColumn(name = "cart_id")
//    private Cart cart;
//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Order order;
//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;
//    private int amount; // todo: rename to totalCount
//    private double price; // todo: rename to totalPrice
//    // todo: вместо Details добавить в Cart и Order поле Json - в Cart id и количество, где общая сумма вычисляется по ходу исполнения метода, а в Order - id, количество и цена на момент заказа
//}
