package com.example.store.controller;

import com.example.store.dto.ShoppingListResponseDTO;
import com.example.store.entity.ShoppingList;
import com.example.store.entity.User;
import com.example.store.repository.UserRepository;
import com.example.store.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shopping-list")
@PreAuthorize("isAuthenticated()")
public class ShoppingListController {

    @Autowired
    private ShoppingListService shoppingListService;

    @Autowired
    private UserRepository userRepository;

    @// This `GetMapping` method in the `ShoppingListController` class is responsible for handling GET
    // requests to retrieve the shopping list items for a specific user. Here's a breakdown of what
    // the method does:
    GetMapping
    public ResponseEntity<List<ShoppingListResponseDTO>> getShoppingList(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        List<ShoppingList> items = shoppingListService.getUserShoppingList(userId);

        List<ShoppingListResponseDTO> response = items.stream()
                .map(item -> new ShoppingListResponseDTO(
                        item.getProduct(),
                        item.isTagged(),
                        item.getAddedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * This function adds a product to a user's shopping list and returns the updated shopping list.
     * 
     * @param productId The `productId` parameter in the `addProduct` method represents the unique
     * identifier of the product that is being added to the shopping list. This identifier is used to
     * identify the specific product that the user wants to add to their list.
     * @return The method is returning a ResponseEntity with the added ShoppingList object in the
     * response body.
     */
    @PostMapping("/{productId}")
    public ResponseEntity<?> addProduct(@PathVariable Long productId, Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        ShoppingList added = shoppingListService.addProductToList(userId, productId);
        return ResponseEntity.ok(added);
    }

    /**
     * This Java function removes a product from a shopping list based on the provided product ID and
     * returns a success message along with the removed product ID.
     * 
     * @param productId The `productId` parameter in the `removeProduct` method represents the unique
     * identifier of the product that needs to be removed from the shopping list. This identifier is
     * used to locate and remove the specific product from the user's shopping list.
     * @return The `removeProduct` method is returning a `ResponseEntity` with a HashMap containing a
     * message confirming the successful removal of a product from the shopping list, the productId
     * that was removed, and a status code of 200.
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> removeProduct(@PathVariable Long productId, Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        shoppingListService.removeProductFromList(userId, productId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product removed from shopping list successfully.");
        response.put("productId", productId);
        response.put("status", 200);

        return ResponseEntity.ok(response);
    }

    /**
     * This function toggles the tag status of a product in a shopping list and returns a message along
     * with the updated product information.
     * 
     * @param productId The `productId` parameter is a Long value representing the unique identifier of
     * a product in the system.
     * @param tagged The `tagged` parameter in the `toggleTag` method is a boolean parameter that
     * indicates whether a product should be tagged or untagged. When `tagged` is `true`, it means the
     * product should be tagged as selected, and when `tagged` is `false`, it
     * @return The method `toggleTag` is returning a `ResponseEntity` object with a map containing the
     * following key-value pairs:
     * - "message": A message indicating whether the product was tagged as selected or untagged
     * - "productId": The ID of the product being tagged or untagged
     * - "tagged": A boolean value indicating whether the product is currently tagged or not
     */
    @PatchMapping("/{productId}/tag")
    public ResponseEntity<?> toggleTag(@PathVariable Long productId,
                                       @RequestParam boolean tagged,
                                       Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        ShoppingList updated = shoppingListService.toggleTag(userId, productId, tagged);
        return ResponseEntity.ok(Map.of(
                "message", tagged ? "Product tagged as selected" : "Product untagged",
                "productId", productId,
                "tagged", updated.isTagged()
        ));
    }

    /**
     * This function retrieves the user ID from the authentication object by querying the user
     * repository with the username.
     * 
     * @param auth The `auth` parameter in the `getUserIdFromAuth` method is of type `Authentication`.
     * It is used to retrieve the username of the authenticated user.
     * @return The method `getUserIdFromAuth` is returning the `id` of the user retrieved from the
     * database based on the username obtained from the `Authentication` object.
     */
    private Long getUserIdFromAuth(Authentication auth) {
        String username = auth.getName(); // devuelve el username (generalmente el email o nombre de usuario)
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getId();
    }
}
