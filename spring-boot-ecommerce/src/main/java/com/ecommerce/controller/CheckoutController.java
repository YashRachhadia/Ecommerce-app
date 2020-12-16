package com.ecommerce.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dao.CartItemRepository;
import com.ecommerce.dao.CartRepository;
import com.ecommerce.dao.ConfirmedOrderRepository;
import com.ecommerce.dao.PaymentRepository;
import com.ecommerce.dao.ProductRepository;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.ConfirmedOrder;
import com.ecommerce.model.ConfirmedOrderItem;
import com.ecommerce.model.CustomerOrder;
import com.ecommerce.model.Payment;
import com.ecommerce.model.Product;


@RestController
public class CheckoutController {
	
	@Autowired
	CartItemRepository cartItemRepo;
	
	@Autowired
	ConfirmedOrderRepository confirmedOrderRepo;
	
	@Autowired
	CartRepository cartRepo;
	
	@Autowired
	PaymentRepository paymentRepo;
	
	@Autowired
	ProductRepository productRepo;
	
	@PutMapping("/checkout/{cartId}")
	public ResponseEntity<Object> checkOutFromCart(@PathVariable("cartId") int cartId){
		Optional<Cart> optcart = cartRepo.findById(cartId);
		Cart cart =  optcart.isPresent() ? optcart.get() : null;
		if (cart == null || cart.getCartItems().isEmpty()) {
			return ResponseEntity.badRequest().body("Cart is Empty !");
		}
		else {
			double grandTotal = 0;
			List<CartItem> cartItems = cart.getCartItems();
			for (CartItem item : cartItems) {
				grandTotal += item.getTotalPrice();
			}
			cart.setGrandTotal(grandTotal);
			return ResponseEntity.ok().body(cartRepo.save(cart));
		}
	}
		
	@PostMapping("/orderConfirmation/paybyCod/{cartId}")
	public ResponseEntity<String> payByCod(@PathVariable("cartId") int cartId) {
		Optional<Cart> optcart = cartRepo.findById(cartId);
		Cart cart =  optcart.isPresent() ? optcart.get() : null;
		
		if(cart != null && !cart.getCartItems().isEmpty()) {
			CustomerOrder customerOrder = new CustomerOrder();
			customerOrder.setCustomer(cart.getCustomer());
			customerOrder.setCart(cart);
			ConfirmedOrder confirmedOrder = new ConfirmedOrder();
			confirmedOrder.setCustomer(customerOrder.getCart().getCustomer());
			confirmedOrder.setGrandTotal(customerOrder.getCart().getGrandTotal());
			List<ConfirmedOrderItem> confirmedOrderItems = new ArrayList<>();
			List<CartItem> cartItems = customerOrder.getCart().getCartItems();
			for (CartItem cartItem : cartItems) {
				ConfirmedOrderItem confirmedOrderItem = new ConfirmedOrderItem();
				confirmedOrderItem.setProduct(cartItem.getProduct());
				confirmedOrderItem.setQuantity(cartItem.getQuantity());
				confirmedOrderItem.setTotalPrice(cartItem.getTotalPrice());
				confirmedOrderItem.setConfirmedOrder(confirmedOrder);
				confirmedOrderItems.add(confirmedOrderItem);
			}
			confirmedOrder.setOrderItems(confirmedOrderItems);
			confirmedOrder.setOrderDate(new Date());
			confirmedOrder.setFulfilled(false);
			
			Payment payment = new Payment();
			payment.setMode("COD");
			payment.setStatus("UnPaid");
			paymentRepo.save(payment);
			confirmedOrder.setPayment(payment);
			confirmedOrderRepo.save(confirmedOrder);
			List<CartItem> temp = customerOrder.getCart().getCartItems();
			for(int i=0; i<temp.size(); i++) {
				Product p = temp.get(i).getProduct();
				p.setStock(p.getStock()-temp.get(i).getQuantity());
				productRepo.save(p);
			}
			for (CartItem item : cartItems) {
				cartItemRepo.deleteById(item.getCartItemId());
			}
			cart.setGrandTotal(0);
			cartRepo.save(cart);
			return ResponseEntity.ok().body("Order Successful: Mode of Payment - COD");
		}
		return ResponseEntity.ok().body("Error in payment process");
	}
		
	@PostMapping("/orderConfirmation/payOnline/{cartId}")
	public ResponseEntity<String> payByOnline(@PathVariable("cartId") int cartId) {
		Optional<Cart> optcart = cartRepo.findById(cartId);
		Cart cart =  optcart.isPresent() ? optcart.get() : null;
		
		if(cart != null && !cart.getCartItems().isEmpty()) {
			CustomerOrder customerOrder = new CustomerOrder();
			customerOrder.setCustomer(cart.getCustomer());
			customerOrder.setCart(cart);
			ConfirmedOrder confirmedOrder = new ConfirmedOrder();
			confirmedOrder.setCustomer(customerOrder.getCart().getCustomer());
			confirmedOrder.setGrandTotal(customerOrder.getCart().getGrandTotal());
			List<ConfirmedOrderItem> confirmedOrderItems = new ArrayList<>();
			List<CartItem> cartItems = customerOrder.getCart().getCartItems();
			for (CartItem cartItem : cartItems) {
				ConfirmedOrderItem confirmedOrderItem = new ConfirmedOrderItem();
				confirmedOrderItem.setProduct(cartItem.getProduct());
				confirmedOrderItem.setQuantity(cartItem.getQuantity());
				confirmedOrderItem.setTotalPrice(cartItem.getTotalPrice());
				confirmedOrderItem.setConfirmedOrder(confirmedOrder);
				confirmedOrderItems.add(confirmedOrderItem);
			}
			confirmedOrder.setOrderItems(confirmedOrderItems);
			confirmedOrder.setOrderDate(new Date());
			confirmedOrder.setFulfilled(false);
			
			Payment payment = new Payment();
			payment.setMode("Paid Online");
			payment.setStatus("Paid");
			confirmedOrder.setPayment(payment);
			paymentRepo.save(payment);
			confirmedOrderRepo.save(confirmedOrder);
			List<CartItem> temp = customerOrder.getCart().getCartItems();
			for(int i=0; i<temp.size(); i++) {
				Product p = temp.get(i).getProduct();
				p.setStock(p.getStock()-temp.get(i).getQuantity());
				productRepo.save(p);
			}
			for (CartItem item : cartItems) {
				cartItemRepo.deleteById(item.getCartItemId());
			}
			cart.setGrandTotal(0);
			cartRepo.save(cart);
			return ResponseEntity.ok().body("Order Successful: Mode of Payment - Online");
		}
		return ResponseEntity.ok().body("Error in payment process");
	}
	
}
