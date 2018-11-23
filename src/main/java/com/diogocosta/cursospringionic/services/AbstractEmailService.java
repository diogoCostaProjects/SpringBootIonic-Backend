package com.diogocosta.cursospringionic.services;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import com.diogocosta.cursospringionic.domain.Pedido;


public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}") // email configurado como remetente
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm  = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj){
		
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail()); //destinatário
		sm.setFrom(sender); // remetente
        sm.setSubject("Pedido confirmado! Código"+ obj.getId()); // assunto
        sm.setText(obj.toString()); // corpo do email
		
		return sm;
	}

	

}
