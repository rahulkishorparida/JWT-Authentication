package com.sc.demo.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sc.demo.model.TokenBlacklist;
import com.sc.demo.repo.TokenBlacklistRepository;
import com.sc.demo.service.TokenBlacklistService;



@Service
public class TokenBlacklistServiceImpl  implements TokenBlacklistService{

    @Autowired
    private TokenBlacklistRepository repository;

    public boolean isBlacklisted(String token) {
        return repository.existsByToken(token);
    }
    public void blacklistToken(String token) {
        TokenBlacklist blacklist = new TokenBlacklist();
        blacklist.setToken(token);
        blacklist.setBlacklistedAt(LocalDateTime.now());
        repository.save(blacklist);
//        System.out.println("Token save to DB: " + token);
    }


}
