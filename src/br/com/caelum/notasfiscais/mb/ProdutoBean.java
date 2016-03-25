package br.com.caelum.notasfiscais.mb;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.facelets.FaceletContext;
import javax.inject.Inject;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import br.com.caelum.notasfiscais.dao.GraficoDao;
import br.com.caelum.notasfiscais.dao.ProdutoDao;
import br.com.caelum.notasfiscais.datamodel.DataModelProdutos;
import br.com.caelum.notasfiscais.modelo.Produto;
import br.com.caelum.notasfiscais.modelo.QuantidadePorProduto;
import br.com.caelum.notasfiscais.tx.Transactional;

@Model // Contém @Named e @RequestScoped
public class ProdutoBean implements Serializable{
	private Produto produto = new Produto();
	private List<Produto> produtos;
	private List<QuantidadePorProduto> listaRelatorio;
	private double total;
	
	@Inject
	private ProdutoDao produtoDao;
	
	@Inject
	private GraficoDao graficoDao;
	
	@Inject
	private DataModelProdutos dataModel;
	
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
	
	public DataModelProdutos getDataModel() {
		return dataModel;
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
	
	public PieChartModel getRelatorioQuantidadePorProduto() {
		PieChartModel model = new PieChartModel();
		
		model.setTitle("Quantidade vendida por Produto");
		model.setLegendPosition("e");
		model.setShowDataLabels(true);
		
		
		for (QuantidadePorProduto qtde : this.getListaParaRelatorio()) {
			model.set(qtde.getProduto().getNome(), qtde.getQuantidade());
		}
		return model;
	}
	
	public BarChartModel getChartBar() {
		BarChartModel bar = new BarChartModel();
		
		bar.setTitle("Quantidade vendida por Produto");
		bar.setLegendPosition("e");
		bar.setShowDatatip(true);
		bar.setShowPointLabels(true);
		
		Axis axis = bar.getAxis(AxisType.Y);
		axis.setLabel("Quantidade");
	
		ChartSeries produtos = new ChartSeries();
		produtos.setLabel("Produto");
		
		for (QuantidadePorProduto qtde : this.getListaParaRelatorio()) {
			produtos.set(qtde.getProduto().getNome(), qtde.getQuantidade());
		}
		
		bar.addSeries(produtos);
		
		return bar;
	}
	
	private List<QuantidadePorProduto> getListaParaRelatorio() {
		if(this.listaRelatorio == null) {
			this.listaRelatorio = this.graficoDao.relatorioQuantidadePorProduto();
		}
		return this.listaRelatorio;
	}
	
	public void cancela() {
		this.produto = new Produto();
	}
}
