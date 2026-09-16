package com.devfernandes.dscatalogo.services;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.devfernandes.dscatalogo.dto.EmailDTO;
import com.devfernandes.dscatalogo.dto.NewPasswordDTO;
import com.devfernandes.dscatalogo.entities.PasswordRecover;
import com.devfernandes.dscatalogo.entities.User;
import com.devfernandes.dscatalogo.repositories.PasswordRecoverRepository;
import com.devfernandes.dscatalogo.repositories.UserRepository;
import com.devfernandes.dscatalogo.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

	@Value("${email.password-recover.token.minutes}") // para pegar o tempo em minintos configurado no
														// application.properties
	private Long tokenMinutes;

	@Value("${email.password-recover.uri}") // o link que permite o user alterar email configurado no
											// application.properties
	private String recoverUri;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordRecoverRepository passwordRecoverRepository;

	@Transactional
	public void createRcouverToken(EmailDTO body) {

		User user = userRepository.findByEmail(body.getEmail());
		if (user == null) {
			throw new ResourceNotFoundException("Email nao encontrado");
		}
		String token = UUID.randomUUID().toString();// UUID.randomUUID() gera um numero ndomico grande que serve com
													// token de recuperação

		PasswordRecover entity = new PasswordRecover();
		entity.setEmail(body.getEmail());
		entity.setToken(token);
		entity.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));

		entity = passwordRecoverRepository.save(entity);

		String text = "Acesse o link para definir uma nova senha\n\n" + recoverUri + token + ". Validade "
				+ tokenMinutes + " minutos";

		emailService.sendEmail(body.getEmail(), "Recuperação de senha", text); // para quem que vai enviar o assunto e o
																				// corpo
	}

	@Transactional
	public void saveNewPassword(NewPasswordDTO body) {

		List<PasswordRecover> result = passwordRecoverRepository.searchValidTokens(body.getToken(), Instant.now());

		if (result.size() == 0) {
			throw new ResourceNotFoundException("Token invalido");

		}
		User user = userRepository.findByEmail(result.get(0).getEmail());
		user.setPassword(passwordEncoder.encode(body.getPassword()));
		user = userRepository.save(user);

	}
	
	// Obter usuário logado

	protected User authenticated() {
	  try {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
	    String username = jwtPrincipal.getClaim("username");
	    return userRepository.findByEmail(username);
	  }
	  catch (Exception e) {
	    throw new UsernameNotFoundException("Invalid user");
	  }
	}


}
