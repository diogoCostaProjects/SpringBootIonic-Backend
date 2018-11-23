package com.diogocosta.cursospringionic.services;

import org.springframework.mail.SimpleMailMessage;

import com.diogocosta.cursospringionic.domain.Pedido;

public interface EmailService { // PADRÃO TEMPLATE METHOD

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);	
}
