package com.devfernandes.dscatalogo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devfernandes.dscatalogo.entities.Category;
import com.devfernandes.dscatalogo.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly =true)
	public List<Category>  findAll() {
		
	return	categoryRepository.findAll();
	}

}
