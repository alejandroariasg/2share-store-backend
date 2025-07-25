package com.example.store.security;

import com.example.store.entity.User;
import com.example.store.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public CustomAuthenticationSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * The `onAuthenticationSuccess` method handles successful user authentication by returning user
     * information in JSON format or an unauthorized error message if the user is not found.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);

        if (user != null) {
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("status", 200);
            responseData.put("message", "Login successful");
            responseData.put("user", Map.of(
                    "id", user.getId(),
                    "username", user.getUsername(),
                    "fullName", user.getFullName()
            ));

            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), responseData);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            Map<String, Object> errorData = new HashMap<>();
            errorData.put("status", 401);
            errorData.put("message", "Unauthorized: user not found");
            new ObjectMapper().writeValue(response.getOutputStream(), errorData);
        }
    }
}
