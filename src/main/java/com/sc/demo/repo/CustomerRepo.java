package com.sc.demo.repo;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sc.demo.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
	
//	Page<Customer>findByNameContainingIgnoringCaseorEmailontainingIgnoringCase(String email,String name,Pageable pageable);
	  Page<Customer> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Pageable pageable);
	
}
