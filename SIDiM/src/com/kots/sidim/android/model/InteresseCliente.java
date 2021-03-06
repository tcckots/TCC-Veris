package com.kots.sidim.android.model;

import java.util.Date;



@SuppressWarnings("serial")
public class InteresseCliente implements java.io.Serializable {
	
	private ImovelMobile imovel;
	private Cliente cliente;
	private Date data;

	public InteresseCliente() {
	}

	public InteresseCliente(ImovelMobile imovel, Cliente cliente, Date data) {
		
		this.imovel = imovel;
		this.cliente = cliente;
		this.data = data;
	}
	
	
	public ImovelMobile getImovel() {
		return this.imovel;
	}

	public void setImovel(ImovelMobile imovel) {
		this.imovel = imovel;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
