package com.sc.demo.service;

import java.util.Map;

import com.sc.demo.dto.UserResponse;
import com.sc.demo.model.User;

public interface UserService {
//	String login(UserResponse response);
	 Map<String, String> login(UserResponse response);
	
	  public User getUserByUsername(String username);

}
