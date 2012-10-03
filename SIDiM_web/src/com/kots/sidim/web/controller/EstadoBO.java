package com.kots.sidim.web.controller;


import java.util.List;

import com.kots.sidim.web.dao.DAOFactory;
import com.kots.sidim.web.dao.EstadoDAO;
import com.kots.sidim.web.model.Estado;
import com.kots.sidim.web.util.Biblio;

public class EstadoBO {

	public EstadoBO() {
		
	}

	public void salvar(Estado estado) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			EstadoDAO dao = factory.getDAO(EstadoDAO.class);
			dao.save(estado);
		} catch (Exception e) {
			Biblio.tratarErro("insereEstado", e);
		}
	}
	
	public void excluir(Estado estado) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			EstadoDAO dao = factory.getDAO(EstadoDAO.class);
			dao.delete(estado);
		} catch (Exception e) {
			Biblio.tratarErro("removeEstado", e);
		}
	}

	public Estado obter(String uf) {
		Estado ret = null;
		try {
			DAOFactory factory = DAOFactory.getInstance();
			EstadoDAO dao = factory.getDAO(EstadoDAO.class);
			Estado entidadeFilter = new Estado();
			entidadeFilter.setUf(uf);

			ret = dao.uniqueResult(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("obterEstado", e);
		}
		return ret;
	}

	public List<Estado> listar() {
		List<Estado> lista = null;
		DAOFactory factory = DAOFactory.getInstance();
		EstadoDAO dao = factory.getDAO(EstadoDAO.class);

		Estado entidadeFilter = new Estado();
		try {
			lista = dao.listar(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("buscaEstados", e);
		}
		return lista;
	}
	
}
