package com.kots.sidim.web.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.slf4j.LoggerFactory;

import com.kots.sidim.web.model.Bairro;
import com.kots.sidim.web.model.Cidade;
import com.kots.sidim.web.model.Cliente;
import com.kots.sidim.web.model.Configuracao;
import com.kots.sidim.web.model.Estado;
import com.kots.sidim.web.model.Foto;
import com.kots.sidim.web.model.Funcionario;
import com.kots.sidim.web.model.Imovel;
import com.kots.sidim.web.model.InteresseCliente;
import com.kots.sidim.web.model.InteresseClienteId;
import com.kots.sidim.web.model.PendenciaCadastroImoveis;
import com.kots.sidim.web.model.Perfil;
import com.kots.sidim.web.model.TipoImovel;


/**
 * Classe utilit�ria que inicializa a SessionFactory e abre as sess�es do
 * hibernate.
 * 
 */
public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();

	/**
	 * Inicializa a SessionFactory do hibernate.<br/>
	 * � utilizada uma SessionFactory por inst�ncia do hibernate.
	 * 
	 * @return SessionFactory inicializada
	 */
	private static SessionFactory buildSessionFactory() {
		AnnotationConfiguration cfg = new AnnotationConfiguration();

		List<Class<?>> list = new ArrayList<Class<?>>();

		
		
		
		
		list.add(Bairro.class);
		list.add(Cidade.class);
		list.add(Cliente.class);
		list.add(Configuracao.class);
		list.add(Estado.class);
		list.add(Funcionario.class);
		list.add(Imovel.class);
		list.add(InteresseCliente.class);
		list.add(InteresseClienteId.class);
		list.add(PendenciaCadastroImoveis.class);
		list.add(Perfil.class);
		list.add(TipoImovel.class);
		list.add(Foto.class);
		
		
		

		// Itera sobre as classes de mapeamento e adiciona ao configurador do
		// hibernate
		for (Iterator<Class<?>> it = list.iterator(); it.hasNext();) {
			cfg.addAnnotatedClass(it.next());
		}

		// Carrega o arquivo hibermate.cfg.xml de dentro do jar
		URL hibernateCfgUrl = Thread.currentThread().getContextClassLoader()
				.getResource("hibernate.cfg.xml");
		cfg.configure(hibernateCfgUrl);

		try {

			// Cria a SessionFactory atrav�s do arquivo hibernate.xfg.xml
			return cfg.buildSessionFactory();

		} catch (Exception e) {
			LoggerFactory.getLogger(HibernateUtil.class).error(
					"Erro ao iniciar a SessionFactory", e);
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * GET SessionFactory
	 * 
	 * @return atributo sessionFactory
	 */
	private static SessionFactory getSessionFactory() {
		return (sessionFactory);
	}

	/**
	 * Abre a sess�o com o banco de dados
	 * 
	 * @return Session com o banco de dados
	 */
	public synchronized static Session openSession() {
		return (getSessionFactory().openSession());
	}
}
