package com.diogocosta.cursospringionic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.diogocosta.cursospringionic.domain.Cliente;
import com.diogocosta.cursospringionic.repositories.ClienteRepository;
import com.diogocosta.cursospringionic.services.exceptions.ObjectNotFoundException;

//classe de serviço para separação por camadas

@Service  
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id){
		Cliente obj = repo.findOne(id);
		if(obj == null){
				throw new ObjectNotFoundException("Objeto não encontrado Id: " + id +
												",Tipo: "+Cliente.class.getName());
		}
		return obj;
	}
}
