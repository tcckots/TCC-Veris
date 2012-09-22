package com.kots.sidim.web.model;

// Generated 27/08/2012 21:21:46 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Configuracao generated by hbm2java
 */
@Entity
@Table(name = "CONFIGURACAO")
@ManagedBean(name = "configuracao")
public class Configuracao implements java.io.Serializable {

	private BigDecimal precoMinimo;

	public Configuracao() {
	}

	public Configuracao(BigDecimal precoMinimo) {
		this.precoMinimo = precoMinimo;
	}

	@Id
	@Column(name = "PRECO_MINIMO", nullable = false, precision = 10)
	public BigDecimal getPrecoMinimo() {
		return this.precoMinimo;
	}

	public void setPrecoMinimo(BigDecimal precoMinimo) {
		this.precoMinimo = precoMinimo;
	}

}
