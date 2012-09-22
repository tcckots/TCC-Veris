package com.kots.sidim.web.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.kots.sidim.web.util.HibernateUtil;


public abstract class DAOFactory {
	public static int TRANSACTION_READ_UNCOMMITTED = Connection.TRANSACTION_READ_UNCOMMITTED;

	// Singleton do DAOFactory
	private static DAOFactory instance;

	// Map com a sessões longas que estão sendo utilizadas
	private static Map<Integer, Session> sessions = new HashMap<Integer, Session>();

	/**
	 * Retorna o singleton do DAOFactory
	 * 
	 * @return singleton do DAOFactory
	 */
	public static DAOFactory getInstance() {
		if (instance == null) {
			instance = new HibernateDAOFactory();
		}

		return (instance);
	}

	/**
	 * Cria uma nova sessão e a associa a um objeto. Essa sessão poderá ser
	 * usada em uma transação que ocorra entre diversos DAOs, isolando o que
	 * ocorre nessa sessão das outras sessões concorrentes. Caso já exista uma
	 * sessão associada ao objeto passado como parâmetro, esta é fechada e uma
	 * nova sessão é associada a esse objeto.
	 * 
	 * @param obj
	 *            objeto ao qual a sessão deve ser associado
	 */
	public synchronized void newSession(Object obj) {
		Session session = sessions.get(obj.hashCode());
		if (session != null) {
			closeSession(obj);
		}

		sessions.put(obj.hashCode(), HibernateUtil.openSession());
	}

	/**
	 * Cria uma nova sessão com o isolation level READ COMMITED e a associa a um
	 * objeto. Essa sessão poderá ser usada em uma transação que ocorra entre
	 * diversos DAOs, isolando o que ocorre nessa sessão das outras sessões
	 * concorrentes. Caso já exista uma sessão associada ao objeto passada como
	 * parâmetro, esta é fechada e uma nova sessão é associada ao objeto.
	 * 
	 * @param obj
	 *            objeto ao qual a sessão deve ser associado
	 * @param transactionLevel
	 *            isolation level da nova sessão
	 * 
	 */
	public synchronized void newSession(Object obj, int transactionLevel) {
		Session session = sessions.get(obj.hashCode());
		if (session != null) {
			closeSession(obj);
		}

		session = HibernateUtil.openSession();
		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				connection
						.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			}
		});

		sessions.put(obj.hashCode(), session);
	}

	/**
	 * Fecha a sessão associada a um determinado objeto e a remove da memória.
	 * Se uma transação estiver ativa, é feito um rollback.
	 * 
	 * @see DAOFactory#newSession(Object)
	 * @param obj
	 *            objeto cuja sessão deve ser fechada
	 */
	private synchronized void closeSession(Object obj) {
		Session session = sessions.get(obj.hashCode());

		if (session != null) {
			if (session.isOpen()) {
				if ((session.getTransaction() != null)
						&& session.getTransaction().isActive()) {
					session.getTransaction().rollback();
				}
			}

			sessions.remove(obj.hashCode());
		}
	}

	/**
	 * Associa a sessão que foi aberta para um determinado objeto ao DAO
	 * 
	 * @param obj
	 *            objeto que possui uma sessão aberta
	 * @param dao
	 *            DAO cuja sessão será configurada
	 * @throws Exception
	 *             caso não exista nenhuma sessão associada ao objeto
	 */
	public synchronized void setSession(Object obj,
			GenericHibernateDAO<?, ?> dao) throws Exception {
		Session session = sessions.get(obj.hashCode());

		if (session == null) {
			throw new Exception("Nenhuma sessão associada a thread informada");
		}

		dao.setSession(session);
	}

	/**
	 * Verifica se a sessão associada ao objeto está aberta
	 * 
	 * @param obj
	 *            objeto cuja sessão deseja ser verificada
	 * @return <code>true</code> caso a sessão esteja aberta, <code>false</code>
	 *         caso a sessão esteja fechada
	 */
	public Boolean isSessionOpen(Object obj) {
		Boolean r = false;

		Session session = sessions.get(obj.hashCode());
		if (session != null) {
			r = session.isOpen();
		}

		return (r);
	}

	/**
	 * Retorna o DAO desejado de acordo com a classe passada como parâmetro e
	 * inicializa com a sessão associada com o objeto passado como parâmetro.
	 * 
	 * @param obj
	 *            objeto que possui a sessão associada
	 * @param c
	 *            Classe do DAO que se deseja obter
	 * @return DAO instanciado e com a sessão desejada
	 * @throws Exception
	 *             Caso o objeto passado não possua nenhuma sessão associada
	 */
	public <T> T getDAO(Object obj, Class<T> c) throws Exception {
		T dao = (T) getDAO(c);
		setSession(obj, (GenericHibernateDAO<?, ?>) dao);

		return (dao);
	}

	/**
	 * Retorna o DAO desejado de acordo com a classe passada como parâmetro
	 * 
	 * @param c
	 *            Classe do DAO que se deseja
	 * @return DAO instanciado
	 */
	public abstract <T> T getDAO(Class<T> c);
}
