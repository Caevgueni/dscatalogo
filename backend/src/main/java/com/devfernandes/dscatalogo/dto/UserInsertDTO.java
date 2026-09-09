package com.devfernandes.dscatalogo.dto;

import com.devfernandes.dscatalogo.services.validatins.UserInsertValid;

@UserInsertValid
public class UserInsertDTO extends UserDTO{
	
	
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
