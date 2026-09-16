package com.devfernandes.dscatalogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devfernandes.dscatalogo.dto.EmailDTO;
import com.devfernandes.dscatalogo.dto.UserDTO;
import com.devfernandes.dscatalogo.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value ="/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	

	
	@PostMapping(value ="/{recover-token}")
	public ResponseEntity<Void> createRecouverToken(@Valid @RequestBody EmailDTO body){
		authService.createRcouverToken(body);
		return ResponseEntity.noContent().build();
		
	}
	
	
	
}
