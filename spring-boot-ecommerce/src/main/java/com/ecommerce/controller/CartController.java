package com.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dao.CartItemRepository;
import com.ecommerce.dao.CartRepository;
import com.ecommerce.dao.CustomerRepository;
import com.ecommerce.dao.ProductRepository;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Customer;
import com.ecommerce.model.Product;

@RestController
public class CartController {
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	CartItemRepository cartItemRepo;
	
	@Autowired
	CartRepository cartRepo;
	
	@PostMapping(value="/cart/add/{productId}")
	public ResponseEntity<String> addItem(@PathVariable(value = "productId") int productId, @RequestBody String username){
		Customer customer = customerRepo.findByUsername(username);
		Cart cart = customer.getCart();
		Optional<Product> opc = productRepo.findById(productId);
		Product product = opc.isPresent() ? opc.get() : null;
		if(product != null) {
			if(product.getStock() > 0) {
				List<CartItem> cartItems = cart.getCartItems();
				
				boolean b = false;
				for (int i = 0; i < cartItems.size(); i++) {
					if (product.getProductId() == cartItems.get(i).getProduct().getProductId()) {
						CartItem cartItem = cartItems.get(i);
						cartItem.setQuantity(cartItem.getQuantity() + 1);
						cartItem.setTotalPrice((double)(product.getPrice() * cartItem.getQuantity()));
						cartItemRepo.save(cartItem);
						b = true;
					}
				}
				if(!b) {
					CartItem cartItem = new CartItem();
					cartItem.setProduct(product);
					cartItem.setQuantity(1);
					cartItem.setTotalPrice((double)(product.getPrice() * cartItem.getQuantity()));
					cartItem.setCart(cart);
					cartItemRepo.save(cartItem);
				}
				return ResponseEntity.ok().body("Product is successfully added");
			}
			else {
				return ResponseEntity.badRequest().body("Product is not in stock");
			}
		}
		else {
			return ResponseEntity.badRequest().body("Error adding product to cart");
		}
	}
	
	@GetMapping("/getCart")
	public Cart getCart(@RequestParam("username") String username){
		Customer customer = customerRepo.findByUsername(username);
		return customer.getCart();	
	}
	
	@GetMapping("/getCartById/{cartId}")
	public Cart getCartById(@PathVariable("cartId") int cartId){
		Optional<Cart> opt = cartRepo.findById(cartId);
		return opt.isPresent() ? opt.get() : null;
	}
	
	
	@GetMapping("/getCartItems")
	public List<CartItem> getCartItems(@RequestParam String username){
		Customer customer = customerRepo.findByUsername(username);
		Cart cart = customer.getCart();
		return cartItemRepo.findByCart(cart);
	}
	
	@DeleteMapping(value = "/cart/remove/{productId}")
	public ResponseEntity<String> removeItem(@PathVariable(value = "productId") int productId) {
		Optional<Product> opt = productRepo.findById(productId);
		Product product = opt.isPresent() ? opt.get() : null;
		if(product != null) {
			CartItem cartItem = cartItemRepo.findByProduct(product);
			cartItemRepo.deleteById(cartItem.getCartItemId());
			return ResponseEntity.ok().body("Item removed !");
		}
		else {
			return ResponseEntity.badRequest().body("Error in removing item");
		}
	}
	
	@DeleteMapping(value="/cart/clear/{cartId}")
	public ResponseEntity<String> clearCart(@PathVariable(value="cartId") int cartId){
		Optional<Cart> opt = cartRepo.findById(cartId);
		Cart cart = opt.isPresent() ? opt.get() : null;
		if(cart != null && !cart.getCartItems().isEmpty()) {
			List<CartItem> cartItems = cartItemRepo.findAll();
			for (CartItem item : cartItems) {
				cartItemRepo.deleteById(item.getCartItemId());
			}
			return ResponseEntity.ok().body("Cart is now empty");
		}
		else {
			return ResponseEntity.badRequest().body("Error occured in deleting all cart items !");
		}
	}
	
}
