package com.danila.market.service;

import com.danila.market.dto.CartDTO;
import com.danila.market.entity.Cart;
import com.danila.market.entity.Product;
import com.danila.market.entity.User;
import com.danila.market.repository.CartRepository;
import com.danila.market.repository.ProductRepository;
import com.danila.market.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartDTO addProductToCart(int userId, List<Integer> productIds) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setProduct(new ArrayList<>());
        }
        List<Product> productList = productRepository.findAllById(productIds);
        cart.getProduct().addAll(productList);
        Cart savedCart = cartRepository.save(cart);
        user.setCart(savedCart);
        userRepository.save(user);
        return createCartDTO(savedCart);
    }
    private CartDTO createCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        var productAmount = new HashMap<Product, Double>();
        for (Product product : cart.getProduct()) {
            if (productAmount.containsKey(product)) {
                productAmount.put(product, productAmount.get(product) + 1);
            } else {
                productAmount.put(product, 1.0);
            }
        }
        cartDTO.setProductAmount(productAmount);
        cartDTO.setTotalPrice(calculateTotalPrice(productAmount));
        return cartDTO;
    }
    private Double calculateTotalPrice(HashMap<Product, Double> productAmount) {
        var totalPrice = 0;
        for (var entry : productAmount.entrySet()) {
            totalPrice += entry.getKey().getPrice() * entry.getValue();
        }
        return (double) totalPrice;
    }
    public void deleteProductFromCart(int userId, List<Integer> productIds) {
        List<Product> newProductsList = productRepository.findAllById(productIds);
        Cart cart = cartRepository.findCartByUserId(userId);
        List<Product> currentProductsList = cart.getProduct();
        currentProductsList.removeAll(newProductsList);
        cart.setProduct(currentProductsList);
        cartRepository.save(cart);
    }
    public void deleteCart(int userId) {
        if (cartRepository.existsCartByUserId(userId)) {
            cartRepository.deleteCartByUserId(userId);
        } else {
            throw new EntityNotFoundException("Cart with id " + userId + " not found");
        }

    }
}
