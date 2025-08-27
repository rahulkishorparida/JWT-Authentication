package com.sc.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;



import com.sc.demo.dto.CustomerDTO;
import com.sc.demo.model.Customer;

public interface CustomerService {
	
	CustomerDTO saveCustomer(CustomerDTO customerDTO);
	Optional<CustomerDTO> getCustomerById(int id);
	List<CustomerDTO> getAllCustomers();
	CustomerDTO updateCustomer(int id, CustomerDTO customerDTO);
	void deleteCustomer(int id);
	Page<Customer> findByNameContainingIgnoreCase(String keyword, Pageable pageable);


}

