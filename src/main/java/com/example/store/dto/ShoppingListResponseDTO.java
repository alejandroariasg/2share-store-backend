package com.example.store.dto;

import com.example.store.entity.Product;

import java.time.LocalDateTime;

public class ShoppingListResponseDTO {
    private Long productId;
    private String productName;
    private boolean tagged;
    private LocalDateTime addedAt;

    public ShoppingListResponseDTO(Product product, boolean tagged, LocalDateTime addedAt) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.tagged = tagged;
        this.addedAt = addedAt;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public boolean isTagged() {
        return tagged;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

}
