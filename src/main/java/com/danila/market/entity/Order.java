package com.danila.market.entity;

import com.danila.market.Utils.HashMapConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Map;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    private static final String SEQ_NAME = "orders_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private int id;
    @CreationTimestamp
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private double sum;
    private String address;
    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> details;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
