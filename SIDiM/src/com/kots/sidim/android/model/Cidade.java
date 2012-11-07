package com.kots.sidim.android.model;


@SuppressWarnings("serial")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cidade other = (Cidade) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	
}
