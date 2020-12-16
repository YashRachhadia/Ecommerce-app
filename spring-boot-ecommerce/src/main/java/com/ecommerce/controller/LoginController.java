package com.ecommerce.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.AuthenticationData;

@RestController
public class LoginController {
	
	@GetMapping("/validateLogin")
	public AuthenticationData validateLogin() {
		return new AuthenticationData("User successfully authenticated");
	}

}
