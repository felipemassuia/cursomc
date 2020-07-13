package com.felipe.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.felipe.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg);

}
