package com.sc.demo.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import com.sc.demo.dto.UserResponse;
import com.sc.demo.model.User;
import com.sc.demo.repo.UserRepo;
import com.sc.demo.service.JwtService;
import com.sc.demo.service.RefreshTokenService;
import com.sc.demo.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
	private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepo userRepository;
    
    @Autowired
    private RefreshTokenService refreshTokenService;

	
    @Override
    public Map<String, String> login(UserResponse response) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(response.getUsername(), response.getPassword())
        );

        if (authentication.isAuthenticated()) {
            User user = userRepository.findByUsername(response.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

            String accessToken = jwtService.generatetoken(user);
            
            // Create & save refresh token in DB
            String refreshToken = refreshTokenService.createRefreshToken(user.getUsername()).getToken();

            // Return both tokens as a Map (you can customize response DTO too)
            return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
            );
        }
        
        throw new RuntimeException("Authentication failed for user: " + response.getUsername());
    }


    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    
//    
//    public User getUserByUsername(String username) {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found: " + username);
//        }
//        return user;
//    }

    
    

}
