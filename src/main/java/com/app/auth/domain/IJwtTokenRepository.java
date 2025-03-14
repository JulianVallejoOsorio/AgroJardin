package com.app.auth.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IJwtTokenRepository extends JpaRepository<JwtToken, Long> {
    Optional<JwtToken> findByToken(String token);
    void deleteByUserEmail(String userEmail);
} 