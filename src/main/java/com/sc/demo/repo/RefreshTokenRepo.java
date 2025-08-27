package com.sc.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sc.demo.model.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {
	
	Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);

}

