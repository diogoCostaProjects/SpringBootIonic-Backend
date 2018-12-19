package com.diogocosta.cursospringionic.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private Environment env;
	
	
	private static final String[] PUBLIC_MATCHERS = {
			"/h2/**"
	
	}; // define caminhos onde o springSecurity não deve interceptar	
	
	
	private static final String[] PUBLIC_MATCHERS_GET = { // Acesso apenas a leitura dos dados 
			"/produtos/**",
			"/categorias/**",
			"/clientes/**"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
				http.cors().and().csrf().disable();
				http.authorizeRequests()
				.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
				.antMatchers(PUBLIC_MATCHERS).permitAll()
				.anyRequest().authenticated();
				http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Pois não trabalha com seções
				
				if (Arrays.asList(env.getActiveProfiles()).contains("test")) { // Verifica os profiles ativos, se for o profile Test, libera o acesso ao H2-console
					http.headers().frameOptions().disable();
				}
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationsource(){
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		
		return source; // permitindo o acesso aos endpoints por multiplas fontes, aos recursos básicos
	}
	
	@Bean 	
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	
}
