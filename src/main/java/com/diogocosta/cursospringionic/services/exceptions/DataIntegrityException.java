package com.diogocosta.cursospringionic.services.exceptions;

public class DataIntegrityException extends RuntimeException {
// Classe criada para fazer o tratamento de exceções 
	private static final long serialVersionUID = 1L;

	public DataIntegrityException(String msg) {
		super(msg);
	}
	
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
 
}
