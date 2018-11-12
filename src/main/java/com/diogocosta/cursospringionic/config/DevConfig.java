package com.diogocosta.cursospringionic.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.diogocosta.cursospringionic.services.DBService;

@Configuration
@Profile("dev") /*
					 * define a configuração para o profile Test, informando o
					 * compiler que todos os Beans ficarão ativos apenas quando
					 * o profile estiver ativo no aplication.properties
					 */
public class DevConfig {
	
	@Autowired
	private DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		if(!"create".equals(strategy)){
			return false;
		}
		
		dbService.instantiateDatabase();
		return true;
	}

}
