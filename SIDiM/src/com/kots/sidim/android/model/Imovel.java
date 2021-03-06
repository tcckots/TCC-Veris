package com.kots.sidim.android.model;

// Generated 13/09/2012 19:59:14 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;


@SuppressWarnings("serial")
public class Imovel implements java.io.Serializable {
	
	private int idImovel;
	private Byte dormitorios;
	private Double areaTotal;
	private Double areaConstruida;
	private Byte garagens;
	private Byte suites;
	private String cep;
	private String rua;
	private Estado estado;
	private TipoImovel tipoImovel;
	private Cidade cidade;
	private Bairro bairro;
	private BigDecimal preco;
	private String descricao;
	private String intencao;


	public Imovel() {
	}

	public Imovel(int idImovel, TipoImovel tipoImovel, Byte dormitorios, Double areaTotal, Double areaConstruida, String cep, String rua, Estado estado, Cidade cidade, Bairro bairro,
			BigDecimal preco) {
		this.idImovel = idImovel;
		this.tipoImovel = tipoImovel;
		this.dormitorios = dormitorios;
		this.areaConstruida = areaConstruida;
		this.areaTotal = areaTotal;
		this.cep = cep;
		this.rua = rua;
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.preco = preco;
	}

	public Imovel(int idImovel, TipoImovel tipoImovel, Byte dormitorios,Double areaTotal, Double areaConstruida, Byte garagens, Byte suites, String cep, String rua, Estado estado,
			Cidade cidade, Bairro bairro, BigDecimal preco, String descricao, String intencao
			//, Set<Foto> fotos,Set<InteresseCliente> interesseClientes
			) {
		this.idImovel = idImovel;
		this.tipoImovel = tipoImovel;
		this.dormitorios = dormitorios;
		this.areaConstruida = areaConstruida;
		this.areaTotal = areaTotal;
		this.garagens = garagens;
		this.suites = suites;
		this.cep = cep;
		this.rua = rua;
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.preco = preco;
		this.descricao = descricao;
		this.intencao = intencao;
//		this.fotos = fotos;
//		this.interesseClientes = interesseClientes;
	}

	
	public int getIdImovel() {
		return this.idImovel;
	}

	public void setIdImovel(int idImovel) {
		this.idImovel = idImovel;
	}

	
	public Byte getDormitorios() {
		return this.dormitorios;
	}

	public void setDormitorios(Byte dormitorios) {
		this.dormitorios = dormitorios;
	}

	
	public Double getAreaTotal() {
		return this.areaTotal;
	}

	public void setAreaTotal(Double areaTotal) {
		this.areaTotal = areaTotal;
	}
	
	
	public Double getAreaConstruida() {
		return this.areaConstruida;
	}

	public void setAreaConstruida(Double areaConstruida) {
		this.areaConstruida = areaConstruida;
	}


	public Byte getGaragens() {
		return this.garagens;
	}

	public void setGaragens(Byte garagens) {
		this.garagens = garagens;
	}

	
	public Byte getSuites() {
		return this.suites;
	}

	public void setSuites(Byte suites) {
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

	
	public String getIntencao() {
		return this.intencao;
	}

	public void setIntencao(String intencao) {
		this.intencao = intencao;
	}

	
	
}
