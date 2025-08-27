package com.sc.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sc.demo.model.TokenBlacklist;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
	
	 boolean existsByToken(String token);

}
