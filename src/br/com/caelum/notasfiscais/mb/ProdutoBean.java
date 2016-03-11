package br.com.caelum.notasfiscais.mb;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.notasfiscais.dao.ProdutoDao;
import br.com.caelum.notasfiscais.modelo.Produto;
import br.com.caelum.notasfiscais.tx.Transactional;

@Model // Contém @Named e @RequestScoped
public class ProdutoBean implements Serializable{
	private Produto produto = new Produto();
	private List<Produto> produtos;
	private double total;
	
	@Inject
	private ProdutoDao produtoDao;
	
	@Transactional
	public List<Produto> getProdutos() {
		if(this.produtos == null) {
			System.out.println("Carregando os produtos...");
			this.produtos = this.produtoDao.listaTodos();
		}
		return produtos;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
/*	public double getTotal() {
		if(this.produtos == null) return 0;
		
		for (Produto produto : produtos) {
			this.total += produto.getPreco();
		}
		
		return this.total;
	}*/
	
	@Transactional
	public void grava() {
		
		if(this.produto.getId() == null) {
			this.produtoDao.adiciona(produto);			
		} else {
			this.produtoDao.atualiza(produto);
		}
		
		this.produto = new Produto();
		this.produtos = this.produtoDao.listaTodos();
	}
	
	@Transactional
	public void remove(Produto produto) {
		this.produtoDao.remove(produto);
		this.produtos = this.produtoDao.listaTodos();
	}
	
}
