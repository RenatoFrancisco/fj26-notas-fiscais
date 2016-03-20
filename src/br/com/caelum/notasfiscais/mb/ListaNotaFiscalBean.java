package br.com.caelum.notasfiscais.mb;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.notasfiscais.dao.NotaFiscalDao;
import br.com.caelum.notasfiscais.datamodel.DataModelNotasFiscais;
import br.com.caelum.notasfiscais.modelo.NotaFiscal;
import br.com.caelum.notasfiscais.stereotypes.ViewModel;

@ViewModel
public class ListaNotaFiscalBean implements Serializable{
	private List<NotaFiscal> notas;
	
	@Inject
	private NotaFiscalDao dao;
	
	@Inject
	private DataModelNotasFiscais dataModel;
	
	public List<NotaFiscal> getNotas() {
		if(this.notas == null) {
			this.notas = dao.listaTodos();
		}
		return notas;
	}
	
	public DataModelNotasFiscais getDataModel() {
		return dataModel;
	}
}
