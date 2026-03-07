package com.greams.SpringBootJWT.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Date;

@Service   // Marks this class as a Spring service (used for business logic)
public class JWTService {

    private final SecretKey secretKey;   // This key will be used to sign and verify JWT tokens

    public JWTService() {
        try {
            // Create a random secret key using HMAC-SHA256 algorithm
            // This key will be used to sign every JWT
            SecretKey k = KeyGenerator.getInstance("HmacSHA256").generateKey();

            // Convert the generated key into a format JJWT library can use safely
            secretKey = Keys.hmacShaKeyFor(k.getEncoded());
        } catch (Exception e) {
            // If key generation fails, throw a runtime exception
            throw new RuntimeException(e);
        }
    }

    // Method to generate a JWT token
    public String getJWTToken() {

        return Jwts.builder()
                // The "subject" is the identity stored inside the token (like a username)
                .subject("greams")

                // When the token was created
                .issuedAt(new Date(System.currentTimeMillis()))

                // Token expiration time (15 minutes)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))

                // Sign the token using the secret key so it cannot be changed
                .signWith(secretKey)

                // Convert everything into a compact JWT string
                .compact();
    }

    // Method to extract the username ("subject") from a JWT token
    public String getUserName(String token) {

        return Jwts.parser()
                // Use the same secret key that was used to sign the token
                .verifyWith(secretKey)
                .build()

                // Parse the JWT — if the signature is wrong, this will throw an error
                .parseSignedClaims(token)

                // Get the body part (payload) of the token
                .getPayload()

                // Extract the subject (username)
                .getSubject();
    }
}
