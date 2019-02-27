package com.diogocosta.cursospringionic.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.diogocosta.cursospringionic.domain.Cliente;
import com.diogocosta.cursospringionic.domain.Pedido;

public interface EmailService { // PADRÃO TEMPLATE METHOD

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);	
	
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void sendHtmlEmail(MimeMessage msg);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
