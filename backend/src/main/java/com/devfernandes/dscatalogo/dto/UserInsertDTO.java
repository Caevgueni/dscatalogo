package com.devfernandes.dscatalogo.dto;

import com.devfernandes.dscatalogo.services.validatins.UserInsertValid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@UserInsertValid
public class UserInsertDTO extends UserDTO{
	
	
	@NotBlank(message ="campo requerido")
	@Size(min = 8, message="Deve ter no minimo 8 caracter")
	private String password;

	
	public UserInsertDTO() {
		super();
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	

}
