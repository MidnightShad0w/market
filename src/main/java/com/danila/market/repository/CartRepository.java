package com.danila.market.repository;

import com.danila.market.entity.Cart;
import com.danila.market.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    boolean existsCartByUserId(int userId);
    Cart findCartByUserId(int userId);
    void deleteCartByUserId(int userId);
}
