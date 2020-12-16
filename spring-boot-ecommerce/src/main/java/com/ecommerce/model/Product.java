package com.ecommerce.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int productId;

	@NotNull
	private String productName;
	
	@NotNull
	private String productCategory;
	
	@NotNull
	private String productDescription;
	
	private String status;

	@Min(value = 0)
	private int stock;
	
	private String manufacturer;
	
	@Min(value = 0)
	private int price;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] productImage;
	
	private String productImageFileName; 
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CartItem> cartItemList;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<ConfirmedOrderItem> confirmedOrderItemList;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public byte[] getProductImage() {
		return productImage;
	}

	public void setProductImage(byte[] productImage) {
		this.productImage = productImage;
	}

	public String getProductImageFileName() {
		return productImageFileName;
	}

	public void setProductImageFileName(String productImageFileName) {
		this.productImageFileName = productImageFileName;
	}

	public List<CartItem> getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(List<CartItem> cartItemList) {
		this.cartItemList = cartItemList;
	}

	public List<ConfirmedOrderItem> getConfirmedOrderItemList() {
		return confirmedOrderItemList;
	}

	public void setConfirmedOrderItemList(List<ConfirmedOrderItem> confirmedOrderItemList) {
		this.confirmedOrderItemList = confirmedOrderItemList;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productCategory="
				+ productCategory + ", productDescription=" + productDescription + ", status=" + status + ", stock="
				+ stock + ", manufacturer=" + manufacturer + ", Price=" + price + "]";
	}

}
