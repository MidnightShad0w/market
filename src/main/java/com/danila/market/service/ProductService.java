package com.danila.market.service;

import com.danila.market.entity.Product;
import com.danila.market.exceptions.AppError;
import com.danila.market.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        var products = productRepository.findAll();
        return products;
    }

    public Product saveNewProduct(Product product) {
        var savedProduct = productRepository.save(product);
        return savedProduct;
    }
    public void deleteProductById(int id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Product with id " + id + " not found");
        }
    }
}
