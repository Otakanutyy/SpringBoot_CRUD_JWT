package com.example.CRUD_back_springBoot.controllers;

import com.example.CRUD_back_springBoot.DTOs.JwtRefreshResponse;
import com.example.CRUD_back_springBoot.DTOs.LoginRequest;
import com.example.CRUD_back_springBoot.DTOs.RefreshTokenRequest;
import com.example.CRUD_back_springBoot.DTOs.RegisterRequest;

import com.example.CRUD_back_springBoot.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtRefreshResponse> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtRefreshResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }
}
