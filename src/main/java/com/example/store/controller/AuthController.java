package com.example.store.controller;

import com.example.store.entity.User;
import com.example.store.repository.UserRepository;
import com.example.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // This `register` method in the `AuthController` class is handling the registration of a new
    // user. Here's a breakdown of what it does:
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Username already taken"));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "User registered successfully"));
    }

    /**
     * The function logoutSuccess() returns a ResponseEntity with a message indicating that the logout
     * was successful.
     * 
     * @return The method `logoutSuccess()` is returning a ResponseEntity with a message "Logout
     * successful" and an HTTP status code of 200 (OK).
     */
    @GetMapping("/logout-success")
    public ResponseEntity<?> logoutSuccess() {
        return ResponseEntity.ok("Logout successful");
    }

    /**
     * This function returns the current user's username and authentication status if the user is
     * authenticated, otherwise it returns an unauthorized message.
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "No active session"));
        }

        return ResponseEntity.ok(Map.of(
                "username", authentication.getName(),
                "authenticated", true
        ));
    }
}
