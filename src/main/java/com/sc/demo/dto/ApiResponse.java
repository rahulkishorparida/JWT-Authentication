package com.sc.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ApiResponse<T> {
	
	   private String message;
	   private boolean success;
	   @JsonInclude(JsonInclude.Include.NON_NULL)
	   private T data;

	    public ApiResponse(String message, boolean success) {
	        this.message = message;
	        this.success = success;
	    }

	    public ApiResponse(String message, boolean success, T data) {
	        this.message = message;
	        this.success = success;
	        this.data = data;
	    }

	
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	

}
