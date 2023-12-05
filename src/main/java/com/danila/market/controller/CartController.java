package com.danila.market.controller;

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

/*    @PostMapping("/add") //add?userId=?&productIds=?,?,...
    public ResponseEntity<?> addProductToCart(@RequestParam Integer userId, @RequestParam List<Integer> productIds) {
        var cart = cartService.addProductToCart(userId, productIds);
        if (cart == null) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Error adding products to cart"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(cart);
    }
    @DeleteMapping("/delete") //delete?userId=?&productIds=?,?,...
    public ResponseEntity<?> deleteProductFromCart(@RequestParam Integer userId, @RequestParam List<Integer> productIds) {
        cartService.deleteProductFromCart(userId, productIds);
        return ResponseEntity.ok("Products " + productIds + " deleted from cart");
    }*/
}
