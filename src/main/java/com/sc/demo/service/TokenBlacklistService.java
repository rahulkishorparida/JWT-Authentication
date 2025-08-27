package com.sc.demo.service;

public interface TokenBlacklistService {
	
	 public void blacklistToken(String token);
	  
	 public boolean isBlacklisted(String token);

}
