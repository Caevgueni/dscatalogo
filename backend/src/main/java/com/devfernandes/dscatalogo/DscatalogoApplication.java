package com.devfernandes.dscatalogo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.devfernandes.dscatalogo.services.S3Service;

@SpringBootApplication
public class DscatalogoApplication implements CommandLineRunner {

	@Autowired
	private S3Service service;
	
	public static void main(String[] args) {
		SpringApplication.run(DscatalogoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		service.uploadFile("C:\\tempo\\foto.jpg");
	}

}
