package com.kots.sidim.android.model;



public class TipoImovel implements java.io.Serializable {

	private short idTipoImovel;
	private String descricao;	

	public TipoImovel() {
	}

	public TipoImovel(short idTipoImovel, String descricao) {
		this.idTipoImovel = idTipoImovel;
		this.descricao = descricao;
	}

	
	public short getIdTipoImovel() {
		return this.idTipoImovel;
	}

	public void setIdTipoImovel(short idTipoImovel) {
		this.idTipoImovel = idTipoImovel;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
