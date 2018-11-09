package com.diogocosta.cursospringionic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		
		
		
	}
	
	 // Esse cara (ComandLineRunner) roda uma Thread com o "run" e instancia os objetos por fora, 
	//  já preparando o banco H2, que funciona todo em memória,
	//  e por isso não guarda nada em disco como um banco normal
}
