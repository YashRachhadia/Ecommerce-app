package com.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class CartItem {


	@Id
	@GeneratedValue
	private int cartItemId;

	@ManyToOne
	@JoinColumn(name = "cartId")
	private Cart cart;

	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;

	private int quantity;
	
	private double totalPrice;

	public int getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(int cartItemId) {
		this.cartItemId = cartItemId;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "CartItem [cartItemId=" + cartItemId + ", cart=" + cart + ", product=" + product + ", quantity="
				+ quantity + ", totalPrice=" + totalPrice + "]";
	}
	
}
