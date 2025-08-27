package com.sc.demo.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sc.demo.model.User;

public class CustomeUser implements UserDetails {
	
	private User user;
	

	public CustomeUser(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		return Collections.singleton(authority);
		
//		   return Arrays.stream(user.getRoles().split(","))
//	                 .map(SimpleGrantedAuthority::new)
//	                 .collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword() ;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

}
