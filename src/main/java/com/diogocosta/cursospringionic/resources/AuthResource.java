package com.diogocosta.cursospringionic.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.diogocosta.cursospringionic.domain.security.UserSS;
import com.diogocosta.cursospringionic.dto.EmailDTO;
import com.diogocosta.cursospringionic.services.AuthService;
import com.diogocosta.cursospringionic.services.UserService;
import com.diogocosta.cursospringionic.services.security.JWTUtil;

@RestController
@RequestMapping(value ="/auth") // recurso para recuperação de senha e e de token  
public class AuthResource {
	
	@Autowired
	private AuthService authService;
	
	@Autowired 
    private JWTUtil jwtUtil;
	
	@RequestMapping(value="/refresh_token", method=RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/forgot", method=RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		
		authService.sendNewPassword(objDto.getEmail());
		
		return ResponseEntity.noContent().build();
	}
	
}
