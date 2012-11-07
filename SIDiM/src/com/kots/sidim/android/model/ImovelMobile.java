package com.kots.sidim.android.model;

import java.util.List;


@SuppressWarnings("serial")
public class ImovelMobile extends Imovel implements java.io.Serializable {
	
	private List<String> fotos;
	
	public List<String> getFotos() {
		return fotos;
	}
	public void setFotos(List<String> fotos) {
		this.fotos = fotos;
	}
	
	
}
