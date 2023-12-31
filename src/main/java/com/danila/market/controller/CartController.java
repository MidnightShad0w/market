package com.danila.market.controller;

import com.danila.market.dto.CartRequest;
import com.danila.market.entity.Cart;
import com.danila.market.exceptions.AppError;
import com.danila.market.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    @PostMapping("/add")
    public ResponseEntity<?> addProductToCart(@RequestBody CartRequest cartRequest) {
        var cart = cartService.addProductToCart(cartRequest);
        return ResponseEntity.ok(cart);
    }
    @GetMapping("/get")
    public ResponseEntity<?> getCart(@RequestParam Integer userId) {
        var cart = cartService.getCart(userId);
        if (cart == null) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "Cart not found"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cart);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProductFromCart(@RequestBody CartRequest cartRequest) {
        Cart cart = cartService.deleteProductFromCart(cartRequest);
        return ResponseEntity.ok(cart);
    }

}
