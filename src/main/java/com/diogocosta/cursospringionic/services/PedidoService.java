package com.diogocosta.cursospringionic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.diogocosta.cursospringionic.domain.Pedido;
import com.diogocosta.cursospringionic.repositories.PedidoRepository;
import com.diogocosta.cursospringionic.services.exceptions.ObjectNotFoundException;

//classe de serviço para separação por camadas

@Service  
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	
	public Pedido buscar(Integer id){
		
		Pedido obj = repo.findOne(id);
		
		
		if(obj == null){  // testa se não retornou e lança a exeção caso nao venha nada
				throw new ObjectNotFoundException("Objeto não encontrado Id: " + id +
												",Tipo: "+Pedido.class.getName());
		}
		return obj;
	}
}
