package com.kots.sidim.android.model;



@SuppressWarnings("serial")
public class Bairro implements java.io.Serializable {

	private long idBairro;
	private Cidade cidade;
	private String nome;
	private String padrao;
	

	public Bairro() {
	}

	public Bairro(long idBairro, Cidade cidade, String nome, String padrao) {
		this.idBairro = idBairro;
		this.cidade = cidade;
		this.nome = nome;
		this.padrao = padrao;
	}

	

	
	public long getIdBairro() {
		return this.idBairro;
	}

	public void setIdBairro(long idBairro) {
		this.idBairro = idBairro;
	}

	
	public Cidade getCidade() {
		return this.cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
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
