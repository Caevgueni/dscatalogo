package com.devfernandes.dscatalogo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDTO {
	
	@NotBlank(message = "email tem ser preenchido")
	@Email(message = "email invalido")
	private String email;
	
	EmailDTO(){
		
	}

	public EmailDTO(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
