package com.devfernandes.dscatalogo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devfernandes.dscatalogo.dto.CategoryDTO;
import com.devfernandes.dscatalogo.entities.Category;
import com.devfernandes.dscatalogo.repositories.CategoryRepository;
import com.devfernandes.dscatalogo.services.exceptions.DatabaseException;
import com.devfernandes.dscatalogo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;


@Service
public class CategoryService {
	
	
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly =true)
	public Page<CategoryDTO> findAllPaged(Pageable pageable) {
		Page<Category> list = categoryRepository.findAll(pageable);
	return list.map(x -> new CategoryDTO(x));
	}
	
	@Transactional(readOnly =true)
	public CategoryDTO findById( Long id) {
		Optional<Category> obj = categoryRepository.findById(id);
		
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada")); 
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = categoryRepository.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {

		try {
		Category entity = categoryRepository.getReferenceById(id);

		entity.setName(dto.getName());
		entity = categoryRepository.save(entity);
		return new CategoryDTO(entity);
		
		}
		catch(EntityNotFoundException e) {
			
			throw new ResourceNotFoundException("Id not found" + id);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!categoryRepository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
			categoryRepository.deleteById(id);    		
		}
	    	catch (DataIntegrityViolationException e) {
	        	throw new DatabaseException("Falha de integridade referencial");
	   	}
	}

	
}
