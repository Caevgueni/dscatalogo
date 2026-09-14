package com.devfernandes.dscatalogo.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devfernandes.dscatalogo.dto.CategoryDTO;
import com.devfernandes.dscatalogo.dto.ProductDTO;
import com.devfernandes.dscatalogo.entities.Category;
import com.devfernandes.dscatalogo.entities.Product;
import com.devfernandes.dscatalogo.projections.ProductProjection;
import com.devfernandes.dscatalogo.repositories.CategoryRepository;
import com.devfernandes.dscatalogo.repositories.ProductRepository;
import com.devfernandes.dscatalogo.services.exceptions.DatabaseException;
import com.devfernandes.dscatalogo.services.exceptions.ResourceNotFoundException;
import com.devfernandes.dscatalogo.util.Utils;

import jakarta.persistence.EntityNotFoundException;


@Service
public class ProductService {
	
	
	
	@Autowired
	private ProductRepository repository;
	

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly =true)
	public Page<ProductDTO> findAllPaged(Pageable pageable) {
		Page<Product> list = repository.findAll(pageable);
	return list.map(x -> new ProductDTO(x));
	}
	
	@Transactional(readOnly =true)
	public ProductDTO findById( Long id) {
		Optional<Product> obj = repository.findById(id);
		
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado")); 
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		
		// o metodo foi emplementado la em baixo
		CopyDtoToEntity(dto, entity);
		
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}

	

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {

		try {
		Product entity = repository.getReferenceById(id);
		
		// o metodo foi emplementado la em baixo
		CopyDtoToEntity(dto, entity);
		
		entity = repository.save(entity);
		return new ProductDTO(entity);
		
		}
		catch(EntityNotFoundException e) {
			
			throw new ResourceNotFoundException("Id not found" + id);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
			repository.deleteById(id);    		
		}
	    	catch (DataIntegrityViolationException e) {
	        	throw new DatabaseException("Falha de integridade referencial");
	   	}
	}
	
	
	
	
	// o consumidor desse metodo está mentodo insert a updatate em cima
	private void CopyDtoToEntity(ProductDTO dto, Product entity) {
	
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		entity.getCategories().clear(); // apagar as possiveis categorias antes de instanciar as novas
		
		for(CategoryDTO catDto : dto.getCategories()) {
			
			Category category = categoryRepository.getOne(catDto.getId()); // usamos o funçao getOne ao envés do findById, para assinalar o id do produto que vamos atualizar
			entity.getCategories().add(category);
		}
		
	}
	
	public Page<ProductDTO> findAllPaged(String name, String categoryId, Pageable pageable){
		
		List<Long> categorieIds = Arrays.asList();
		if(!"0".equals(categoryId)) {
			categorieIds = Arrays.asList(categoryId.split(",")).stream().map(Long::parseLong).toList();
		}
		Page<ProductProjection> page = repository.searchProducts(categorieIds, name, pageable);
		List<Long> productsIds = page.map(x -> x.getId()).toList();
		List<Product> entities = repository.searchProductsWithCategories(productsIds);
		entities =Utils.replace(page.getContent(), entities);
		
		List<ProductDTO> dtos = entities.stream().map(p -> new ProductDTO(p, p.getCategories())).toList();
		Page<ProductDTO> pageDto = new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
		return pageDto;
	}
	
	
	
}
