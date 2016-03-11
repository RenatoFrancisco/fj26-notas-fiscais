package br.com.caelum.notasfiscais.mb;


import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.notasfiscais.dao.UsuarioDao;
import br.com.caelum.notasfiscais.modelo.Usuario;
import br.com.caelum.notasfiscais.tx.Transactional;

@Model // Contém @Named e @RequestScoped
public class LoginBean implements Serializable{
	private Usuario usuario = new Usuario();
	
	@Inject
	private UsuarioDao dao;
	
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	
	@Inject
	Event<Usuario> eventoLogin;
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	@Transactional
	public String efetuaLogin() {
		boolean existe = dao.existe(this.usuario);
		System.out.println("Login valido? " + existe);
		if(existe) {
			this.eventoLogin.fire(this.usuario);
			
			this.usuarioLogado.loga(this.usuario);
			return "produto?faces-redirect=true";
		} else {
			this.usuarioLogado.desloga();
			this.usuario = new Usuario();
			return "login?faces-redirect=true";
		}
	}
	
	public String logout() {
		this.usuarioLogado.desloga();
		return "login?faces-redirect=true";
	}
}
