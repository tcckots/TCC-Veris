package com.kots.sidim.android.model;

import java.util.ArrayList;

public class FiltroImovelWeb  {

	private String uf;
	private String cidade;
	private ArrayList<String> idsBairro;
	private ArrayList<Integer> idsTipo;
	private int intencao;
	private int faixaPreco;
	private int qtdDorm;
	private int qtdSuite;
	private int qtdGaragens;
	
	private int indexBuscas;
	
	public int getIndexBuscas() {
		return indexBuscas;
	}
	public void setIndexBuscas(int indexBuscas) {
		this.indexBuscas = indexBuscas;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public ArrayList<String> getIdsBairro() {
		return idsBairro;
	}
	public void setIdsBairro(ArrayList<String> idsBairro) {
		this.idsBairro = idsBairro;
	}
	public ArrayList<Integer> getIdsTipo() {
		return idsTipo;
	}
	public void setIdsTipo(ArrayList<Integer> idsTipo) {
		this.idsTipo = idsTipo;
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
