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

	// Map com a sess�es longas que est�o sendo utilizadas
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
	 * Cria uma nova sess�o e a associa a um objeto. Essa sess�o poder� ser
	 * usada em uma transa��o que ocorra entre diversos DAOs, isolando o que
	 * ocorre nessa sess�o das outras sess�es concorrentes. Caso j� exista uma
	 * sess�o associada ao objeto passado como par�metro, esta � fechada e uma
	 * nova sess�o � associada a esse objeto.
	 * 
	 * @param obj
	 *            objeto ao qual a sess�o deve ser associado
	 */
	public synchronized void newSession(Object obj) {
		Session session = sessions.get(obj.hashCode());
		if (session != null) {
			closeSession(obj);
		}

		sessions.put(obj.hashCode(), HibernateUtil.openSession());
	}

	/**
	 * Cria uma nova sess�o com o isolation level READ COMMITED e a associa a um
	 * objeto. Essa sess�o poder� ser usada em uma transa��o que ocorra entre
	 * diversos DAOs, isolando o que ocorre nessa sess�o das outras sess�es
	 * concorrentes. Caso j� exista uma sess�o associada ao objeto passada como
	 * par�metro, esta � fechada e uma nova sess�o � associada ao objeto.
	 * 
	 * @param obj
	 *            objeto ao qual a sess�o deve ser associado
	 * @param transactionLevel
	 *            isolation level da nova sess�o
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
	 * Fecha a sess�o associada a um determinado objeto e a remove da mem�ria.
	 * Se uma transa��o estiver ativa, � feito um rollback.
	 * 
	 * @see DAOFactory#newSession(Object)
	 * @param obj
	 *            objeto cuja sess�o deve ser fechada
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
	 * Associa a sess�o que foi aberta para um determinado objeto ao DAO
	 * 
	 * @param obj
	 *            objeto que possui uma sess�o aberta
	 * @param dao
	 *            DAO cuja sess�o ser� configurada
	 * @throws Exception
	 *             caso n�o exista nenhuma sess�o associada ao objeto
	 */
	public synchronized void setSession(Object obj,
			GenericHibernateDAO<?, ?> dao) throws Exception {
		Session session = sessions.get(obj.hashCode());

		if (session == null) {
			throw new Exception("Nenhuma sess�o associada a thread informada");
		}

		dao.setSession(session);
	}

	/**
	 * Verifica se a sess�o associada ao objeto est� aberta
	 * 
	 * @param obj
	 *            objeto cuja sess�o deseja ser verificada
	 * @return <code>true</code> caso a sess�o esteja aberta, <code>false</code>
	 *         caso a sess�o esteja fechada
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
	 * Retorna o DAO desejado de acordo com a classe passada como par�metro e
	 * inicializa com a sess�o associada com o objeto passado como par�metro.
	 * 
	 * @param obj
	 *            objeto que possui a sess�o associada
	 * @param c
	 *            Classe do DAO que se deseja obter
	 * @return DAO instanciado e com a sess�o desejada
	 * @throws Exception
	 *             Caso o objeto passado n�o possua nenhuma sess�o associada
	 */
	public <T> T getDAO(Object obj, Class<T> c) throws Exception {
		T dao = (T) getDAO(c);
		setSession(obj, (GenericHibernateDAO<?, ?>) dao);

		return (dao);
	}

	/**
	 * Retorna o DAO desejado de acordo com a classe passada como par�metro
	 * 
	 * @param c
	 *            Classe do DAO que se deseja
	 * @return DAO instanciado
	 */
	public abstract <T> T getDAO(Class<T> c);
}
