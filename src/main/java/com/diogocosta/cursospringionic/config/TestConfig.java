package com.diogocosta.cursospringionic.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.diogocosta.cursospringionic.services.DBService;
import com.diogocosta.cursospringionic.services.EmailService;
import com.diogocosta.cursospringionic.services.MockEmailService;

@Configuration
@Profile("test") /*
					 * define a configuração para o profile Test, informando o
					 * compiler que todos os Beans ficarão ativos apenas quando
					 * o profile estiver ativo no aplication.properties
					 */
public class TestConfig {
	
	@Autowired
	private DBService dbService;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		dbService.instantiateDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService(){
		return new MockEmailService();
	}

}
