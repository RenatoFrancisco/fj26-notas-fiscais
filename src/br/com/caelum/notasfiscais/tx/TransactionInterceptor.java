package br.com.caelum.notasfiscais.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Interceptor
@Transactional
public class TransactionInterceptor implements Serializable {

	@Inject
	private EntityManager manager;
	
	@AroundInvoke
	public Object intercept(InvocationContext ctx) throws Exception {
		System.out.println("Abrindo a conexão...");
		this.manager.getTransaction().begin();

		// Passando para o JSF tratar a requisição e pegando o retorno da
		// lógica.
		Object resultado = ctx.proceed();

		this.manager.getTransaction().commit();
		System.out.println("Comitando a transação...");

		return resultado;
	}
}
