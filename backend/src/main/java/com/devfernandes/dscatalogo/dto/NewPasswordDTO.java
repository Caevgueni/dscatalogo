package com.devfernandes.dscatalogo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewPasswordDTO {
	
	@NotBlank(message = "Campo obrigatorio")
	private String token;
	
	@NotBlank(message = "Campo obrigatorio")
	@Size(min = 8, message = "Deve ter min 8 caracteres")
	private String password;
	
	
	
	
	public NewPasswordDTO() {
		
	}
	
	public NewPasswordDTO( String token, String password) {
		this.token = token;
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setEmail(String email) {
		this.password = password;
	}

	

	
	
}
