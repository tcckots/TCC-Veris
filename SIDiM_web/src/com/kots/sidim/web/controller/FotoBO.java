package com.kots.sidim.web.controller;

import java.util.List;

import com.kots.sidim.web.dao.DAOFactory;
import com.kots.sidim.web.dao.FotoDAO;
import com.kots.sidim.web.model.Foto;
import com.kots.sidim.web.util.Biblio;

public class FotoBO {

	public FotoBO() {
	}
	

	public void salvar(Foto foto) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			FotoDAO dao = factory.getDAO(FotoDAO.class);
			dao.save(foto);
		} catch (Exception e) {
			Biblio.tratarErro("insereFoto", e);
		}
	}
	
	public void excluir(Foto foto) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			FotoDAO dao = factory.getDAO(FotoDAO.class);
			dao.delete(foto);
		} catch (Exception e) {
			Biblio.tratarErro("removeFoto", e);
		}
	}

	public Foto obter(int idFoto) {
		Foto retorno = null; 
		try {
			DAOFactory factory = DAOFactory.getInstance();
			FotoDAO dao = factory.getDAO(FotoDAO.class);
			Foto entidadeFilter = new Foto();
			entidadeFilter.setIdFoto(idFoto);

			retorno = dao.uniqueResult(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("obterFoto", e);
		}
		return retorno;
	}
	

	public List<Foto> listar() {
		List<Foto> lista = null;
		DAOFactory factory = DAOFactory.getInstance();
		FotoDAO dao = factory.getDAO(FotoDAO.class);

		Foto entidadeFilter = new Foto();
		try {
			lista = dao.listar(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("buscaFoto", e);
		}
		return lista;
	}


	
}
