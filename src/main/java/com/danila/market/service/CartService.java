package com.danila.market.service;

import com.danila.market.dto.CartRequest;
import com.danila.market.dto.ProductRequest;
import com.danila.market.dto.ProductResponse;
import com.danila.market.entity.Cart;
import com.danila.market.entity.Product;
import com.danila.market.entity.User;
import com.danila.market.repository.CartRepository;
import com.danila.market.repository.ProductRepository;
import com.danila.market.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public Cart addProductToCart(CartRequest cartRequest) {
        int userId = cartRequest.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
            cart.setDetails(new HashMap<>());
        }

        Map<String, Object> details = cart.getDetails();

        for (ProductRequest productRequest : cartRequest.getProducts()) {
            int productId = productRequest.getProductId();
            int amount = productRequest.getAmount();
            Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));
            double price = product.getPrice();
            if (details.containsKey(String.valueOf(productId))) {
                ProductResponse existingProductResponse = objectMapper.convertValue(details.get(String.valueOf(productId)), ProductResponse.class);
                int existingAmount = existingProductResponse.getAmount();
                int newAmount = existingAmount + amount;
                existingProductResponse.setAmount(newAmount);
                existingProductResponse.setTotalPrice(newAmount*price);
                details.put(String.valueOf(productId), existingProductResponse);
            } else {
                details.put(String.valueOf(productId), new ProductResponse(productId, amount, price*amount));
            }
        }
        cart.setDetails(details);
        cartRepository.save(cart);
        userRepository.save(user);
        return cart;
    }
    public Cart getCart(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        Cart cart = user.getCart();
        if (cart != null) {
            return cart;
        } else {
            return null;
        }
    }
    public Cart deleteProductFromCart(CartRequest cartRequest) {
        var userId = cartRequest.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        Cart cart = user.getCart();

        Map<String, Object> details = cart.getDetails();

        for (ProductRequest productRequest : cartRequest.getProducts()) {
            int productId = productRequest.getProductId();
            int amount = productRequest.getAmount();
            Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));
            double price = product.getPrice();
            if (details.containsKey(String.valueOf(productId))) {
                ProductResponse existingProductResponse = objectMapper.convertValue(details.get(String.valueOf(productId)), ProductResponse.class);
                int existingAmount = existingProductResponse.getAmount();
                int newAmount = existingAmount - amount;
                if (newAmount <= 0) {
                    details.remove(String.valueOf(productId));
                } else {
                    existingProductResponse.setAmount(newAmount);
                    existingProductResponse.setTotalPrice(newAmount*price);
                    details.put(String.valueOf(productId), existingProductResponse);
                }
            }
        }
        cart.setDetails(details);
        if (cart.getDetails().isEmpty()) {
            user.setCart(null);
            userRepository.save(user);
            cartRepository.delete(cart);
        } else {
            cartRepository.save(cart);
        }
        return cart;
    }
    public void deleteCart(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        Cart cart = user.getCart();
        if (cart != null) {
            user.setCart(null);
            userRepository.save(user);
            cartRepository.delete(cart);
        }
    }
}
