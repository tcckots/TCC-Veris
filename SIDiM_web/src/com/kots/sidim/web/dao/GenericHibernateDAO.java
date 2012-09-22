package com.kots.sidim.web.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.LockMode;
import org.hibernate.PropertyValueException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.DataException;
import org.slf4j.LoggerFactory;

import com.kots.sidim.web.util.HibernateUtil;

public abstract class GenericHibernateDAO<T, ID extends Serializable> {

	private Class<T> persistentClass;
	private Session session;

	@SuppressWarnings("unchecked")
	public GenericHibernateDAO() {
		persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void setSession(Session s) {
		this.session = s;
	}

	protected Session getSession() {
		if ((session == null) || (!session.isOpen())) {
			session = HibernateUtil.openSession();
		}

		return (session);
	}

	@SuppressWarnings("unchecked")
	public T findById(ID id) {
		T entity = null;

		try {
			entity = (T) getSession().load(persistentClass, id);

			closeSession();
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error("Exceção ao executar findById; id=" + id, e);
		}

		return (entity);
	}

	public void lock(T entity) {
		getSession().lock(entity, LockMode.UPGRADE);
	}

	public Boolean beginTransaction() {
		Boolean r = false;

		try {
			getSession().beginTransaction();

			r = true;
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error("Exceção do iniciar transação", e);
		}

		return (r);
	}

	public Boolean commit() {
		Boolean r = false;

		try {
			getSession().getTransaction().commit();
			closeSession();

			r = true;
		} catch (DataException e) {
			if (e.getSQLException() != null) {
				if ("String or binary data would be truncated.".equals(e.getSQLException().getMessage())) {

					LoggerFactory.getLogger(this.getClass()).error("Algum dado pode ser truncado");
					LoggerFactory.getLogger(this.getClass()).error(e.getSQLException().toString());
				}
			}
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error("Exceção não tratada ao efetuar commit", e);
		}

		return (r);
	}

	public Boolean rollback() {
		Boolean r = false;

		try {
			getSession().getTransaction().rollback();
			closeSession();

			r = true;
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error("Exceção não tratada ao efetuar rollback", e);
		}

		return (r);
	}

	public Boolean isTransactionActive() {
		return (getSession().getTransaction().isActive());
	}

	public void closeSession() {
		if ((getSession().getTransaction() == null) || !isTransactionActive()) {
			session.close();
		}
	}

	public T makePersistent(T entity) {
		T r = null;

		try {
			getSession().saveOrUpdate(entity);

			r = entity;
		} catch (PropertyValueException e) {
			LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
			if (e.getMessage().contains("not-null property references a null or transient value")) {
				LoggerFactory.getLogger(this.getClass()).error("Entidade=" + e.getEntityName() + ";Coluna=" + e.getPropertyName());
			}
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error("Exceção não tratada ao executar makePersistent", e);
		}

		return (r);
	}

	public Boolean makeTransient(T entity) {
		Boolean r = false;

		try {
			getSession().delete(entity);

			r = true;
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error("Exceção não tratada ao executar makePersistent", e);
		}

		return (r);
	}

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

			LoggerFactory.getLogger(this.getClass()).error("Exceção não tratada ao executar save", e);
		}

		return (r);
	}

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

			LoggerFactory.getLogger(this.getClass()).error("Exceção não tratada ao executar delete", e);
		}

		return (r);
	}

	@SuppressWarnings("unchecked")
	public List<T> listar(T entity, String orderBy) {
		List<T> r = new ArrayList<T>();

		try {
			if (entity == null) {
				entity = persistentClass.newInstance();
			}

			Session session = getSession();
			StringBuilder sb = new StringBuilder("SELECT t FROM " + persistentClass.getSimpleName() + " as t WHERE ");

			Field[] fields = persistentClass.getDeclaredFields();

			for (Field field : fields) {
				if (validarProperty(entity, field)) {
					sb.append("t." + field.getName() + " = :" + field.getName() + " AND ");
				}
			}

			sb.append("1=1 ");
			

			if (orderBy != null) {
				if (orderBy.length()>0) {
					sb.append(" Order By ").append(orderBy);
				}
			}
			
			
			System.out.println("Nossa Query: " + sb.toString());
			Query query = session.createQuery(sb.toString());

			for (Field field : fields) {
				if (validarProperty(entity, field)) {
					query.setParameter(field.getName(), PropertyUtils.getSimpleProperty(entity, field.getName()));
				}
			}

			r = query.list();

			closeSession();
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error("Exceção não tratada ao listar", e);
		}

		return (r);
	}

	private Boolean validarProperty(T entity, Field field) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Boolean ret = false;
		Object objeto = PropertyUtils.getSimpleProperty(entity, field.getName());

		if (objeto instanceof HashSet) {
			ret = false;
		} else {
			if (objeto != null) {
				ret = true;

				if (field.getName().toUpperCase().contains("ID")) {
					try {
						if ((Integer) objeto == 0)
							ret = false;
					} catch (Exception e) {
					}
					try {
						if ((Double) objeto == 0)
							ret = false;
					} catch (Exception e) {
					}
					try {
						if ((Long) objeto == 0)
							ret = false;
					} catch (Exception e) {
					}
					try {
						if ((Short) objeto == 0)
							ret = false;
					} catch (Exception e) {
					}
					try {
						if ((Byte) objeto == 0)
							ret = false;
					} catch (Exception e) {
					}
				}
			}
		}

		return ret;
	}

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

	@SuppressWarnings("unchecked")
	public List<Object[]> listBySqlQuery(String strSqlQuery) {
		List<Object[]> r = new ArrayList<Object[]>();

		try {
			Query query = getSession().createSQLQuery(strSqlQuery);
			r = query.list();
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error("Exceção não tratada ao executar listBySQLQuery", e);
		}
		return (r);
	}

	@SuppressWarnings("unchecked")
	public List<T> listTByQuery(String strQuery) {
		List<T> r = new ArrayList<T>();

		try {
			Query query = getSession().createQuery(strQuery);
			r = query.list();
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error("Exceção não tratada ao executar listTByQuery", e);
		}
		return (r);
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> listByQuery(String strQuery) {
		List<Object[]> r = new ArrayList<Object[]>();

		try {
			Query query = getSession().createQuery(strQuery);
			r = query.list();
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error("Exceção não tratada ao executar listTByQuery", e);
		}
		return (r);
	}
}
