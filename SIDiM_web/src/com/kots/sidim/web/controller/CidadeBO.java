package com.kots.sidim.web.controller;

import java.util.List;

import com.kots.sidim.web.dao.CidadeDAO;
import com.kots.sidim.web.dao.DAOFactory;
import com.kots.sidim.web.model.Cidade;
import com.kots.sidim.web.model.Estado;
import com.kots.sidim.web.util.Biblio;

public class CidadeBO {

	public CidadeBO() {
		
	}

	Cidade cidade;

	public Boolean validarCidade() {
		Boolean result = false;

		return result;
	}

	public Boolean verificaCidadeExiste(String nomeCidade, Estado estado) {
		Boolean result = false;
		
		List<Cidade> lista = listar(estado);
		for (Cidade item : lista) {
			if (item.getNome().toUpperCase().equals( nomeCidade.toUpperCase() )) {
				result = true;
			}
		}

		return result;
	}

	public void salvar(Cidade cidade) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			CidadeDAO dao = factory.getDAO(CidadeDAO.class);
			dao.save(cidade);
		} catch (Exception e) {
			Biblio.tratarErro("insereCidade", e);
		}
	}

	public void excluir(Cidade cidade) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			CidadeDAO dao = factory.getDAO(CidadeDAO.class);
			dao.delete(cidade);
		} catch (Exception e) {
			Biblio.tratarErro("removeCidade", e);
		}
	}

	public Cidade obter(int id_cidade) {
		Cidade retorno = null;
		try {
			DAOFactory factory = DAOFactory.getInstance();
			CidadeDAO dao = factory.getDAO(CidadeDAO.class);
			Cidade entidadeFilter = new Cidade();
			entidadeFilter.setIdCidade(id_cidade);

			retorno = dao.uniqueResult(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("obterCidade", e);
		}
		return retorno;
	}

	public List<Cidade> listar(Estado estado) {
		List<Cidade> lista = null;
		DAOFactory factory = DAOFactory.getInstance();
		CidadeDAO dao = factory.getDAO(CidadeDAO.class);

		Cidade entidadeFilter = new Cidade();
		if (estado != null) {
			entidadeFilter.setEstado(estado);
		}

		try {
			lista = dao.listar(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("buscaCidade", e);
		}
		return lista;
	}
}
