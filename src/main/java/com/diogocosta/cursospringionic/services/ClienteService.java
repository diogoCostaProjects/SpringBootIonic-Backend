package com.diogocosta.cursospringionic.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.diogocosta.cursospringionic.domain.Cliente;
import com.diogocosta.cursospringionic.dto.ClienteDTO;
import com.diogocosta.cursospringionic.repositories.ClienteRepository;
import com.diogocosta.cursospringionic.services.exceptions.DataIntegrityException;
import com.diogocosta.cursospringionic.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

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
	
	private void updateData(Cliente newObj, Cliente obj){
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
