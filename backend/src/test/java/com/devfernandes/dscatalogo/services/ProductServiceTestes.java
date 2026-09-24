package com.devfernandes.dscatalogo.services;

import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devfernandes.dscatalogo.repositories.ProductRepository;
import com.devfernandes.dscatalogo.services.exceptions.DatabaseException;
import com.devfernandes.dscatalogo.services.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTestes {
	
	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;

	private long existingId;
	private long nonExistingId;
	private long dependentId;
	@BeforeEach
	void setUp() {

	    existingId = 1L;
	    nonExistingId = 2L;
	    dependentId  = 3l;

	    Mockito.when(repository.existsById(existingId)).thenReturn(true);
	    Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
	    Mockito.when(repository.existsById(dependentId)).thenReturn(true);

	}
	@Test
	public void deleteShouldThrowEmptyResultDatabaseExceptionWh() {
		Assertions.assertThrows(DatabaseException.class,() -> {
			service.delete(dependentId);
		});
	}
	

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

	    Assertions.assertThrows(
	        ResourceNotFoundException.class,
	        () -> service.delete(nonExistingId)
	    );
	}

	@Test
	public void deleteShouldDoNothingWhenIdExist() {

	    Assertions.assertDoesNotThrow(
	        () -> service.delete(existingId)
	    );

	    Mockito.verify(repository, times(1))
	           .deleteById(existingId);
	}
	


}
