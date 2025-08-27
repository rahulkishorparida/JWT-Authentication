package com.sc.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sc.demo.model.User;

public interface UserRepo extends JpaRepository<User,Integer> {
	
	
	Optional<User> findByUsername(String username);

}
//User findByUsername(String username);
//Optional<User> findByUsername(String username);
