package com.diogocosta.cursospringionic.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.diogocosta.cursospringionic.domain.Cliente;
import com.diogocosta.cursospringionic.domain.security.UserSS;
import com.diogocosta.cursospringionic.repositories.ClienteRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService { // serviço de validações do Spring Security

	@Autowired
	private ClienteRepository clienteRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli = clienteRepository.findByEmail(email);
		if(cli == null) {
			throw new UsernameNotFoundException(email);
			
		}
		return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}

}
