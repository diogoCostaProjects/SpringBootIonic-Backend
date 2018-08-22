package com.diogocosta.cursospringionic.services.exceptions;

public class ObjectNotFoundException extends RuntimeException {
// Classe criada para fazer o tratamento de exceções 
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
 
}
