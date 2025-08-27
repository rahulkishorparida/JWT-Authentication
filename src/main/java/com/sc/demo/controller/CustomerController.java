package com.sc.demo.controller;


import com.sc.demo.dto.ApiResponse;
import com.sc.demo.dto.CustomerDTO;
import com.sc.demo.repo.MapperClass;
import com.sc.demo.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	@Autowired
	private MapperClass customerMapper;

    @Autowired
    private CustomerService customerService;
    
    
	@GetMapping("/home")
	public ResponseEntity<?> home(){
		return ResponseEntity.ok("welcome DONE!!!!!!!!!!!!!!!!!!!");
		
	}

    // Create
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<CustomerDTO>> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO saved = customerService.saveCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<CustomerDTO>("Customer created successfully", true, saved));
    }
    
    // Update
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDTO>> updateCustomer(@PathVariable int id,
                                                                   @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updated = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok(new ApiResponse<>("Customer updated successfully", true, updated));
    }

 // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDTO>> getCustomer(@PathVariable int id) {
        return customerService.getCustomerById(id)
                .map(customer -> ResponseEntity.ok(new ApiResponse<>("Customer found", true, customer)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Customer not found with id " + id, false)));
    }

    // Get all
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CustomerDTO>>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(new ApiResponse<>("Customers retrieved successfully", true, customers));
    }



    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(new ApiResponse<>("Customer deleted successfully", true));
    }
    
    @GetMapping("/customers")
    public Page<CustomerDTO> getCustomers(@RequestParam(required = false) String keyword,Pageable pageable) {
    	
        return customerService.findByNameContainingIgnoreCase(keyword, pageable).map(customerMapper::toDto);
    }


}
