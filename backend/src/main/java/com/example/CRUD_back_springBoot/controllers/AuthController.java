package com.example.CRUD_back_springBoot.controllers;

import com.example.CRUD_back_springBoot.DTOs.JwtRefreshResponse;
import com.example.CRUD_back_springBoot.DTOs.LoginRequest;
import com.example.CRUD_back_springBoot.DTOs.RefreshTokenRequest;
import com.example.CRUD_back_springBoot.DTOs.RegisterRequest;

import com.example.CRUD_back_springBoot.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration, login, and JWT refresh")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Operation(
            summary     = "Register new user",
            description = "Returns 201 on success or 409 if email exists",
            responses   = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "400", description = "Invalid role"),
                    @ApiResponse(responseCode = "409", description = "Email exists")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @Operation(
            summary     = "Login and get JWT + refresh token",
            responses   = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Bad credentials")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<JwtRefreshResponse> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @Operation(
            summary     = "Refresh JWT using refresh token",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses   = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Invalid / expired token")
            }
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<JwtRefreshResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }
}
