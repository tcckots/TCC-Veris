package com.kots.sidim.android.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


public class Perfil implements java.io.Serializable {

	private int idPerfil;
	private Estado estado;
	private Cidade cidade;
	private Cliente cliente;
	private Short dormitorios;
	private Integer area;
	private Byte garagens;
	private Short suites;
	private String rua;
	private BigDecimal valorMinimo;
	private BigDecimal valorMaximo;
	private String intencao;
	private Set<Bairro> bairros = new HashSet<Bairro>(0);
	private Set<TipoImovel> tipoImovels = new HashSet<TipoImovel>(0);

	public Perfil() {
	}

	public Perfil(int idPerfil, Cliente cliente) {
		this.idPerfil = idPerfil;
		this.cliente = cliente;
	}

	public Perfil(int idPerfil, Estado estado, Cidade cidade, Cliente cliente, Short dormitorios, Integer area, Byte garagens, Short suites, String rua,
			BigDecimal valorMinimo, BigDecimal valorMaximo, String intencao, Set<Bairro> bairros, Set<TipoImovel> tipoImovels) {
		this.idPerfil = idPerfil;
		this.estado = estado;
		this.cidade = cidade;
		this.cliente = cliente;
		this.dormitorios = dormitorios;
		this.area = area;
		this.garagens = garagens;
		this.suites = suites;
		this.rua = rua;
		this.valorMinimo = valorMinimo;
		this.valorMaximo = valorMaximo;
		this.intencao = intencao;
		this.bairros = bairros;
		this.tipoImovels = tipoImovels;
	}

	
	public int getIdPerfil() {
		return this.idPerfil;
	}

	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}

	
	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	
	public Cidade getCidade() {
		return this.cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	
	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
	public Short getDormitorios() {
		return this.dormitorios;
	}

	public void setDormitorios(Short dormitorios) {
		this.dormitorios = dormitorios;
	}

	
	public Integer getArea() {
		return this.area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	
	public Byte getGaragens() {
		return this.garagens;
	}

	public void setGaragens(Byte garagens) {
		this.garagens = garagens;
	}

	
	public Short getSuites() {
		return this.suites;
	}

	public void setSuites(Short suites) {
		this.suites = suites;
	}

	
	public String getRua() {
		return this.rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	
	public BigDecimal getValorMinimo() {
		return this.valorMinimo;
	}

	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	
	public BigDecimal getValorMaximo() {
		return this.valorMaximo;
	}

	public void setValorMaximo(BigDecimal valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	
	public String getIntencao() {
		return this.intencao;
	}

	public void setIntencao(String intencao) {
		this.intencao = intencao;
	}


	public Set<Bairro> getBairros() {
		return this.bairros;
	}

	public void setBairros(Set<Bairro> bairros) {
		this.bairros = bairros;
	}

	
	
	public Set<TipoImovel> getTipoImovels() {
		return this.tipoImovels;
	}

	public void setTipoImovels(Set<TipoImovel> tipoImovels) {
		this.tipoImovels = tipoImovels;
	}

}
