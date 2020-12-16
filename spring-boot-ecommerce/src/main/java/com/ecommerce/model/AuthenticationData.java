package com.ecommerce.model;

public class AuthenticationData {
	private String status;
	
	public AuthenticationData(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AuthenticationData [status=" + status + "]";
	}
	
}
