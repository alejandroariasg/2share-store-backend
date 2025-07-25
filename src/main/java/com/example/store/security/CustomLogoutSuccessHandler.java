package com.example.store.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    /**
     * The `onLogoutSuccess` function checks if the user is authenticated and returns a JSON response
     * indicating the logout status.
    */
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication)
            throws IOException {

        Map<String, Object> responseBody = new HashMap<>();

        if (authentication != null && authentication.isAuthenticated()) {
            responseBody.put("status", 200);
            responseBody.put("message", "Logout successful");
        } else {
            responseBody.put("status", 401);
            responseBody.put("message", "You are not authenticated");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
    }
}