package com.danila.market.entity;

import com.danila.market.Utils.HashMapConverter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Getter
@Setter
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
    private String detailsJSON;
    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> details;
}
