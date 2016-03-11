package br.com.caelum.notasfiscais.listener;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import br.com.caelum.notasfiscais.emails.EmailComercial;
import br.com.caelum.notasfiscais.modelo.Usuario;

public class LoginListener {
	
	@Inject
	@EmailComercial
	private String email;
	
	public void onLogin(@Observes Usuario usuario) {
		System.out.printf("Usuário %s se logou no sistema\n", usuario.getLogin());
		
		System.out.printf("Enviando e-mail para %s\n", this.email);
	}
}
