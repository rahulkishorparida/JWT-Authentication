package com.sc.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sc.demo.model.User;
import com.sc.demo.repo.UserRepo;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		   User user = userRepo.findByUsername(username)
			        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
			    return new CustomeUser(user);
// Spring Security works internally with its own UserDetails type — not directly with your User entity.
}
}
