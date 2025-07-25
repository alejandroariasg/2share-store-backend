package com.example.store.config;

import com.example.store.entity.Product;
import com.example.store.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    /**
     * This function in Java Spring Boot loads sample product data into a repository if it is empty.
     */
    @Bean
    public CommandLineRunner loadData(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                List<Product> products = Arrays.asList(
                        new Product("Laptop", 1, LocalDateTime.now()),
                        new Product("Smartphone", 1, LocalDateTime.now()),
                        new Product("Headphones", 1, LocalDateTime.now()),
                        new Product("Keyboard", 2, LocalDateTime.now()),
                        new Product("Mouse", 1, LocalDateTime.now()),
                        new Product("Monitor", 2, LocalDateTime.now()),
                        new Product("Printer", 1, LocalDateTime.now()),
                        new Product("Camera", 1, LocalDateTime.now()),
                        new Product("Speaker", 1, LocalDateTime.now()),
                        new Product("Tablet", 2, LocalDateTime.now()),
                        new Product("Webcam", 1, LocalDateTime.now()),
                        new Product("Microphone", 1, LocalDateTime.now()),
                        new Product("Hard Drive", 1, LocalDateTime.now()),
                        new Product("SSD", 2, LocalDateTime.now()),
                        new Product("RAM", 1, LocalDateTime.now()),
                        new Product("GPU", 1, LocalDateTime.now()),
                        new Product("CPU", 2, LocalDateTime.now()),
                        new Product("Power Supply", 1, LocalDateTime.now()),
                        new Product("Motherboard", 2, LocalDateTime.now()),
                        new Product("Case", 1, LocalDateTime.now())
                );

                productRepository.saveAll(products);
            }
        };
    }
}
