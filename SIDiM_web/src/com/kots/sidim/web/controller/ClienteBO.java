package com.kots.sidim.web.controller;

import java.util.List;

import com.kots.sidim.web.dao.DAOFactory;
import com.kots.sidim.web.dao.ClienteDAO;
import com.kots.sidim.web.model.Cliente;
import com.kots.sidim.web.util.Biblio;



public class ClienteBO {



	public void salvar(Cliente cliente) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			ClienteDAO dao = factory.getDAO(ClienteDAO.class);
			dao.save(cliente);
		} catch (Exception e) {
			Biblio.tratarErro("insereCliente", e);
		}
	}
	
	public void excluir(Cliente cliente) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			ClienteDAO dao = factory.getDAO(ClienteDAO.class);
			dao.delete(cliente);
		} catch (Exception e) {
			Biblio.tratarErro("removeCliente", e);
		}
	}

	public Cliente obter(String login) {
		Cliente retorno = null; 
		try {
			DAOFactory factory = DAOFactory.getInstance();
			ClienteDAO dao = factory.getDAO(ClienteDAO.class);
			Cliente entidadeFilter = new Cliente();
			entidadeFilter.setLogin(login);

			retorno = dao.uniqueResult(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("obterCliente", e);
		}
		return retorno;
	}
	

	public List<Cliente> listar() {
		List<Cliente> lista = null;
		DAOFactory factory = DAOFactory.getInstance();
		ClienteDAO dao = factory.getDAO(ClienteDAO.class);

		Cliente entidadeFilter = new Cliente();
		try {
			lista = dao.listar(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("buscaCliente", e);
		}
		return lista;
	}
	
	
}
