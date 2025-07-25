package com.example.store.service;

import com.example.store.entity.Product;
import com.example.store.entity.ShoppingList;
import com.example.store.entity.User;
import com.example.store.repository.ProductRepository;
import com.example.store.repository.ShoppingListRepository;
import com.example.store.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingListService {

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<ShoppingList> getUserShoppingList(Long userId) {
        return shoppingListRepository.findByUserId(userId);
    }

    /**
     * The function `addProductToList` adds a product to a user's shopping list after checking if it
     * already exists.
     */
    public ShoppingList addProductToList(Long userId, Long productId) {
        // Verifica si ya existe
        boolean exists = shoppingListRepository.findByUserIdAndProductId(userId, productId).isPresent();
        if (exists) {
            throw new RuntimeException("Product already in shopping list");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ShoppingList item = new ShoppingList();
        item.setUser(user);
        item.setProduct(product);
        item.setTagged(false);
        item.setAddedAt(LocalDateTime.now());

        return shoppingListRepository.save(item);
    }

    @Transactional
    public void removeProductFromList(Long userId, Long productId) {
        shoppingListRepository.deleteByUserIdAndProductId(userId, productId);
    }

    public ShoppingList toggleTag(Long userId, Long productId, boolean tagged) {
        ShoppingList item = shoppingListRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Item not found in list"));

        item.setTagged(tagged);
        return shoppingListRepository.save(item);
    }


}
