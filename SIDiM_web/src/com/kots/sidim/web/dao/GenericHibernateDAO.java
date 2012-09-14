package com.kots.sidim.web.dao;

import java.io.Serializable;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.LockMode;
import org.hibernate.PropertyValueException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.DataException;
import org.slf4j.LoggerFactory;

import com.kots.sidim.web.util.HibernateUtil;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public abstract class GenericHibernateDAO<T, ID extends Serializable> {

	// Armazena a classe que esse DAO implementa
	private Class<T> persistentClass;

	// Sess�o que o DAO est� utilizando
	private Session session;

	/**
	 * Intancia o DAO armazenando a classe da entidade que esse DAO manipula
	 */
	@SuppressWarnings("unchecked")
	public GenericHibernateDAO() {
		persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Configura a sess�o desse DAO com a sess�o passada como par�metro.
	 * 
	 * @param s
	 *            Sess�o a ser utilizada por esse DAO
	 */
	public void setSession(Session s) {
		this.session = s;
	}

	/**
	 * Retorna a sess�o que est� sendo usada por esse DAO, ou abre uma nova
	 * sess�o caso a atual esteja fechada
	 * 
	 * @return Sess�o a ser utilizada por esse DAO
	 */
	protected Session getSession() {
		if ((session == null) || (!session.isOpen())) {
			session = HibernateUtil.openSession();
		}

		return (session);
	}

	/**
	 * Faz a busca pelo ID da entidade
	 * 
	 * @param id
	 *            ID da entidade que deseja ser buscada
	 * @return Entidade que foi buscada de acordo com o ID informado
	 */
	@SuppressWarnings("unchecked")
	public T findById(ID id) {
		T entity = null;

		try {
			entity = (T) getSession().load(persistentClass, id);

			closeSession();
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(
					"Exce��o ao executar findById; id=" + id, e);
		}

		return (entity);
	}

	/**
	 * Faz o lock da entidade
	 * 
	 * @param entity
	 *            Entidade que se deseja efetuar o lock
	 */
	public void lock(T entity) {
		getSession().lock(entity, LockMode.UPGRADE);
	}

	/**
	 * Inicia uma transa��o
	 * 
	 * @return <code>true</code> caso a transa��o seja iniciada corretamente<br/>
	 *         <code>false</code> caso ocorra algum erro ao iniciar a transa��o.
	 */
	public Boolean beginTransaction() {
		Boolean r = false;

		try {
			getSession().beginTransaction();

			r = true;
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(
					"Exce��o do iniciar transa��o", e);
		}

		return (r);
	}

	/**
	 * Faz o commit de uma transa��o que est� aberta.
	 * 
	 * @return <code>true</code> caso o commit seja efetuado corretamente<br/>
	 *         <code>false</code> caso ocorra algum erro ao efetuar o commit
	 */
	public Boolean commit() {
		Boolean r = false;

		try {
			getSession().getTransaction().commit();
			closeSession();

			r = true;
		} catch (DataException e) {
			if (e.getSQLException() != null) {
				if ("String or binary data would be truncated.".equals(e
						.getSQLException().getMessage())) {

					LoggerFactory.getLogger(this.getClass()).error(
							"Algum dado pode ser truncado");
					LoggerFactory.getLogger(this.getClass()).error(
							e.getSQLException().toString());
				}
			}
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(
					"Exce��o n�o tratada ao efetuar commit", e);
		}

		return (r);
	}

	/**
	 * Faz o rollback de uma transa��o que est� aberta.
	 * 
	 * @return <code>true</code> caso o rollback seja efetuado corretamente<br/>
	 *         <code>false</code> caso ocorra algum erro ao efetuar o rollback
	 */
	public Boolean rollback() {
		Boolean r = false;

		try {
			getSession().getTransaction().rollback();
			closeSession();

			r = true;
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(
					"Exce��o n�o tratada ao efetuar rollback", e);
		}

		return (r);
	}

	/**
	 * Verifica se a sess�o possui transa��o ativa
	 * 
	 * @return <code>true</code> caso a sess�o possui transa��o ativa<br/>
	 *         <code>false</code> caso a sess�o n�o possua transa��o ativa
	 */
	public Boolean isTransactionActive() {
		return (getSession().getTransaction().isActive());
	}

	/**
	 * Fecha a sess�o atual do DAO caso a mesma n�o possua transa��o ativa
	 */
	public void closeSession() {
		if ((getSession().getTransaction() == null) || !isTransactionActive()) {
			session.close();
		}
	}

	/**
	 * Persiste uma entidade no banco de dados, n�o faz o commit da transa��o.
	 * 
	 * @param entity
	 *            entidade que se deseja persistir no banco de dados
	 * @return entidade que foi persistida no banco ou <code>null</code> caso
	 *         ocorra algum erro.
	 */
	public T makePersistent(T entity) {
		T r = null;

		try {
			getSession().saveOrUpdate(entity);

			r = entity;
		} catch (PropertyValueException e) {
			LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
			if (e.getMessage().contains(
					"not-null property references a null or transient value")) {
				LoggerFactory.getLogger(this.getClass()).error(
						"Entidade=" + e.getEntityName() + ";Coluna="
								+ e.getPropertyName());
			}
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(
					"Exce��o n�o tratada ao executar makePersistent", e);
		}

		return (r);
	}

	/**
	 * Apaga uma entidade do banco de dados, n�o faz o commit da transa��o.
	 * 
	 * @param entity
	 *            entidade que se deseja apagar do banco de dados
	 * @return <code>true</code> caso a entidade seja apagada corretamente<br/>
	 *         <code>false</code> caso contr�rio
	 */
	public Boolean makeTransient(T entity) {
		Boolean r = false;

		try {
			getSession().delete(entity);

			r = true;
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(
					"Exce��o n�o tratada ao executar makePersistent", e);
		}

		return (r);
	}

	/**
	 * Persiste uma entidade no banco de dados efetuando o commit da transa��o.
	 * 
	 * @param entity
	 *            entidade que se deseja gravar no banco de dados
	 * @return <code>true</code> caso a entidade seja gravada corretamente<br/>
	 *         <code>false</code> caso contr�rio
	 */
	public Boolean save(T entity) {
		Boolean r = false;

		try {
			if (beginTransaction()) {
				makePersistent(entity);
				r = commit();
			}
		} catch (Exception e) {
			if (isTransactionActive()) {
				rollback();
			}

			LoggerFactory.getLogger(this.getClass()).error(
					"Exce��o n�o tratada ao executar save", e);
		}

		return (r);
	}

	/**
	 * Apaga uma entidade do banco de dados efetuando o commit da transa��o.
	 * 
	 * @param entity
	 *            entidade que se deseja apagar do banco de dados
	 * @return <code>true</code> caso a entidade seja apagada corretamente<br/>
	 *         <code>false</code> caso contr�rio
	 */
	public Boolean delete(T entity) {
		Boolean r = false;

		try {
			if (beginTransaction()) {
				makeTransient(entity);
				r = commit();
			}
		} catch (Exception e) {
			if (isTransactionActive()) {
				rollback();
			}

			LoggerFactory.getLogger(this.getClass()).error(
					"Exce��o n�o tratada ao executar delete", e);
		}

		return (r);
	}

	/**
	 * Faz o select usando os campos preenchidos da entidade como filtros. Todos
	 * os campos s�o filtrados com AND.
	 * 
	 * @param entity
	 *            entidade com os campos que ser�o utilizados como filtro
	 * @param orderBy
	 *            nome dos campos que ser�o utilizados no order by, separados
	 *            por v�rgula
	 * @return Lista contendo os registros que foram encontrados ou uma lista
	 *         vazia caso nenhum registro seja encontrado
	 */
	@SuppressWarnings("unchecked")
	public List<T> listar(T entity, String orderBy) {
		List<T> r = new ArrayList<T>();

		try {
			if (entity == null) {
				entity = persistentClass.newInstance();
			}

			Session session = getSession();
			StringBuilder sb = new StringBuilder("SELECT t FROM "
					+ persistentClass.getSimpleName() + " as t WHERE ");

			Field[] fields = persistentClass.getDeclaredFields();
			
			for (Field field : fields) {
				if (validarProperty(entity,field)) {
					sb.append("t." + field.getName() + " = :" + field.getName() + " AND ");
				}
			}

			sb.append("1=1 ");
			System.out.println("nossa query " + sb.toString());

			if (orderBy != null) {
				sb.append(" Order By ").append(orderBy);
			}

			Query query = session.createQuery(sb.toString());

			for (Field field : fields) {
				if (validarProperty(entity,field)) {
					query.setParameter(field.getName(),PropertyUtils.getSimpleProperty(entity,field.getName()));
				}
			}

			r = query.list();

			closeSession();
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(
					"Exce��o n�o tratada ao listar", e);
		}

		return (r);
	}
		
		
	private Boolean validarProperty(T entity, Field field) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Boolean ret = false;
		Object objeto = PropertyUtils.getSimpleProperty(entity, field.getName());
		
		if (objeto instanceof HashSet) {
			ret=false;
		} else {
			if (objeto != null) {
				ret=true;
				
				if (field.getName().toUpperCase().contains("ID")) {
					try { if ( (Integer) objeto == 0) ret=false; } catch (Exception e) {}
					try { if ( (Double) objeto == 0) ret=false; } catch (Exception e) {}
					try { if ( (Long) objeto == 0) ret=false; } catch (Exception e) {}
					try { if ( (Short) objeto == 0) ret=false; } catch (Exception e) {}
				}
			}
		}
		
		return ret;
	}
		
		
		

	/**
	 * Faz o select usando os campos preenchidos da entidade como filtros. Todos
	 * os campos s�o filtrados com AND.
	 * 
	 * @param entity
	 *            entidade com os campos que ser�o utilizados como filtro
	 * @param orderBy
	 *            nome dos campos que ser�o utilizados no order by, separados
	 *            por v�rgula
	 * @return entidade que foi encontrada ou <code>null</code> caso nenhum
	 *         registro seja encontrado
	 * @throws Exception
	 */
	public T uniqueResult(T entity, String orderBy) throws Exception {
		T r = null;

		List<T> lista = listar(entity, orderBy);

		if (lista.size() > 1) {
			throw new Exception("Foi retornado mais do que 1 tupla");
		}

		if (lista.size() == 1) {
			r = lista.get(0);
		}

		return (r);
	}

	/**
	 * Verifica se uma entidade existe no banco de dados
	 * 
	 * @param entity
	 *            entidade com os campos que ser�o utilizados como filtro
	 * @return <code>true</code> caso a entidade seja encontrada<br/>
	 *         <code>false</code> caso contr�rio
	 */
	public Boolean exists(T entity) {
		Boolean r = false;

		T unique;
		try {
			unique = uniqueResult(entity, null);

			if (unique != null) {
				r = true;
			}
		} catch (Exception e) {
		}

		return (r);
	}

	/**
	 * Faz o select no banco de dados, usando o SQL passado como par�metro.
	 * 
	 * @param strSqlQuery
	 *            SQL com o select a ser executado
	 * @return Lista de Object[], onde cada �ndice do array � um campo na ordem
	 *         que foi informado no select, e cada item na lista � um registro
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> listBySqlQuery(String strSqlQuery) {
		List<Object[]> r = new ArrayList<Object[]>();

		try {
			Query query = getSession().createSQLQuery(strSqlQuery);
			r = query.list();
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(
					"Exce��o n�o tratada ao executar listBySQLQuery", e);
		}
		return (r);
	}

	/**
	 * Faz o select no banco de dados, usando o HQL passado como par�metro.
	 * 
	 * @param strQuery
	 *            HQL com o select a ser executado
	 * @return Lista das entidades que foram encontradas
	 */
	@SuppressWarnings("unchecked")
	public List<T> listTByQuery(String strQuery) {
		List<T> r = new ArrayList<T>();

		try {
			Query query = getSession().createQuery(strQuery);
			r = query.list();
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(
					"Exce��o n�o tratada ao executar listTByQuery", e);
		}
		return (r);
	}

	/**
	 * Faz o select no banco de dados, usando o HQL passado como par�metro.
	 * 
	 * @param strQuery
	 *            HQL com o select a ser executado
	 * @return Lista de Object[], onde cada �ndice do array � um campo na ordem
	 *         que foi informado no select, e cada item na lista � um registro
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> listByQuery(String strQuery) {
		List<Object[]> r = new ArrayList<Object[]>();

		try {
			Query query = getSession().createQuery(strQuery);
			r = query.list();
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(
					"Exce��o n�o tratada ao executar listTByQuery", e);
		}
		return (r);
	}
}
