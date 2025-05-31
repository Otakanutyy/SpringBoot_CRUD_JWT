package com.example.CRUD_back_springBoot.services;

import com.example.CRUD_back_springBoot.DTOs.*;
import com.example.CRUD_back_springBoot.models.RefreshToken;
import com.example.CRUD_back_springBoot.models.Role;
import com.example.CRUD_back_springBoot.models.User;
import com.example.CRUD_back_springBoot.repositories.UserRepository;
import com.example.CRUD_back_springBoot.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Override
    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        if (registerRequest.getRole() != null) {
            try {
                user.setRole(Role.valueOf(registerRequest.getRole().toUpperCase()));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role " + registerRequest.getRole());
            }
        } else {
            user.setRole(Role.USER);
        }
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @Override
    public ResponseEntity<JwtRefreshResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String reqToken = refreshTokenRequest.getRefreshToken();

        Optional<RefreshToken> foundToken = refreshTokenService.findByToken(reqToken);
        if (foundToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new JwtRefreshResponse(null, null));
        }

        RefreshToken token = foundToken.get();
        if (token.getExpiry_date().isBefore(LocalDateTime.now())) {
            refreshTokenService.deleteByToken(reqToken);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new JwtRefreshResponse(null, null));
        }

        String newJwt = jwtUtils.generateToken(
                token.getUser().getEmail(),
                token.getUser().getRole().name()
        );

        return ResponseEntity.ok(new JwtRefreshResponse(newJwt, token.getToken()));
    }

    @Override
    public ResponseEntity<JwtRefreshResponse> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String jwt = jwtUtils.generateToken(user.getEmail(), user.getRole().name());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return ResponseEntity.ok(new JwtRefreshResponse(jwt, refreshToken.getToken()));
    }
}