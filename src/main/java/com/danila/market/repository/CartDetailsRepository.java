package com.danila.market.repository;

import com.danila.market.entity.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailsRepository extends JpaRepository<CartDetails, Integer> {
}
