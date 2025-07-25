package com.example.store.controller;

import com.example.store.entity.Product;
import com.example.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * This function returns a list of all products using a GET request.
     * 
     * @return A ResponseEntity object containing a list of Product objects is being returned. The list
     * of Product objects is retrieved by calling the getAllProducts() method of the productService.
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
}
