package com.kots.sidim.web.controller;

import java.util.List;

import com.kots.sidim.web.dao.DAOFactory;
import com.kots.sidim.web.dao.InteresseClienteDAO;
import com.kots.sidim.web.model.InteresseCliente;
import com.kots.sidim.web.model.InteresseClienteId;
import com.kots.sidim.web.util.Biblio;



public class InteresseClienteBO {



	public void salvar(InteresseCliente interesseCliente) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			InteresseClienteDAO dao = factory.getDAO(InteresseClienteDAO.class);
			dao.save(interesseCliente);
		} catch (Exception e) {
			Biblio.tratarErro("insereInteresseCliente", e);
		}
	}
	
	public void excluir(InteresseCliente interesseCliente) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			InteresseClienteDAO dao = factory.getDAO(InteresseClienteDAO.class);
			dao.delete(interesseCliente);
		} catch (Exception e) {
			Biblio.tratarErro("removeInteresseCliente", e);
		}
	}

	public InteresseCliente obter(InteresseClienteId id) {
		InteresseCliente retorno = null; 
		try {
			DAOFactory factory = DAOFactory.getInstance();
			InteresseClienteDAO dao = factory.getDAO(InteresseClienteDAO.class);
			InteresseCliente entidadeFilter = new InteresseCliente();
			entidadeFilter.setId(id);

			retorno = dao.uniqueResult(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("obterInteresseCliente", e);
		}
		return retorno;
	}
	

	public List<InteresseCliente> listar() {
		List<InteresseCliente> lista = null;
		DAOFactory factory = DAOFactory.getInstance();
		InteresseClienteDAO dao = factory.getDAO(InteresseClienteDAO.class);

		InteresseCliente entidadeFilter = new InteresseCliente();
		try {
			lista = dao.listar(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("buscaInteresseCliente", e);
		}
		return lista;
	}
	
	
}
