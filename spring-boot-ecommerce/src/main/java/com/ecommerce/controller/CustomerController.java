package com.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dao.AuthoritiesRepository;
import com.ecommerce.dao.CartRepository;
import com.ecommerce.dao.CustomerRepository;
import com.ecommerce.dao.UsersRepository;
import com.ecommerce.model.Authorities;
import com.ecommerce.model.Cart;
import com.ecommerce.model.Customer;
import com.ecommerce.model.Users;

@RestController
public class CustomerController {
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	CartRepository cartRepo;
	
	@Autowired
	AuthoritiesRepository authoritiesRepo;
	
	@Autowired
	UsersRepository usersRepo;
	
	@PostMapping(value="/addCustomer", consumes="application/json")
	public Customer registerCustomer(@Valid @RequestBody Customer customer) {
		customer.setEnabled(true);
		// Set encrypted password.
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(hashedPassword);
		Cart newCart = new Cart();
		newCart.setCustomer(customer);
		newCart.setGrandTotal(0);
		cartRepo.save(newCart);
		customer.setCart(newCart);
		
		Users newUser = new Users();
		newUser.setUsername(customer.getUsername());
		newUser.setPassword(customer.getPassword());
		newUser.setEnabled(true);
		newUser.setCustomerId(customer.getCustomerId());
		usersRepo.save(newUser);
		
		Authorities newAuthority = new Authorities();
		newAuthority.setUsername(customer.getUsername());
		newAuthority.setAuthority("ROLE_USER");
		authoritiesRepo.save(newAuthority);
		
		return customerRepo.save(customer);
	}
	
	
	@GetMapping(value="/getCustomers", produces="application/json")
	public List<Customer> getAllCustomers(){
		return customerRepo.findAll();
	}
	
	@GetMapping(value="/customer/{id}", produces="application/json")
	public Customer getCustomer(@PathVariable("id") int id){
		Optional<Customer> opc = customerRepo.findById(id);
		return opc.isPresent() ? opc.get() : null;
	}
	
	@PutMapping(value="/customer/{id}", consumes="application/json")
	public ResponseEntity<Object> updateCustomer(@PathVariable("id") int id, @RequestBody Customer customerDetails){
		Optional<Customer> opc = customerRepo.findById(id);
		Customer customer = opc.isPresent() ? opc.get() : null;
		if(customer != null) {
			customer.setCustomerName(customerDetails.getCustomerName());
			customer.setUsername(customerDetails.getUsername());
			customer.setCustomerEmail(customerDetails.getCustomerEmail());
			customer.setCustomerAddress(customerDetails.getCustomerAddress());
			
			return ResponseEntity.ok().body(customerRepo.save(customer));
		}
		return ResponseEntity.badRequest().body("Error updating customer");
	}
	
	@DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(value = "id") int id){
	    customerRepo.deleteById(id);
	    return ResponseEntity.ok().body("Customer Deleted Successfully");
    }
}
