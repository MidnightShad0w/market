package com.danila.market.service;

import com.danila.market.dto.CartRequest;
import com.danila.market.dto.CartResponse;
import com.danila.market.dto.ProductResponse;
import com.danila.market.entity.Cart;
import com.danila.market.entity.CartDetails;
import com.danila.market.entity.Product;
import com.danila.market.entity.User;
import com.danila.market.repository.CartDetailsRepository;
import com.danila.market.repository.CartRepository;
import com.danila.market.repository.ProductRepository;
import com.danila.market.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartDetailsRepository cartDetailsRepository;

    public void addProductToCart(CartRequest cartRequest) {
        var userId = cartRequest.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setDetails(new ArrayList<>());
        }
        var productIds = cartRequest.getProducts().stream().map(productRequest -> productRequest.getProductId()).toList();
        List<Product> products = productRepository.findAllById(productIds);
        for (Product product : products) {
            Optional<CartDetails> existingCartDetails = cart.getDetails().stream()
                    .filter(cd -> cd.getProduct().getId() == product.getId())
                    .findFirst();
            if (existingCartDetails.isPresent()) {
                CartDetails cartDetails = existingCartDetails.get();
                int additionalAmount = cartRequest.getProducts().stream()
                        .filter(pr -> pr.getProductId() == product.getId())
                        .findFirst().get().getAmount();
                cartDetails.setAmount(cartDetails.getAmount() + additionalAmount);
                cartDetails.setPrice(product.getPrice() * cartDetails.getAmount());
            } else {
                CartDetails cartDetails = new CartDetails();
                cartDetails.setProduct(product);
                cartDetails.setCart(cart);
                cartDetails.setAmount(cartRequest.getProducts().stream()
                        .filter(pr -> pr.getProductId() == product.getId())
                        .findFirst().get().getAmount());
                cartDetails.setPrice(product.getPrice() * cartDetails.getAmount());
                cart.getDetails().add(cartDetails);
            }
        }
        user.setCart(cart);
        cartRepository.save(cart);
        userRepository.save(user);
    }
    public CartResponse getCart(int userId) {
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
            Optional<CartDetails> existingCartDetails = cart.getDetails().stream()
                    .filter(cd -> cd.getProduct().getId() == product.getId())
                    .findFirst();
            if (existingCartDetails.isPresent()) {
                CartDetails cartDetails = existingCartDetails.get();
                int additionalAmount = cartRequest.getProducts().stream()
                        .filter(pr -> pr.getProductId() == product.getId())
                        .findFirst().get().getAmount();
                cartDetails.setAmount(cartDetails.getAmount() - additionalAmount);
                if (cartDetails.getAmount() <= 0) {
                    cart.getDetails().remove(cartDetails);
                } else {
                    cartDetails.setPrice(product.getPrice() * cartDetails.getAmount());
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
}
