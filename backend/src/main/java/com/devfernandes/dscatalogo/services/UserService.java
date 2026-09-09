package com.devfernandes.dscatalogo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devfernandes.dscatalogo.dto.RoleDTO;
import com.devfernandes.dscatalogo.dto.UserDTO;
import com.devfernandes.dscatalogo.dto.UserInsertDTO;
import com.devfernandes.dscatalogo.dto.UserUpdateDTO;
import com.devfernandes.dscatalogo.entities.Role;
import com.devfernandes.dscatalogo.entities.User;
import com.devfernandes.dscatalogo.repositories.CategoryRepository;
import com.devfernandes.dscatalogo.repositories.RoleRepository;
import com.devfernandes.dscatalogo.repositories.UserRepository;
import com.devfernandes.dscatalogo.services.exceptions.DatabaseException;
import com.devfernandes.dscatalogo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable) {
		Page<User> list = repository.findAll(pageable);
		return list.map(x -> new UserDTO(x));
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> obj = repository.findById(id);

		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO insert(UserInsertDTO dto) {
		User entity = new User();
		CopyDtoToEntity(dto, entity); // o metodo foi emplementado la em baixo
        entity.setPassword(passwordEncoder.encode(dto.getPassword())); // passwordEncoder.encode codifica o password
		entity = repository.save(entity);
		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO update(Long id, UserUpdateDTO dto) {

		try {
			User entity = repository.getReferenceById(id);

			// o metodo foi emplementado la em baixo
			CopyDtoToEntity(dto, entity);

			entity = repository.save(entity);
			return new UserDTO(entity);

		} catch (EntityNotFoundException e) {

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
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}

	// o consumidor desse metodo está mentodo insert a updatate em cima
	private void CopyDtoToEntity(UserDTO dto, User entity) {

		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		

		entity.getRoles().clear(); // apagar as possiveis roles antes de instanciar as novas

		for (RoleDTO roleDto : dto.getRoles()) {

			Role role = roleRepository.getOne(roleDto.getId()); // usamos o funçao getOne ao envés do
																			// findById, para assinalar o id do produto
																		// que vamos atualizar
			entity.getRoles().add(role);
		}

	}

}
