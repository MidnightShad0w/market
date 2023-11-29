package com.danila.market.controller;

import com.danila.market.entity.Product;
import com.danila.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProducts() {
        return productService.getAllProducts();
    }
    @PostMapping("/save")
    public ResponseEntity<?> saveNewProduct(@RequestBody Product product) {
        return productService.saveNewProduct(product);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable int id) {
        return productService.deleteProductById(id);
    }
}
