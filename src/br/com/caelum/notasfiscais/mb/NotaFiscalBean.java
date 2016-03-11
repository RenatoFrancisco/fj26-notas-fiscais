package br.com.caelum.notasfiscais.mb;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.notasfiscais.dao.NotaFiscalDao;
import br.com.caelum.notasfiscais.dao.ProdutoDao;
import br.com.caelum.notasfiscais.modelo.Item;
import br.com.caelum.notasfiscais.modelo.NotaFiscal;
import br.com.caelum.notasfiscais.modelo.Produto;
import br.com.caelum.notasfiscais.stereotypes.ViewModel;
import br.com.caelum.notasfiscais.tx.Transactional;

@ViewModel // Annotation personalizada, agrupa @Named e @View
public class NotaFiscalBean implements Serializable {
	private NotaFiscal notaFiscal = new NotaFiscal();
	private Item item = new Item();
	private Long idProduto;

	@Inject
	private NotaFiscalDao notaFiscalDao;

	@Inject
	private ProdutoDao produtoDao;

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
	public void grava() {
		this.notaFiscalDao.adiciona(this.notaFiscal);
		this.limpa();
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

	private void limpa() {
		this.notaFiscal = new NotaFiscal();
	}

}
