package com.diogocosta.cursospringionic.services;
import org.springframework.security.core.context.SecurityContextHolder;
import com.diogocosta.cursospringionic.domain.security.UserSS;



public class UserService {

	
	public static UserSS authenticated() { // retorna usuário autenticado com base no User SS
		try{
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception e ) {
			return null;
		}
	}
}
