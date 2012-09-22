package com.kots.sidim.web.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kots.sidim.web.controller.CidadeBO;
import com.kots.sidim.web.controller.EstadoBO;
import com.kots.sidim.web.controller.TipoImovelBO;
import com.kots.sidim.web.model.Cidade;
import com.kots.sidim.web.model.Estado;
import com.kots.sidim.web.model.PendenciaCadastroImoveis;
import com.kots.sidim.web.model.TipoImovel;



public class MainTeste {
	

	public MainTeste() {
	}

	public static void main(String[] args) {
		String ret = "END";
		try {

			/*
			EstadoBO estadoBO = new EstadoBO();
			Estado estado = estadoBO.obter("RS");
			
			CidadeBO cidadeBO = new CidadeBO();
			
			
			List<Cidade> lst = null;
			
			lst = cidadeBO.listar(null);
			String nome="primeiro_item";
			for (Cidade v : lst) {
				nome = nome + " - " + v.getNome();
			}
			ret = nome;
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(ret);
	}

}
