package com.diogocosta.cursospringionic.resources.exception;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Classe de erro específica para tratamento dos campos, pega o campo e gera as mensagens de erro que estão ocorrendo naquele campo

public class ValidationError extends StandardError implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private List<FieldMessage> errors = new ArrayList<>();
	
	
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
		
	}

	public List<FieldMessage> getErrors() { // mudado de getList para getErrors pois no JSON será pega a palavra do método get e posta como chave "Errors":{ "ex" }
		return errors;
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}

	
}
