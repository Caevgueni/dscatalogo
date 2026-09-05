package com.devfernandes.dscatalogo.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.devfernandes.dscatalogo.entities.Product;




@DataJpaTest
public class ProductRepositoryTests {

@Autowired
private ProductRepository repository;

@Test
public void deleteShouldDeleteObjectWhendExists() {
	
	long existeId = 1L;
	
	repository.deleteById(existeId);
	
	
	Optional<Product> result = repository.findById(existeId);
	Assertions.assertFalse(result.isPresent());
}


}
