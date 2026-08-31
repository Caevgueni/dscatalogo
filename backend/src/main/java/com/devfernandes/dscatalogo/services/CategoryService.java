package com.devfernandes.dscatalogo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devfernandes.dscatalogo.dto.CategoryDTO;
import com.devfernandes.dscatalogo.entities.Category;
import com.devfernandes.dscatalogo.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly =true)
	public List<CategoryDTO>  findAll() {
		List<Category> list = categoryRepository.findAll();
	return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}

}
