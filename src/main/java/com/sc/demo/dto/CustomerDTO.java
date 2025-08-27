package com.sc.demo.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class CustomerDTO {
	
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    
	    @Column(nullable = false, length = 100)
	    private String name;
	    
		@Column(nullable = false, unique = true, length = 150)
	    private String email;

	    @Column(nullable = false, unique = true, length = 15)
	    private String phoneNumber;

	    private String city;

	    @Column(length = 10)
	    private String postalCode;

	    private LocalDate registrationDate  = LocalDate.now();

	    private Boolean active= true;
	    
	    private String base64;

	    public CustomerDTO(int id, String name, String email, String phoneNumber, String city, String postalCode,
				LocalDate registrationDate, Boolean active, String base64) {
			
			this.id = id;
			this.name = name;
			this.email = email;
			this.phoneNumber = phoneNumber;
			this.city = city;
			this.postalCode = postalCode;
			this.registrationDate = registrationDate;
			this.active = active;
			this.base64 = base64;
		}

	
	    

		public String getBase64() {
			return base64;
		}

		public void setBase64(String base64) {
			this.base64 = base64;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getPostalCode() {
			return postalCode;
		}

		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}

		public LocalDate getRegistrationDate() {
			return registrationDate;
		}

		public void setRegistrationDate(LocalDate registrationDate) {
			this.registrationDate = registrationDate;
		}

		public Boolean getActive() {
			return active;
		}

		public void setActive(Boolean active) {
			this.active = active;
		}

}
