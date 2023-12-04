package com.danila.market.controller;

import com.danila.market.entity.Product;
import com.danila.market.exceptions.AppError;
import com.danila.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        if (productList.isEmpty()) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "Products not found"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(productList);
    }
    @PostMapping("/save")
    public ResponseEntity<?> saveNewProduct(@RequestBody Product product) {
        var savedProduct = productService.saveNewProduct(product);
        if (savedProduct == null) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Error saving product"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(savedProduct);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable int id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Product with id " + id + " deleted");
    }
}
