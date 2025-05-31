package com.example.CRUD_back_springBoot.services;

import com.example.CRUD_back_springBoot.DTOs.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> register(RegisterRequest registerRequest);
    ResponseEntity<JwtRefreshResponse> login(LoginRequest loginRequest);
    ResponseEntity<JwtRefreshResponse> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
