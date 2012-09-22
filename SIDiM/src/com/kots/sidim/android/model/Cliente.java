package com.kots.sidim.android.model;



public class Cliente extends ResultSidimAPI implements java.io.Serializable {

	private String login;
	private String cidade;
	private String nome;	
	private String telefone;
	private String senha;

	public Cliente() {
	}

	public Cliente(String login, String nome, String senha) {
		this.login = login;
		this.nome = nome;		
		this.senha = senha;
	}

		public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}


	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
