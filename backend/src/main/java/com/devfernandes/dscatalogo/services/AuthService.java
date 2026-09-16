package com.devfernandes.dscatalogo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devfernandes.dscatalogo.dto.EmailDTO;
import com.devfernandes.dscatalogo.entities.User;
import com.devfernandes.dscatalogo.repositories.UserRepository;
import com.devfernandes.dscatalogo.services.exceptions.ResourceNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;

	public void createRcouverToken(EmailDTO body) {
		
		User user = userRepository.findByEmail(body.getEmail());
		if(user == null) {
			throw new ResourceNotFoundException("Email nao encontrado");
		}
		
	}

}
