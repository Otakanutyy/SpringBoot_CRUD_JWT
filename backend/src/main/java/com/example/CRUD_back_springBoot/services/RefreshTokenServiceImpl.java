package com.example.CRUD_back_springBoot.services;

import com.example.CRUD_back_springBoot.models.RefreshToken;
import com.example.CRUD_back_springBoot.models.User;
import com.example.CRUD_back_springBoot.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken createRefreshToken(User user, String refreshToken, LocalDateTime expiryDate) {
        RefreshToken re = new RefreshToken();
        re.setUser(user);
        re.setToken(refreshToken);
        re.setExpiry_date(expiryDate);
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
