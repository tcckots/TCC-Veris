package com.kots.sidim.web.controller;

import java.util.List;

import com.kots.sidim.web.dao.DAOFactory;
import com.kots.sidim.web.dao.FuncionarioDAO;
import com.kots.sidim.web.model.Funcionario;
import com.kots.sidim.web.util.Biblio;



public class FuncionarioBO {



	public void salvar(Funcionario funcionario) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			FuncionarioDAO dao = factory.getDAO(FuncionarioDAO.class);
			dao.save(funcionario);
		} catch (Exception e) {
			Biblio.tratarErro("insereFuncionario", e);
		}
	}
	
	public void excluir(Funcionario funcionario) {
		try {
			DAOFactory factory = DAOFactory.getInstance();
			FuncionarioDAO dao = factory.getDAO(FuncionarioDAO.class);
			dao.delete(funcionario);
		} catch (Exception e) {
			Biblio.tratarErro("removeFuncionario", e);
		}
	}

	public Funcionario obter(String cpf) {
		Funcionario retorno = null; 
		try {
			DAOFactory factory = DAOFactory.getInstance();
			FuncionarioDAO dao = factory.getDAO(FuncionarioDAO.class);
			Funcionario entidadeFilter = new Funcionario();
			entidadeFilter.setCpf(cpf);

			retorno = dao.uniqueResult(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("obterFuncionario", e);
		}
		return retorno;
	}
	

	public List<Funcionario> listar() {
		List<Funcionario> lista = null;
		DAOFactory factory = DAOFactory.getInstance();
		FuncionarioDAO dao = factory.getDAO(FuncionarioDAO.class);

		Funcionario entidadeFilter = new Funcionario();
		try {
			lista = dao.listar(entidadeFilter, null);
		} catch (Exception e) {
			Biblio.tratarErro("buscaFuncionario", e);
		}
		return lista;
	}
	
	
}
