package com.kots.sidim.web.controller;

import java.util.List;

import com.kots.sidim.web.dao.BairroDAO;
import com.kots.sidim.web.dao.DAOFactory;
import com.kots.sidim.web.model.Bairro;
import com.kots.sidim.web.model.Cidade;
import com.kots.sidim.web.util.Biblio;

public class BairroBO {

	public BairroBO() {
		
	}



	public Boolean verificarBairroExiste(String nomeBairro, Cidade cidade) {
		Boolean result = false;
		
		List<Bairro> lista = listar(cidade);
		for (Bairro item : lista) {
			if (item.getNome().toUpperCase().equals( nomeBairro.toUpperCase() )) {
				result = true;
			}
		}

		return result;
	}

	public void salvar(Bairro bairro) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			BairroDAO dao = factory.getDAO(BairroDAO.class);
			dao.save(bairro);
		} catch (Exception e) {
			Biblio.tratarErro("insereCidade", e);
		}
	}

	public void excluir(Bairro bairro) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			BairroDAO dao = factory.getDAO(BairroDAO.class);
			dao.delete(bairro);
		} catch (Exception e) {
			Biblio.tratarErro("removeBairro", e);
		}
	}

	public Bairro obter(int idBairro) {
		Bairro retorno = null;
		try {
			DAOFactory factory = DAOFactory.getInstance();
			BairroDAO dao = factory.getDAO(BairroDAO.class);
			Bairro entidadeFilter = new Bairro();
			entidadeFilter.setIdBairro(idBairro);

			retorno = dao.uniqueResult(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("obterBairro", e);
		}
		return retorno;
	}

	public List<Bairro> listar(Cidade cidade) {
		List<Bairro> lista = null;
		DAOFactory factory = DAOFactory.getInstance();
		BairroDAO dao = factory.getDAO(BairroDAO.class);

		Bairro entidadeFilter = new Bairro();
		if (cidade != null) {
			entidadeFilter.setCidade(cidade);
		}

		try {
			lista = dao.listar(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("buscaBairro", e);
		}
		return lista;
	}
	
	

}
