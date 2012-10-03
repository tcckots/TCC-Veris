package com.kots.sidim.web.controller;

import java.util.List;
import com.kots.sidim.web.dao.DAOFactory;
import com.kots.sidim.web.dao.TipoImovelDAO;
import com.kots.sidim.web.model.TipoImovel;
import com.kots.sidim.web.util.Biblio;

public class TipoImovelBO {

	public TipoImovelBO() {

	}


	public void salvar(TipoImovel tipoImovel) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			TipoImovelDAO dao = factory.getDAO(TipoImovelDAO.class);
			dao.save(tipoImovel);
		} catch (Exception e) {
			Biblio.tratarErro("insereTipoImovel", e);
		}
	}
	
	public void excluir(TipoImovel tipoImovel) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			TipoImovelDAO dao = factory.getDAO(TipoImovelDAO.class);
			dao.delete(tipoImovel);
		} catch (Exception e) {
			Biblio.tratarErro("removeTipoImovel", e);
		}
	}

	public TipoImovel obter(int idTipoImovel) {
		TipoImovel retorno = null; 
		try {
			DAOFactory factory = DAOFactory.getInstance();
			TipoImovelDAO dao = factory.getDAO(TipoImovelDAO.class);
			TipoImovel entidadeFilter = new TipoImovel();
			entidadeFilter.setIdTipoImovel(idTipoImovel);

			retorno = dao.uniqueResult(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("obterTipoImovel", e);
		}
		return retorno;
	}
	

	public List<TipoImovel> listar() {
		List<TipoImovel> lista = null;
		DAOFactory factory = DAOFactory.getInstance();
		TipoImovelDAO dao = factory.getDAO(TipoImovelDAO.class);

		TipoImovel entidadeFilter = new TipoImovel();
		try {
			lista = dao.listar(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("buscaTipoImovel", e);
		}
		return lista;
	}

}
