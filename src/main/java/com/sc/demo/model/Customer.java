package com.sc.demo.model;

import java.time.LocalDate;

import org.hibernate.annotations.GeneratorType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Customer {
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

	    private LocalDate registrationDate = LocalDate.now();

	    private Boolean active = true;
	    @Lob
	    private String image;
	    
	    
		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
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
