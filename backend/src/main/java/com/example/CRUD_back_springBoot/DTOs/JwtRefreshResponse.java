package com.example.CRUD_back_springBoot.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRefreshResponse {
    private String accessToken;
    private String refreshToken;
}
