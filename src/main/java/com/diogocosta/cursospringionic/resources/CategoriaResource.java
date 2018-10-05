package com.diogocosta.cursospringionic.resources;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.diogocosta.cursospringionic.domain.Categoria;
import com.diogocosta.cursospringionic.dto.CategoriaDTO;
import com.diogocosta.cursospringionic.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id){
		Categoria obj = service.find(id);
		
		return ResponseEntity.ok(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/id{}").buildAndExpand(obj.getId()).toUri();       // usa os padrões de retorno de requição para setar e criar a URI nova com o novo dado disponível
		return ResponseEntity.created(uri).build();
	}

	// Pega o id via GET da URL e busca pela categoria para fazer o update  
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll(){
		List<Categoria> list = service.findAll();
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList()); // Mapeia e gera um objeto CategoriaDTO a partir das Categorias
		
		return ResponseEntity.ok().body(listDto);
	}

	// Requisição para fazer a paginação 
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") 			Integer page, 
			@RequestParam (value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam (value="orderBy", defaultValue="nome") 	String orderBy, 
			@RequestParam (value="direction", defaultValue="ASC")	String direction){
		
		Page<Categoria> list = service.findPage(page,linesPerPage,orderBy,direction);
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj)); // Mapeia e gera um objeto CategoriaDTO a partir das Categorias
		
		return ResponseEntity.ok().body(listDto);
	}
}


	





