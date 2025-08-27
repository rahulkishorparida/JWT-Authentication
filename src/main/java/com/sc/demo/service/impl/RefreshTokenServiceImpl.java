package com.sc.demo.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sc.demo.model.RefreshToken;
import com.sc.demo.repo.RefreshTokenRepo;
import com.sc.demo.service.RefreshTokenService;

import jakarta.transaction.Transactional;
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    @Override
    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUsername(username);
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7)); // valid for 7 days
        return refreshTokenRepo.save(refreshToken);
    }

    @Override
    public boolean validateRefreshToken(String token) {
        return refreshTokenRepo.findByToken(token)
                .filter(rt -> !rt.isExpired())
                .isPresent();
    }

    @Override
    @Transactional
    public void deleteRefreshToken(String token) {
        refreshTokenRepo.deleteByToken(token);
    }
    

}
