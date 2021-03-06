package com.kots.sidim.web.model;

// Generated 27/08/2012 21:21:46 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Funcionario generated by hbm2java
 */
@Entity
@Table(name = "FUNCIONARIO")
@ManagedBean(name = "funcionario")
public class Funcionario implements java.io.Serializable {

	private String cpf;
	private Estado estado;
	private Cidade cidade;
	private Bairro bairro;
	private String nome;
	private String sobrenome;
	private String sexo;
	private Date dataNascimento;
	private String rg;
	private String rua;
	private String cep;
	private String unidadeImobiliaria;
	private String telefone;
	private String email;
	private String administrador;
	private String senha;
	private Set<PendenciaCadastroImoveis> pendenciaCadastroImoveis = new HashSet<PendenciaCadastroImoveis>(
			0);

	public Funcionario() {
	}

	public Funcionario(String cpf, Estado estado, Cidade cidade, Bairro bairro,
			String nome, String sobrenome, String sexo, String rua, String cep,
			String unidadeImobiliaria, String email, String administrador) {
		this.cpf = cpf;
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.sexo = sexo;
		this.rua = rua;
		this.cep = cep;
		this.unidadeImobiliaria = unidadeImobiliaria;
		this.email = email;
		this.administrador = administrador;
	}

	public Funcionario(String cpf, Estado estado, Cidade cidade, Bairro bairro,
			String nome, String sobrenome, String sexo, Date dataNascimento,
			String rg, String rua, String cep, String unidadeImobiliaria,
			String telefone, String email, String administrador, String senha,
			Set<PendenciaCadastroImoveis> pendenciaCadastroImoveis) {
		this.cpf = cpf;
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.rg = rg;
		this.rua = rua;
		this.cep = cep;
		this.unidadeImobiliaria = unidadeImobiliaria;
		this.telefone = telefone;
		this.email = email;
		this.administrador = administrador;
		this.senha = senha;
		this.pendenciaCadastroImoveis = pendenciaCadastroImoveis;
	}

	@Id
	@Column(name = "CPF", unique = true, nullable = false, length = 15)
	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ESTADO", nullable = false)
	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CIDADE", nullable = false)
	public Cidade getCidade() {
		return this.cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BAIRRO", nullable = false)
	public Bairro getBairro() {
		return this.bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	@Column(name = "NOME", nullable = false, length = 20)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "SOBRENOME", nullable = false, length = 100)
	public String getSobrenome() {
		return this.sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	@Column(name = "SEXO", nullable = false, length = 1)
	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_NASCIMENTO", length = 7)
	public Date getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Column(name = "RG", length = 13)
	public String getRg() {
		return this.rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	@Column(name = "RUA", nullable = false, length = 50)
	public String getRua() {
		return this.rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	@Column(name = "CEP", nullable = false, length = 10)
	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Column(name = "UNIDADE_IMOBILIARIA", nullable = false, length = 50)
	public String getUnidadeImobiliaria() {
		return this.unidadeImobiliaria;
	}

	public void setUnidadeImobiliaria(String unidadeImobiliaria) {
		this.unidadeImobiliaria = unidadeImobiliaria;
	}

	@Column(name = "TELEFONE", length = 14)
	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Column(name = "EMAIL", nullable = false, length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ADMINISTRADOR", nullable = false, length = 1)
	public String getAdministrador() {
		return this.administrador;
	}

	public void setAdministrador(String administrador) {
		this.administrador = administrador;
	}

	@Column(name = "SENHA", length = 20)
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionario")
	public Set<PendenciaCadastroImoveis> getPendenciaCadastroImoveis() {
		return this.pendenciaCadastroImoveis;
	}

	public void setPendenciaCadastroImoveis(
			Set<PendenciaCadastroImoveis> pendenciaCadastroImoveis) {
		this.pendenciaCadastroImoveis = pendenciaCadastroImoveis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Funcionario))
			return false;
		Funcionario other = (Funcionario) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}
	
	

}
