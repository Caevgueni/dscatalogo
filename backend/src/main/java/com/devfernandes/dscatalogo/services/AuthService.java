package com.devfernandes.dscatalogo.services;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devfernandes.dscatalogo.dto.EmailDTO;
import com.devfernandes.dscatalogo.entities.PasswordRecover;
import com.devfernandes.dscatalogo.entities.User;
import com.devfernandes.dscatalogo.repositories.PasswordRecoverRepository;
import com.devfernandes.dscatalogo.repositories.UserRepository;
import com.devfernandes.dscatalogo.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class AuthService {
	
	@Value("${email.password-recover.token.minutes}") // para pegar o tempo em minintos configurado no application.properties 
	private Long tokenMinutes;
	
	@Value("${email.password-recover.uri}") // o link que permite o user alterar email configurado no application.properties 
	private String recoverUri;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordRecoverRepository passwordRecoverRepository;

	@Transactional
	public void createRcouverToken(EmailDTO body) {
		
		User user = userRepository.findByEmail(body.getEmail());
		if(user == null) {
			throw new ResourceNotFoundException("Email nao encontrado");
		}
		String token = UUID.randomUUID().toString();// UUID.randomUUID() gera um numero ndomico grande que serve com token de recuperação
		
		PasswordRecover entity = new PasswordRecover();
        entity.setEmail(body.getEmail());
        entity.setToken(token); 
        entity.setExpiration(Instant.now().plusSeconds(tokenMinutes*60L));
        
        entity = passwordRecoverRepository.save(entity);
        
        String text ="Acesse o link para definir uma nova senha\n\n"
        		+ recoverUri  + token + ". Validade " + tokenMinutes + " minutos";
        
        emailService.sendEmail(body.getEmail(), "Recuperação de senha", text); //para quem que vai enviar o assunto e o corpo
	}

}
