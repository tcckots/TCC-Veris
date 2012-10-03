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

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();

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

		for (Iterator<Class<?>> it = list.iterator(); it.hasNext();) {
			cfg.addAnnotatedClass(it.next());
		}

		URL hibernateCfgUrl = Thread.currentThread().getContextClassLoader().getResource("hibernate.cfg.xml");
		cfg.configure(hibernateCfgUrl);

		try {
			return cfg.buildSessionFactory();
		} catch (Exception e) {
			LoggerFactory.getLogger(HibernateUtil.class).error("Erro ao iniciar a SessionFactory", e);
			throw new ExceptionInInitializerError(e);
		}
	}

	private static SessionFactory getSessionFactory() {
		return (sessionFactory);
	}

	public synchronized static Session openSession() {
		return (getSessionFactory().openSession());
	}
}
