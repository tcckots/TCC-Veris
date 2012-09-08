package com.kots.sidim.android.model;


public class Cidade implements java.io.Serializable {

	private int idCidade;
	private Estado estado;
	private String nome;
	private String padrao;
	
	public Cidade() {
	}

	public Cidade(int idCidade, Estado estado, String nome, String padrao) {
		this.idCidade = idCidade;
		this.estado = estado;
		this.nome = nome;
		this.padrao = padrao;
		
	}

	public int getIdCidade() {
		return this.idCidade;
	}

	public void setIdCidade(int idCidade) {
		this.idCidade = idCidade;
	}


	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}


	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getPadrao() {
		return this.padrao;
	}

	public void setPadrao(String padrao) {
		this.padrao = padrao;
	}
}
