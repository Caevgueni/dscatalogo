package com.devfernandes.dscatalogo.controller.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.model.FieldNamingStrategy;

public class ValidationError extends StandardError {
	
	
	private List<FieldMessage> errors = new ArrayList<>();


	
	public List<FieldMessage> getErrors() {
		return errors;
	}





	public void addError(String fieldName, String message) {
		
		errors.add(new FieldMessage(fieldName, message));
		
	}

}
