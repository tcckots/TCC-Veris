package com.kots.sidim.web.model;

// Generated 27/08/2012 21:21:46 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Cidade generated by hbm2java
 */
@Entity
@Table(name = "CIDADE")
@ManagedBean(name = "cidade")
public class Cidade implements java.io.Serializable {

	private int idCidade;
	private Estado estado;
	private String nome;
	private String padrao;
	
	private Set<Imovel> imovels = new HashSet<Imovel>(0);
	private Set<Perfil> perfils = new HashSet<Perfil>(0);
	private Set<Cliente> clientes = new HashSet<Cliente>(0);
	private Set<PendenciaCadastroImoveis> pendenciaCadastroImoveis = new HashSet<PendenciaCadastroImoveis>(0);
	private Set<Funcionario> funcionarios = new HashSet<Funcionario>(0);
	private Set<Bairro> bairros = new HashSet<Bairro>(0);

	public Cidade() {
	}

	public Cidade(int idCidade, Estado estado, String nome, String padrao) {
		this.idCidade = idCidade;
		this.estado = estado;
		this.nome = nome;
		this.padrao = padrao;
	}

	public Cidade(int idCidade, Estado estado, String nome, String padrao,
			Set<Imovel> imovels, Set<Perfil> perfils, Set<Cliente> clientes,
			Set<PendenciaCadastroImoveis> pendenciaCadastroImoveis,
			Set<Funcionario> funcionarios, Set<Bairro> bairros) {
		this.idCidade = idCidade;
		this.estado = estado;
		this.nome = nome;
		this.padrao = padrao;
		
		this.imovels = imovels;
		this.perfils = perfils;
		this.clientes = clientes;
		this.pendenciaCadastroImoveis = pendenciaCadastroImoveis;
		this.funcionarios = funcionarios;
		this.bairros = bairros;
	}

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_CIDADE")
	@SequenceGenerator(name="SEQ_CIDADE", sequenceName = "SEQ_CIDADE")
	@Column(name = "ID_CIDADE", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdCidade() {
		return this.idCidade;
	}

	public void setIdCidade(int idCidade) {
		this.idCidade = idCidade;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UF", nullable = false)
	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Column(name = "NOME", nullable = false, length = 50)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "PADRAO", nullable = false, length = 1)
	public String getPadrao() {
		return this.padrao;
	}

	public void setPadrao(String padrao) {
		this.padrao = padrao;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
	public Set<Imovel> getImovels() {
		return this.imovels;
	}

	public void setImovels(Set<Imovel> imovels) {
		this.imovels = imovels;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
	public Set<Perfil> getPerfils() {
		return this.perfils;
	}

	public void setPerfils(Set<Perfil> perfils) {
		this.perfils = perfils;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
	public Set<Cliente> getClientes() {
		return this.clientes;
	}

	public void setClientes(Set<Cliente> clientes) {
		this.clientes = clientes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
	public Set<PendenciaCadastroImoveis> getPendenciaCadastroImoveis() {
		return this.pendenciaCadastroImoveis;
	}

	public void setPendenciaCadastroImoveis(
			Set<PendenciaCadastroImoveis> pendenciaCadastroImoveis) {
		this.pendenciaCadastroImoveis = pendenciaCadastroImoveis;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
	public Set<Funcionario> getFuncionarios() {
		return this.funcionarios;
	}

	public void setFuncionarios(Set<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
	public Set<Bairro> getBairros() {
		return this.bairros;
	}

	public void setBairros(Set<Bairro> bairros) {
		this.bairros = bairros;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idCidade;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Cidade))
			return false;
		Cidade other = (Cidade) obj;
		if (idCidade != other.idCidade)
			return false;
		return true;
	}

	


}