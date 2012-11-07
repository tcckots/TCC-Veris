package com.kots.sidim.android.model;



@SuppressWarnings("serial")
public class Estado implements java.io.Serializable {

	private String uf;
	private String nome;	

	public Estado() {
	}

	public Estado(String uf, String nome) {
		this.uf = uf;
		this.nome = nome;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
