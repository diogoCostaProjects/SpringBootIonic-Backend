package com.diogocosta.cursospringionic.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.diogocosta.cursospringionic.domain.Categoria;
import com.diogocosta.cursospringionic.repositories.CategoriaRepository;
import com.diogocosta.cursospringionic.services.exceptions.DataIntegrityException;
import com.diogocosta.cursospringionic.services.exceptions.ObjectNotFoundException;

//classe de serviço para separação por camadas

@Service  
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	
	
	
	public Categoria find(Integer id){
		Categoria obj = repo.findOne(id);
		
		if(obj == null){
				throw new ObjectNotFoundException("Objeto não encontrado Id: " + id +	
												",Tipo: "+Categoria.class.getName());
		}
		return obj;
	}
	
	
	
	public Categoria insert(Categoria obj){
		return repo.save(obj);
	}
	
	
	public Categoria update(Categoria obj){
        find (obj.getId()); // primeiro executa o find pois ele lança uma exceção caso não encontre o objeto que deve ser atualizado 
		
		return repo.save(obj);
	}
	
	public void delete(Integer id){
		
		find(id);
		try{
			repo.delete(id);
		}
		catch (DataIntegrityViolationException e){      // Exceção personalizada  
			throw new  DataIntegrityException("Não é possível excluir uma categoria que possua produtos!");
		}
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
		
	}
}
