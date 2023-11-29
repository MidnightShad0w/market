package com.danila.market.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    private static final String SEQ_NAME = "users_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private int id;
    private String username;
    private String email;
    private String password;
    @OneToOne(cascade = CascadeType.REMOVE)
    private Cart cart;
    @Enumerated(EnumType.STRING)
    private Role role;
}
