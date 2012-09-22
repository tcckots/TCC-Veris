package com.kots.sidim.android.model;

public class FiltroImovel extends ResultSidimAPI {
	
	private String cidade;
	private String estado;
	private int[] idsCidades;
	private int[] idsTipos;
	private int intencao;
	private int faixaPreco;
	private int qtdDorm;
	private int qtdSuite;
	private int qtdGaragens;
	
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int[] getIdsCidades() {
		return idsCidades;
	}
	public void setIdsCidades(int[] idsCidades) {
		this.idsCidades = idsCidades;
	}
	public int[] getIdsTipos() {
		return idsTipos;
	}
	public void setIdsTipos(int[] idsTipos) {
		this.idsTipos = idsTipos;
	}
	public int getIntencao() {
		return intencao;
	}
	public void setIntencao(int intencao) {
		this.intencao = intencao;
	}
	public int getFaixaPreco() {
		return faixaPreco;
	}
	public void setFaixaPreco(int faixaPreco) {
		this.faixaPreco = faixaPreco;
	}
	public int getQtdDorm() {
		return qtdDorm;
	}
	public void setQtdDorm(int qtdDorm) {
		this.qtdDorm = qtdDorm;
	}
	public int getQtdSuite() {
		return qtdSuite;
	}
	public void setQtdSuite(int qtdSuite) {
		this.qtdSuite = qtdSuite;
	}
	public int getQtdGaragens() {
		return qtdGaragens;
	}
	public void setQtdGaragens(int qtdGaragens) {
		this.qtdGaragens = qtdGaragens;
	}
	
	
	

}
