package com.kots.sidim.web.controller;

import java.util.List;

import com.kots.sidim.web.dao.DAOFactory;
import com.kots.sidim.web.dao.ImovelDAO;
import com.kots.sidim.web.model.Imovel;
import com.kots.sidim.web.util.Biblio;

public class ImovelBO {

	public ImovelBO() {
	}
	

	public void salvar(Imovel imovel) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			ImovelDAO dao = factory.getDAO(ImovelDAO.class);
			dao.save(imovel);
		} catch (Exception e) {
			Biblio.tratarErro("insereImovel", e);
		}
	}
	
	public void excluir(Imovel imovel) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			ImovelDAO dao = factory.getDAO(ImovelDAO.class);
			dao.delete(imovel);
		} catch (Exception e) {
			Biblio.tratarErro("removeImovel", e);
		}
	}

	public Imovel obter(int idImovel) {
		Imovel retorno = null; 
		try {
			DAOFactory factory = DAOFactory.getInstance();
			ImovelDAO dao = factory.getDAO(ImovelDAO.class);
			Imovel entidadeFilter = new Imovel();
			entidadeFilter.setIdImovel(idImovel);

			retorno = dao.uniqueResult(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("obterImovel", e);
		}
		return retorno;
	}
	

	public List<Imovel> listar() {
		List<Imovel> lista = null;
		DAOFactory factory = DAOFactory.getInstance();
		ImovelDAO dao = factory.getDAO(ImovelDAO.class);

		Imovel entidadeFilter = new Imovel();
		try {
			lista = dao.listar(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("buscaImovel", e);
		}
		return lista;
	}


	
}
