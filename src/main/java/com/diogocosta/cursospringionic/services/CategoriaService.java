package com.diogocosta.cursospringionic.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.diogocosta.cursospringionic.domain.Categoria;
import com.diogocosta.cursospringionic.dto.CategoriaDTO;
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
		Categoria newObj = find(obj.getId()); 
		updateData(newObj, obj);
		return repo.save(newObj);
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
	
	
	
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page,linesPerPage, Direction.valueOf(direction), orderBy);
		
		return repo.findAll(pageRequest);
	}
	
	
	
	// Converte de DTO para categoria, pra fazer a inserção no banco 
	public Categoria fromDto(CategoriaDTO objDto){
		
		return new Categoria(objDto.getId(), objDto.getNome());
	}
	
	private void updateData(Categoria newObj, Categoria obj){
		newObj.setNome(obj.getNome());
		
	}
}














