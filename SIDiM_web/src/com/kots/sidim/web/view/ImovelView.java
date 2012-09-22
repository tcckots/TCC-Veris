package com.kots.sidim.web.view;

import java.util.List;

import javax.faces.bean.ManagedBean;

import com.kots.sidim.web.controller.TipoImovelBO;
import com.kots.sidim.web.model.TipoImovel;

@ManagedBean(name = "imovelView")
public class ImovelView {

	public ImovelView() {
		// TODO Auto-generated constructor stub
	}

	private List<TipoImovel> tiposDeImoveis;
	private TipoImovel tipoImovelSelecionado;

	public TipoImovel getTipoImovelSelecionado() {
		return tipoImovelSelecionado;
	}

	public void setTipoImovelSelecionado(TipoImovel tipoImovelSelecionado) {
		this.tipoImovelSelecionado = tipoImovelSelecionado;
	}

	public List<TipoImovel> getTiposDeImoveis() {
		TipoImovelBO bo = new TipoImovelBO();
		this.tiposDeImoveis = bo.listar();
		return tiposDeImoveis;
	}

	public void setTiposDeImoveis(List<TipoImovel> tiposDeImoveis) {
		this.tiposDeImoveis = tiposDeImoveis;
	}

}
