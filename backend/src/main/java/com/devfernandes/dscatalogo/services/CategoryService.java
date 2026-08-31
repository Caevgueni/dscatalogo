package com.devfernandes.dscatalogo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devfernandes.dscatalogo.entities.Category;
import com.devfernandes.dscatalogo.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	public List<Category>  findAll() {
		
	return	categoryRepository.findAll();
	}

}
