package com.kots.sidim.web.dao;

public class HibernateDAOFactory extends DAOFactory {
	public HibernateDAOFactory() {
	}

	@Override
	public <T> T getDAO(Class<T> c) {
		T dao = null;

		try {
			dao = c.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("N�o foi poss�vel instanciar o DAO: " + c.getName(), e);
		}

		return (dao);
	}
}
