package com.diogocosta.cursospringionic.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.diogocosta.cursospringionic.domain.Categoria;
import com.diogocosta.cursospringionic.domain.Produto;
import com.diogocosta.cursospringionic.repositories.CategoriaRepository;
import com.diogocosta.cursospringionic.repositories.ProdutoRepository;
import com.diogocosta.cursospringionic.services.exceptions.ObjectNotFoundException;

//classe de serviço para separação por camadas

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository repoCategorias;
	
	
	
	public Produto find(Integer id) {
		Produto obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado Id: " + id + ",Tipo: " + Produto.class.getName());
		}
		return obj;
	}
	
	
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		
		PageRequest pageRequest = new PageRequest(page,linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = repoCategorias.findAll(ids);
		
			
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}

}
