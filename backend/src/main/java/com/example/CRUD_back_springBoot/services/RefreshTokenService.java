package com.example.CRUD_back_springBoot.services;

import com.example.CRUD_back_springBoot.models.RefreshToken;
import com.example.CRUD_back_springBoot.models.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user, String refreshToken, LocalDateTime expiryDate);
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
    void deleteByToken(String token);
}
