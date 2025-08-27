package com.sc.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sc.demo.dto.ApiResponse;
import com.sc.demo.dto.UserResponse;
import com.sc.demo.model.RefreshToken;
import com.sc.demo.repo.RefreshTokenRepo;
import com.sc.demo.service.JwtService;
import com.sc.demo.service.RefreshTokenService;
import com.sc.demo.service.TokenBlacklistService;
import com.sc.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/go")
public class Controller {
	
	@Autowired
	private UserService userService;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;
    @Autowired
    private RefreshTokenService refreshTokenService; 
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenRepo refreshTokenRepo;
    
 
	
	@GetMapping("/home")
	public ResponseEntity<?> home(){
		return ResponseEntity.ok("welcome!!!!!!!!!!!!!!!!!!!");
		
	}
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody UserResponse response){
//	    Map<String, String> tokens = userService.login(response);
//	    if(tokens == null || tokens.isEmpty()) {
//	        return new ResponseEntity<>("invalid", HttpStatus.BAD_REQUEST);
//	    }
//	    return new ResponseEntity<>(tokens, HttpStatus.OK);
//	}
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody UserResponse response) {
	    Map<String, String> tokens = userService.login(response);

	    if (tokens == null || tokens.isEmpty()) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(new ApiResponse<>("Invalid credentials", false));
	    }

	    return ResponseEntity
	            .status(HttpStatus.OK)
	            .body(new ApiResponse<>("Login successful", true, tokens));
	}


    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
//            System.out.println("Logging out token: " + token);
            tokenBlacklistService.blacklistToken(token);
            return ResponseEntity.ok("Token blacklisted and user logged out.");
        }

        return ResponseEntity.badRequest().body("No token found.");
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshTokenService.validateRefreshToken(refreshToken)) {
            // Get username from refresh token stored in DB
            String username = refreshTokenRepo.findByToken(refreshToken).get().getUsername();

            // Invalidate (delete) old refresh token to prevent reuse
            refreshTokenService.deleteRefreshToken(refreshToken);

            // Create a new refresh token for the user
            RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(username);

            // Generate a new access JWT token
            String newAccessToken = jwtService.generatetoken(userService.getUserByUsername(username));

            // Return both tokens to client
            return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken.getToken()
            ));
        }

        // If token invalid or expired, respond with 401
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
    }



	

}
