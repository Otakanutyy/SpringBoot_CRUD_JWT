package com.example.CRUD_back_springBoot.repositories;

import com.example.CRUD_back_springBoot.models.RefreshToken;
import com.example.CRUD_back_springBoot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}
