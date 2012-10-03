package com.kots.sidim.web.util;


import java.util.List;

import com.kots.sidim.web.controller.ImovelBO;
import com.kots.sidim.web.controller.TipoImovelBO;
import com.kots.sidim.web.model.Imovel;
import com.kots.sidim.web.model.TipoImovel;


public class MainTeste {
	

	public MainTeste() {
	}

	public static void main(String[] args) {
		String ret = "END";
		try {

			
			ImovelBO imovelBO = new ImovelBO();
			
			
			List<Imovel> lst = null;
			
			TipoImovel tipo;
			
			lst = imovelBO.listar();
			String nome = "nada";
			for (Imovel v : lst) {
				tipo = v.getTipoImovel();
				nome = nome + " - " + tipo.getDescricao();
			}
			ret = nome;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(ret);
	}

}
