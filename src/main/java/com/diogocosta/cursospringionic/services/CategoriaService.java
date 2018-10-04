package com.diogocosta.cursospringionic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.diogocosta.cursospringionic.domain.Categoria;
import com.diogocosta.cursospringionic.repositories.CategoriaRepository;
import com.diogocosta.cursospringionic.services.exceptions.ObjectNotFoundException;

//classe de serviço para separação por camadas

@Service  
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	
	
	
	public Categoria buscar(Integer id){
		Categoria obj = repo.findOne(id);
		
		if(obj == null){
				throw new ObjectNotFoundException("Objeto não encontrado Id: " + id +
												",Tipo: "+Categoria.class.getName());
		}
		return obj;
	}
	
	
	
	public Categoria insert(Categoria obj){
		
		 							// seta o id como null pois senão o Spring Data considera como um update e não insert 
		return repo.save(obj);
	}
}
