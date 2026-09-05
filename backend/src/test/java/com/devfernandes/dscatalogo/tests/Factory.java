package com.devfernandes.dscatalogo.tests;

import java.time.Instant;

import com.devfernandes.dscatalogo.dto.ProductDTO;
import com.devfernandes.dscatalogo.entities.Category;
import com.devfernandes.dscatalogo.entities.Product;

public class Factory {
	
	public static Product creatProduct() {
	Product product = new Product(1L, "Phone", "Good phon", 800.0, "https://raw.githubusercontent.cbig.jpg", Instant.parse("2020-07-14T10:00:00Z"));
	product.getCategories().add(new Category(2L, "Electronics"));
	return product;
	}
	
	public static ProductDTO creatProductDTO() {
    Product product = creatProduct();
    return new ProductDTO(product, product.getCategories());
	}
}
;