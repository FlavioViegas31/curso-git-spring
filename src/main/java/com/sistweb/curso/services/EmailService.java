package com.sistweb.curso.services;

import org.springframework.mail.SimpleMailMessage;

import com.sistweb.curso.domain.Cliente;
import com.sistweb.curso.domain.Pedido;

public interface EmailService {
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);

}
