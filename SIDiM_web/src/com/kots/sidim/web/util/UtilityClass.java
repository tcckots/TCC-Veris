package com.kots.sidim.web.util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "utilityClass")
@SessionScoped
public class UtilityClass {

	private String tituloPagina;

	public String getTituloPagina() {
		FacesContext context = FacesContext.getCurrentInstance();
		tituloPagina = context.getAttributes().toString();
		return tituloPagina;
	}

	public void setTituloPagina(String tituloPagina) {

		this.tituloPagina = tituloPagina;
	}

	public String cancelarAcao() {
		return "Home";
	}
}
