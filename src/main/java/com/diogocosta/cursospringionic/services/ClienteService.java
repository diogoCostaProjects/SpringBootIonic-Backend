package com.diogocosta.cursospringionic.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.diogocosta.cursospringionic.domain.Cidade;
import com.diogocosta.cursospringionic.domain.Cliente;
import com.diogocosta.cursospringionic.domain.Endereco;
import com.diogocosta.cursospringionic.domain.enums.TipoCliente;
import com.diogocosta.cursospringionic.dto.ClienteDTO;
import com.diogocosta.cursospringionic.dto.ClienteNewDTO;
import com.diogocosta.cursospringionic.repositories.ClienteRepository;
import com.diogocosta.cursospringionic.repositories.EnderecoRepository;
import com.diogocosta.cursospringionic.services.exceptions.DataIntegrityException;
import com.diogocosta.cursospringionic.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Transactional    										// Garante a transação na inserção do banco de dados 
	public Cliente insert(Cliente obj){
		enderecoRepository.save(obj.getEnderecos());
		obj = repo.save(obj);
		return obj;
	}	

	public Cliente find(Integer id) {
		
		Cliente obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado Id: " + id + ",Tipo: " + Cliente.class.getName());
		}
		return obj;
	}

	public Cliente update(Cliente obj) {
		
		Cliente newObj = find(obj.getId()); 
		updateData(newObj, obj);
		find(obj.getId()); 
		return repo.save(newObj);
	}

	public void delete(Integer id) {

		find(id);
		try {
			repo.delete(id);
		} catch (DataIntegrityViolationException e) { 
			throw new DataIntegrityException("Não é possível excluir um cliente que possua pedidos!");
		}
	}

	public List<Cliente> findAll() {
		
		return repo.findAll();

	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	
	public Cliente fromDto(ClienteDTO objDto) {

		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null,null);
	}
	
	
	
	public Cliente fromDto(ClienteNewDTO objDto) {
		
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid  = new Cidade(objDto.getCidadeId(), null,null);
		Endereco end  = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cid,cli);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		
		
		if(objDto.getTelefone2() != null){
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3() != null){
			cli.getTelefones().add(objDto.getTelefone3());
		}
		
		return cli;
	}
	
	
	
	private void updateData(Cliente newObj, Cliente obj){
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}

// por @Transational no insert do cliente 

// Não utilizar cidadeRepository

