package com.danila.market.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "carts")
public class Cart {
    private static final String SEQ_NAME = "carts_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private int id;
    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
//    @ManyToMany
//    @JoinTable(
//        name = "carts_products", joinColumns = @JoinColumn(name = "cart_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id")
//    )
//    private List<Product> product;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private List<CartDetails> details;
}
