package com.sc.demo.service;

import com.sc.demo.model.RefreshToken;

public interface RefreshTokenService {
	
	    RefreshToken createRefreshToken(String username);
	    boolean validateRefreshToken(String token);
	    void deleteRefreshToken(String token);

}
