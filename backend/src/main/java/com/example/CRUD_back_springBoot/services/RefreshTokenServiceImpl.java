package com.example.CRUD_back_springBoot.services;

import com.example.CRUD_back_springBoot.models.RefreshToken;
import com.example.CRUD_back_springBoot.models.User;
import com.example.CRUD_back_springBoot.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenDurationSeconds;

    @Override
    public RefreshToken createRefreshToken(User user) {
        RefreshToken re = new RefreshToken();
        re.setUser(user);
        re.setToken(UUID.randomUUID().toString());
        re.setExpiry_date(LocalDateTime.now().plusSeconds(refreshTokenDurationSeconds));
        re.setCreated_at(LocalDateTime.now());
        return refreshTokenRepository.save(re);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

    @Override
    public void deleteByToken(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(refreshTokenRepository::delete);
    }
}
