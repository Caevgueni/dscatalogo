package com.devfernandes.dscatalogo.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.devfernandes.dscatalogo.entities.Product;
import com.devfernandes.dscatalogo.tests.Factory;




@DataJpaTest
public class ProductRepositoryTests {

@Autowired
private ProductRepository repository;
private Integer countTatalProduct;

private long existeId = 1L;

@BeforeEach
void setUp() throws Exception{
	
	existeId = 1L;
	countTatalProduct = 25;
	
}

@Test
public void saveShouldPersistWithAuroincrementWhenIdNull() {
	
	Product product = Factory.creatProduct();
	product.setId(null);
	
	product = repository.save(product);
	Assertions.assertNotNull(product.getId());
	Assertions.assertEquals(countTatalProduct + 1, product.getId());
}
@Test
public void deleteShouldDeleteObjectWhendExists() {
		
	repository.deleteById(existeId);
	Optional<Product> result = repository.findById(existeId);
	Assertions.assertFalse(result.isPresent());
}


}
