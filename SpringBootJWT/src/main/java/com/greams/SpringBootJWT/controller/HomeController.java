package com.greams.SpringBootJWT.controller;

import com.greams.SpringBootJWT.service.JWTService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController   // This class handles REST API requests (JSON/text responses)
public class HomeController {

    private final JWTService jwtService;  // Service used to generate and read JWT tokens

    // Constructor injection for the JWTService
    public HomeController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping    // Handles GET request for the root URL "/"
    public String home() {
        return "Welcome to the Home Page!";  // Simple message returned to the user
    }

    @PostMapping("/login")   // Endpoint for generating a JWT token
    public String login() {
        // Call the service to create and return a new JWT token
        return jwtService.getJWTToken();
    }

    @GetMapping("/username")  // Endpoint to extract a username from a token
    public String getUsername(@RequestParam String token) {
        // @RequestParam reads the token from the URL query parameter:
        // Example: /username?token=YOUR_TOKEN_HERE

        // Return the username stored inside the token
        return jwtService.getUserName(token);
    }

}
