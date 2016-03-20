package br.com.caelum.notasfiscais.mb;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.notasfiscais.dao.NotaFiscalDao;
import br.com.caelum.notasfiscais.dao.ProdutoDao;
import br.com.caelum.notasfiscais.modelo.Item;
import br.com.caelum.notasfiscais.modelo.NotaFiscal;
import br.com.caelum.notasfiscais.modelo.Produto;
import br.com.caelum.notasfiscais.tx.Transactional;

@Named
@ConversationScoped
public class NotaFiscalBean implements Serializable {
	private NotaFiscal notaFiscal = new NotaFiscal();
	private Item item = new Item();
	private Long idProduto;

	@Inject
	private NotaFiscalDao notaFiscalDao;

	@Inject
	private ProdutoDao produtoDao;
	
	@Inject
	private Conversation conversation;
	
	public String avanca() {
		if(this.conversation.isTransient()) {
			this.conversation.begin();			
		}
		return "item?faces-redirect=true";
	}


	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public Item getItem() {
		return item;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}
	
	@Transactional
	public String grava() {
		this.notaFiscalDao.adiciona(this.notaFiscal);
		this.limpa();
		this.conversation.end();
		return "notafiscal?faces-redirect=true";
	}

	@Transactional
	public void guardaItem() {
		Produto produto = this.produtoDao.buscaPorId(this.idProduto);

		this.item.setProduto(produto);
		this.item.setValorUnitario(produto.getPreco());

		this.notaFiscal.getItens().add(this.item);
		this.item.setNotaFiscal(this.notaFiscal);

		this.item = new Item();
		this.idProduto = null;
	}
	
	@Transactional
	public List<Produto> busca(String query) {
		return this.produtoDao.buscaPorNome(query);
	}

	private void limpa() {
		this.notaFiscal = new NotaFiscal();
	}

}
