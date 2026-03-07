package com.greams.SpringBootJWT.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration                 // Marks this class as a configuration class
@EnableWebSecurity             // Enables Spring Security for the project
public class SecurityConfig {

    @Bean                     // Makes this method return a Spring-managed bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // We configure the HttpSecurity object and return the security filter chain
        return httpSecurity
                // Disable CSRF protection (mainly needed for stateful apps; not needed for JWT)
                .csrf(c -> c.disable())

                // Tell Spring Security that we want "stateless sessions"
                // This is required when using JWT tokens
                .sessionManagement(s ->
                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Configure which URLs should be allowed without authentication
                .authorizeHttpRequests(r -> r
                        // Allow the /login endpoint without requiring a token
                        .requestMatchers("/login").permitAll()
                        // Any other request must be authenticated
                        .anyRequest().authenticated()
                )

                // Enable basic authentication (mainly for testing tools like Postman)
                .httpBasic(Customizer.withDefaults())

                // Build and return the configured SecurityFilterChain
                .build();
    }
}
