package com.diogocosta.cursospringionic.services.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.diogocosta.cursospringionic.domain.security.UserSS;
import com.diogocosta.cursospringionic.dto.CredenciaisDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager; // injetado no
															// construtor
	private JWTUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override // método para fazer a autenticação de login de usuário, que pega os dados vindos da request pelo HttpServletRequest e converte em CredenciaisDTO
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
		
		try {
				CredenciaisDTO creds = new ObjectMapper().readValue(req.getInputStream(), CredenciaisDTO.class);
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(),creds.getSenha(), new ArrayList<>());
				Authentication auth = authenticationManager.authenticate(authToken);
			
				return auth;
			
		} catch (IOException e) {
				throw new RuntimeException(e);
		}
	}

	
	// Monta o token
	protected void successfulAuthentication(HttpServletRequest req, 
											HttpServletResponse res, 
											FilterChain chain,
											Authentication auth) throws IOException, ServletException {

			String username = ((UserSS) auth.getPrincipal()).getUsername();
			String token = jwtUtil.generateToken(username);
			res.addHeader("Authorization", "Bearer " + token);
	}

}
