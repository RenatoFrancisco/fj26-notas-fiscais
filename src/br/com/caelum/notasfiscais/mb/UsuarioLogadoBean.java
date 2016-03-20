package br.com.caelum.notasfiscais.mb;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.inject.Named;

import br.com.caelum.notasfiscais.modelo.Usuario;

@Named @SessionScoped
public class UsuarioLogadoBean implements Serializable {
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void loga(Usuario usuario) {
		this.usuario = usuario;
	}

	public void desloga() {
		this.usuario = null;
	}

	public boolean isLogado() {
		return this.usuario != null;
	}
}
