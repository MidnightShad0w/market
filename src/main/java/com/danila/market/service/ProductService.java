package com.danila.market.service;

import com.danila.market.entity.Product;
import com.danila.market.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    public ResponseEntity<?> saveNewProduct(Product product) {
        productRepository.save(product);
        return ResponseEntity.ok(product);
    }
    public ResponseEntity<?> deleteProductById(int id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok(id);
    }
}
