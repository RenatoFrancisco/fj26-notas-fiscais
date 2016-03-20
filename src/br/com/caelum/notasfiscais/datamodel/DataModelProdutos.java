package br.com.caelum.notasfiscais.datamodel;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.caelum.notasfiscais.dao.ProdutoDao;
import br.com.caelum.notasfiscais.modelo.Produto;

public class DataModelProdutos extends LazyDataModel<Produto> {
	
	@Inject
	private ProdutoDao dao;
	
	@PostConstruct
	private void inicializaTotal() {
		super.setRowCount(dao.contaTodos());
	}
	
	@Override
	public List<Produto> load(int inicio, int quantidade, String campoOrdenacao,
			SortOrder sentidoOrdenacao, Map<String, Object> filtros) {
		return dao.listaTodosPaginada(inicio, quantidade);
	}
}
