package com.app.auth.application;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.auth.domain.IJwtTokenRepository;
import com.app.auth.domain.JwtToken;
import com.app.auth.infrastructure.JwtUtils;
import com.app.auth.infrastructure.dto.AuthResponse;
import com.app.auth.infrastructure.dto.ProfileResponse;
import com.app.auth.infrastructure.dto.RegisterRequest;
import com.app.users.domain.IUserRepository;
import com.app.users.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final IUserRepository userRepository;
    private final IJwtTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
            String accessToken = jwtUtils.generateToken(authentication);
            String refreshToken = createRefreshToken(email);
            return new AuthResponse(accessToken, refreshToken);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid email or password");
        }
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        // Authenticate the user and generate tokens
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(), 
                    request.getPassword()
                )
            );
            
            String accessToken = jwtUtils.generateToken(authentication);
            String refreshToken = createRefreshToken(request.getEmail());
            
            return new AuthResponse(accessToken, refreshToken);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Error during registration authentication");
        }
    }

    @Transactional
    public AuthResponse refreshToken(String refreshToken) {
        return tokenRepository.findByToken(refreshToken)
                .map(token -> {
                    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
                        tokenRepository.delete(token);
                        throw new RuntimeException("Refresh token expired");
                    }

                    String newAccessToken = jwtUtils.generateToken(token.getUserEmail());
                    return new AuthResponse(newAccessToken, refreshToken);
                })
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));
    }

    @Transactional
    public ProfileResponse getProfile(String email) {
        return userRepository.findByEmail(email)
            .map(user -> new ProfileResponse(user.getId(), user.getName(), user.getEmail()))
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    private String createRefreshToken(String userEmail) {
        tokenRepository.deleteByUserEmail(userEmail);
        
        JwtToken refreshToken = new JwtToken();
        refreshToken.setUserEmail(userEmail);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtUtils.getRefreshTokenDurationMs()));
        
        tokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }
} 