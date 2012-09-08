package com.kots.sidim.android.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Imovel implements java.io.Serializable {

	private int idImovel;
	private Estado estado;
	private TipoImovel tipoImovel;
	private Cidade cidade;
	private Bairro bairro;
	private short dormitorios;
	private int area;
	private Byte garagens;
	private Short suites;
	private String cep;
	private String rua;
	private BigDecimal preco;
	private String descricao;

	public Imovel() {
	}

	public Imovel(int idImovel, Estado estado, TipoImovel tipoImovel,
			Cidade cidade, Bairro bairro, short dormitorios, int area,
			Byte garagens, Short suites, String cep, String rua,
			BigDecimal preco, String descricao,
			Set<InteresseCliente> interesseClientes) {
		this.idImovel = idImovel;
		this.estado = estado;
		this.tipoImovel = tipoImovel;
		this.cidade = cidade;
		this.bairro = bairro;
		this.dormitorios = dormitorios;
		this.area = area;
		this.garagens = garagens;
		this.suites = suites;
		this.cep = cep;
		this.rua = rua;
		this.preco = preco;
		this.descricao = descricao;
	}

	public int getIdImovel() {
		return this.idImovel;
	}

	public void setIdImovel(int idImovel) {
		this.idImovel = idImovel;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public TipoImovel getTipoImovel() {
		return this.tipoImovel;
	}

	public void setTipoImovel(TipoImovel tipoImovel) {
		this.tipoImovel = tipoImovel;
	}

	public Cidade getCidade() {
		return this.cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Bairro getBairro() {
		return this.bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public short getDormitorios() {
		return this.dormitorios;
	}

	public void setDormitorios(short dormitorios) {
		this.dormitorios = dormitorios;
	}

	public int getArea() {
		return this.area;
	}

	public void setArea(int area) {
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

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getRua() {
		return this.rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public BigDecimal getPreco() {
		return this.preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
