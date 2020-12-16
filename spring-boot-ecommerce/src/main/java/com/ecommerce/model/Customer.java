package com.ecommerce.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue
	private int customerId;

	@NotNull
	private String username;
	
	@NotNull
	private String customerName;
	
	@NotNull
	@Email
	private String customerEmail;
	
	@NotNull
	private String customerAddress;
	
	@NotNull
	private String password;
	
	@NotNull
	private String confirmPassword;
	
	private boolean enabled;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cartId")
	@JsonIgnore
	private Cart cart;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", username=" + username + ", customerName=" + customerName
				+ ", customerEmail=" + customerEmail + ", customerAddress=" + customerAddress + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", enabled=" + enabled + "]";
	}

}
