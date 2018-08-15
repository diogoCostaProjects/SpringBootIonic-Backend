package com.diogocosta.cursospringionic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diogocosta.cursospringionic.domain.Categoria;
import com.diogocosta.cursospringionic.repositories.CategoriaRepository;

//classe de serviço para separação por camadas

@Service  
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id){
		Categoria obj = repo.findOne(id);
		
		return obj;
	}
}
