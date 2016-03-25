package br.com.caelum.notasfiscais.validator;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.notasfiscais.modelo.Produto;
import br.com.caelum.notasfiscais.util.ComponentResolver;

@FacesValidator("produtoExiste")
public class ValidadorProdutoExiste implements Validator {

	@Inject
	EntityManager manager;

	@Inject
	private ComponentResolver resolver;

	@Override
	public void validate(FacesContext ctx, UIComponent component, Object value)
			throws ValidatorException {

		String nome = String.valueOf(value);

		Query query = this.manager
				.createQuery("SELECT count(p) FROM Produto P WHERE p.nome LIKE :nome");
		query.setParameter("nome", nome);
		Long count = (Long) query.getSingleResult();

		if (count != 0) {
			throw new ValidatorException(new FacesMessage(
					"Nome de Produto já existente"));
		}

	}
}
