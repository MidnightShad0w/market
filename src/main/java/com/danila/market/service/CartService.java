package com.danila.market.service;

import com.danila.market.dto.CartRequest;
import com.danila.market.dto.CartResponse;
import com.danila.market.dto.ProductRequest;
import com.danila.market.dto.ProductResponse;
import com.danila.market.entity.Cart;
import com.danila.market.entity.Details;
import com.danila.market.entity.Product;
import com.danila.market.entity.User;
import com.danila.market.repository.CartRepository;
import com.danila.market.repository.ProductRepository;
import com.danila.market.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Cart addProductToCart(CartRequest cartRequest) {
        int userId = cartRequest.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setDetails(new HashMap<>());
        }

        Map<String, Object> details = cart.getDetails();

        for (ProductRequest productRequest : cartRequest.getProducts()) {
            int productId = productRequest.getProductId();
            int amount = productRequest.getAmount();
            Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));
            double price = product.getPrice();
            if (details.containsKey(String.valueOf(productId))) {
                ProductResponse existingProductResponse = (ProductResponse) details.get(String.valueOf(productId));
                int existingAmount = existingProductResponse.getAmount();
                int newAmount = existingAmount + amount;
                existingProductResponse.setAmount(newAmount);
                existingProductResponse.setTotalPrice(newAmount*price);
            } else {
                details.put(String.valueOf(productId), new ProductResponse(productId, amount, price*amount));
            }
        }
        cart.setDetails(details);
        cartRepository.save(cart);
        return cart;
    }
    public Cart getCart(int userId) {
        CartResponse cartResponse = new CartResponse();
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        Cart cart = user.getCart();
        if (cart != null) {
            cartResponse.setUserId(userId);
            cartResponse.setProducts(cart.getDetails().stream()
                    .map(cartDetails -> new ProductResponse(cartDetails.getProduct().getId(), cartDetails.getAmount(), cartDetails.getPrice()))
                    .toList());
            return cartResponse;
        } else {
            return null;
        }
    }
    public void deleteProductFromCart(CartRequest cartRequest) {
        var userId = cartRequest.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        Cart cart = user.getCart();
        var productIds = cartRequest.getProducts().stream().map(productRequest -> productRequest.getProductId()).toList();
        List<Product> products = productRepository.findAllById(productIds);
        for (Product product : products) {
            Optional<Details> existingDetails = cart.getDetails().stream()
                    .filter(cd -> cd.getProduct().getId() == product.getId())
                    .findFirst();
            if (existingDetails.isPresent()) {
                Details details = existingDetails.get();
                int additionalAmount = cartRequest.getProducts().stream()
                        .filter(pr -> pr.getProductId() == product.getId())
                        .findFirst().get().getAmount();
                details.setAmount(details.getAmount() - additionalAmount);
                if (details.getAmount() <= 0) {
                    cart.getDetails().remove(details);
                } else {
                    details.setPrice(product.getPrice() * details.getAmount());
                }
            }
        }
        if (cart.getDetails().isEmpty()) {
            user.setCart(null);
            userRepository.save(user);
            cartRepository.delete(cart);
        } else {
            user.setCart(cart);
            userRepository.save(user);
            cartRepository.save(cart);
        }
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
