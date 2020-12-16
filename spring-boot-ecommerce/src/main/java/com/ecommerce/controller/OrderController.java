package com.ecommerce.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dao.CartRepository;
import com.ecommerce.dao.ConfirmedOrderRepository;
import com.ecommerce.dao.CustomerOrderRepository;
import com.ecommerce.dao.PaymentRepository;
import com.ecommerce.model.Cart;
import com.ecommerce.model.ConfirmedOrder;
import com.ecommerce.model.Customer;
import com.ecommerce.model.CustomerOrder;
import com.ecommerce.model.Payment;

@RestController
public class OrderController {

	@Autowired
	CartRepository cartRepo;
	
	@Autowired
	CustomerOrderRepository customerOrderRepo;
	
	@Autowired
	ConfirmedOrderRepository confirmedOrderRepo;
	
	@Autowired
	PaymentRepository paymentRepo;
	
	@PostMapping("/order/{cartId}")
	public ResponseEntity<Object> createOrder(@PathVariable("cartId") int cartId){
		CustomerOrder customerOrder = new CustomerOrder();
		Optional<Cart> optcart = cartRepo.findById(cartId);
		Cart cart =  optcart.isPresent() ? optcart.get() : null; 
		if(cart != null && !cart.getCartItems().isEmpty()) {
			customerOrder.setCart(cart);
			Customer customer = cart.getCustomer();
			customerOrder.setCustomer(customer);
			return ResponseEntity.ok().body(customerOrderRepo.save(customerOrder));
		}
		else {
			return ResponseEntity.badRequest().body("Error creating order !");
		}
	}
	
	
	@GetMapping("/pendingOrders")
	public ResponseEntity<Object> pendingOrders() {
		List<ConfirmedOrder> pendingOrders = confirmedOrderRepo.getAllPendingOrders();
		return ResponseEntity.ok().body(pendingOrders);
	}
	
	@PutMapping("/pendingOrders/setFulfilled/{confirmedOrderId}")
	public ResponseEntity<Object> markFulfilled(@PathVariable("confirmedOrderId") int confirmedOrderId){
		Optional<ConfirmedOrder> opt = confirmedOrderRepo.findById(confirmedOrderId);
		ConfirmedOrder confirmedOrder = opt.isPresent() ? opt.get() : null;
		if(confirmedOrder != null) {
			Optional<Payment> optional = paymentRepo.findById(confirmedOrder.getPayment().getPaymentId());
			Payment payment = optional.isPresent() ? optional.get() : null;
			if(payment != null) {
				payment.setStatus("Paid");
				paymentRepo.save(payment);
				confirmedOrder.setFulfilled(true);
				confirmedOrder.setFulfillmentDate(new Date());
				confirmedOrderRepo.save(confirmedOrder);
				List<ConfirmedOrder> pendingOrders = confirmedOrderRepo.getAllPendingOrders();
				return ResponseEntity.ok().body(pendingOrders);
			}
			else {
				return ResponseEntity.ok().body("Payment not done");
			}
		}
		else {
			return ResponseEntity.ok().body("Error fullfilling the order request");
		}
	}
	
	@GetMapping("/allOrders")
	public List<ConfirmedOrder> allOrders(){
		return confirmedOrderRepo.findAll();
	}
	
}
