package com.sc.demo.service;

import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import com.sc.demo.model.User;

import io.jsonwebtoken.Claims;

public interface JwtService {
	
	public String generatetoken(User user);
	
    public Claims extractAllClaims(String token);
	
	public <T> T extractClaim(String token, Function<Claims, T> claimResolve);

	public String extractUserName(String token);
	
	public Date extractExpiration(String token);

	public Boolean validateToken(String token, UserDetails userDetails);
}
