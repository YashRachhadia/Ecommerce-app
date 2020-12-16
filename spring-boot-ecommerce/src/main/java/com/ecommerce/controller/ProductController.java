package com.ecommerce.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dao.ProductRepository;
import com.ecommerce.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ProductController {
	
	@Autowired
	ProductRepository productRepo;
	
	@PostMapping(value="/addProduct")
	public Product addProduct(@RequestParam("file") MultipartFile file,
			@Valid @RequestParam("product") String product) throws IOException{
		Product prod = new ObjectMapper().readValue(product, Product.class);
		prod.setProductImage(file.getBytes());
		prod.setProductImageFileName(file.getOriginalFilename());
		return productRepo.save(productRepo.save(prod));
	}
	
	@PutMapping(value="/product/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable("id") int id, @RequestParam(value="file", required=false) 
	MultipartFile file, @Valid @RequestParam("product") String product) throws IOException{
		Optional<Product> opc = productRepo.findById(id);
		Product upProd = opc.isPresent() ? opc.get() : null;
		if(upProd != null) {
			Product temp = new ObjectMapper().readValue(product, Product.class);
			if(file != null) {
				upProd.setProductImage(file.getBytes());
				upProd.setProductImageFileName(file.getOriginalFilename());
			}
			upProd.setProductName(temp.getProductName());
			upProd.setProductCategory(temp.getProductCategory());
			upProd.setProductDescription(temp.getProductDescription());
			upProd.setStatus(temp.getStatus());
			upProd.setStock(temp.getStock());
			upProd.setManufacturer(temp.getManufacturer());
			upProd.setPrice(temp.getPrice());
			return ResponseEntity.ok().body(productRepo.save(upProd));
		}
		else {
			return ResponseEntity.badRequest().body("Error updating product");
		}
		
	}
	
	@GetMapping(value="/product/{id}")
	public Product getProduct(@PathVariable("id") int id){
		Optional<Product> opc = productRepo.findById(id);
		return opc.isPresent() ? opc.get() : null;
	}
	
	@DeleteMapping(value="/product/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable(value = "id") int id){
	    productRepo.deleteById(id);
	    return ResponseEntity.ok().body("Product Deleted Successfully");
    }
	
	@GetMapping(value="/getProducts")
	public List<Product> getAllProducts(){
		return productRepo.findAll();
	}

}

