package com.diogocosta.cursospringionic.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.diogocosta.cursospringionic.domain.Cliente;
import com.diogocosta.cursospringionic.repositories.ClienteRepository;
import com.diogocosta.cursospringionic.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService { // serviço para alteração de senha 

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe; 
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	
	public void sendNewPassword(String email) { // Verifica se há um cliente com esse email, recupera a senha e envia pra ele 
		
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado!");
		}
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
			
	}


	private String newPassword() {

		char[] vet = new char[10];
		
		for(int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}


	private char randomChar() {
		
		int opt = rand.nextInt(3); // gera os dígitos da senha 
		
		if(opt == 0) {
			return (char) (rand.nextInt(10) + 48); // 48 é o código ASCI do 0
		}
		else if(opt == 1) {
			return (char) (rand.nextInt(26) + 65);
		}
		else {
			return (char) (rand.nextInt(26) + 97);
		}
				
	}
}
