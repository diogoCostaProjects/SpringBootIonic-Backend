package com.diogocosta.cursospringionic.dto;

import java.io.Serializable;

import com.diogocosta.cursospringionic.domain.Categoria;

public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	
	public CategoriaDTO(){
		
	}
	
	public CategoriaDTO(Categoria obj){
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/* Esse cara faz o espelhamento da entidade de categoria porém pega apenas os dados necessários, pois não é interessante pegar os produtos relacionados as categorias quando buscamos todas categorias
	 * DTO = Data Transfer Object(Objeto de transferência de dados) 
	 * 
	 * 
	 * */
	
}
