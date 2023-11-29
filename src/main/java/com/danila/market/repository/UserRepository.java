package com.danila.market.repository;

import com.danila.market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findFirstByUsername(String username);
}
