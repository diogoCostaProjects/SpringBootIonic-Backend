package com.diogocosta.cursospringionic.services.validation;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.diogocosta.cursospringionic.domain.Cliente;
import com.diogocosta.cursospringionic.domain.enums.TipoCliente;
import com.diogocosta.cursospringionic.dto.ClienteNewDTO;
import com.diogocosta.cursospringionic.repositories.ClienteRepository;
import com.diogocosta.cursospringionic.resources.exception.FieldMessage;
import com.diogocosta.cursospringionic.services.validation.utils.BR;


public class ClienteInsertValidator implements ConstraintValidator <ClienteInsert, ClienteNewDTO> {
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		// VALIDAÇÃO CPF OU CNPJ
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())){
			list.add(new FieldMessage("cpfOuCnpj","CPF inválido!"));
		}
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
			list.add(new FieldMessage("cpfOuCnpj","CNPJ inválido!"));
		}

		//VALIDAÇÃO DO EMAIL
		Cliente aux = repo.findByEmail(objDto.getEmail());
		
		if (aux != null){
			list.add(new FieldMessage("email","e-mail já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();   // no retorno verifica se a lista de erros esta vazia, pois o método isEmpty retorna um boolean
	}
}