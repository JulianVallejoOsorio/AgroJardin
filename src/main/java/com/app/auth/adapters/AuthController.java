package com.app.auth.adapters;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.app.auth.infrastructure.dto.*;
import com.app.auth.application.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(AuthController.BASE_URL)
@RequiredArgsConstructor
@Tag(name = "Authentication", 
     description = "API for authentication and user management")
public class AuthController {

    public static final String BASE_URL = "/api/auth";

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(
        summary = "Login",
        description = "Authenticates a user and returns a JWT token"
    )
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request.getEmail(), request.getPassword()));
    }

    @PostMapping("/register")
    @Operation(
        summary = "Register user",
        description = "Registers a new user and returns a JWT token"
    )
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/refresh")
    @Operation(
        summary = "Refresh token",
        description = "Generate a new JWT token using a refresh token"
    )
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));
    }

    @GetMapping("/profile")
    @Operation(
        summary = "Get profile",
        description = "Gets the profile of the authenticated user"
    )
    public ResponseEntity<ProfileResponse> getUserProfile(Authentication authentication) {
        return ResponseEntity.ok(authService.getProfile(authentication.getName()));
    }
}