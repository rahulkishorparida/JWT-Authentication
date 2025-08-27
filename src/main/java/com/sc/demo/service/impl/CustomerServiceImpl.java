package com.sc.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sc.demo.dto.CustomerDTO;
import com.sc.demo.dto.ImageService;
import com.sc.demo.model.Customer;
import com.sc.demo.repo.CustomerRepo;
import com.sc.demo.repo.MapperClass;
import com.sc.demo.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	 @Autowired
	 private CustomerRepo customerRepo;
	   private final MapperClass customerMapper;

	    public CustomerServiceImpl(MapperClass customerMapper) {
	        this.customerMapper = customerMapper;
	    }
	    @Autowired
	    private ImageService imageService;
 
	@Override
	public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
		 Customer entity = customerMapper.toEntity(customerDTO);

		 if(customerDTO.getBase64() != null && !customerDTO.getBase64().isEmpty() ) {
			 String saveBase64Image = imageService.saveBase64Image(customerDTO.getBase64());
			 entity.setImage(saveBase64Image);
		 
		 }

		 Customer save = customerRepo.save(entity);
		 return customerMapper.toDto(save);
		
	}
	
	@Override
	public CustomerDTO updateCustomer(int id, CustomerDTO customerDTO) {
	    Customer existing = customerRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));

	    // Handle image
	    if (customerDTO.getBase64() != null && !customerDTO.getBase64().isEmpty()) {
	        // delete old file
	        imageService.deleteFile(existing.getImage());

	        // save new file
	        String saveBase64Image = imageService.saveBase64Image(customerDTO.getBase64());
	        existing.setImage(saveBase64Image);
	    }

	    // Map non-id fields from DTO → existing
	    customerMapper.updateCustomerFromDto(customerDTO, existing);

	    // make sure id is preserved
	    existing.setId(id);

	    Customer updated = customerRepo.save(existing);
	    return customerMapper.toDto(updated);
	}


	@Override
	public Optional<CustomerDTO> getCustomerById(int id) {
		return customerRepo.findById(id).map(customerMapper::toDto);
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepo.findAll().stream().map(customerMapper::toDto).toList();
		
	}



	@Override
	public void deleteCustomer(int id) {
	Customer existing = customerRepo.findById(id).orElseThrow(() -> new RuntimeException("customer not find to delete"+ id));
	customerRepo.delete(existing);
	
		
	}

	@Override
	public Page<Customer> findByNameContainingIgnoreCase(String keyword, Pageable pageable) {
		if (keyword == null || keyword.isEmpty()){
			return customerRepo.findAll(pageable);
		}
		return customerRepo.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword, pageable);
	}
//	@Override
//	public Page<Customer> findByNameContainingIgnoreCase(Optional<String> keyword, Pageable pageable) {
//	    return keyword.filter(k -> !k.isBlank())
//	                  .map(k -> customerRepo.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(k, k, pageable))
//	                  .orElseGet(() -> customerRepo.findAll(pageable));
//	}


}
