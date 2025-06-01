package com.example.CRUD_back_springBoot.services;

import com.example.CRUD_back_springBoot.models.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    User saveUser(User user);
    User getCurrentUser(UserDetails userDetails);
}
