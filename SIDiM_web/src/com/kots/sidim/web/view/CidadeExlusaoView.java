package com.kots.sidim.web.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;
import com.kots.sidim.web.controller.CidadeBO;
import com.kots.sidim.web.controller.EstadoBO;
import com.kots.sidim.web.model.Cidade;
import com.kots.sidim.web.model.Estado;

@ManagedBean(name = "cidadeExclusaoView")
@ViewScoped
public class CidadeExlusaoView {

	public CidadeExlusaoView() {
		estadoBO = new EstadoBO();
		cidadeBO = new CidadeBO();
	}

	private Estado estado;
	private Cidade cidade;
	private EstadoBO estadoBO;
	private CidadeBO cidadeBO;
	private List<Estado> estados;
	private List<Cidade> cidades;

	public List<Estado> getEstados() {
		if (estados == null)
			estados = estadoBO.listar();
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Cidade> getCidades() {
		cidades = cidadeBO.listar(estado);
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public EstadoBO getEstadoBO() {
		return estadoBO;
	}

	public void setEstadoBO(EstadoBO estadoBO) {
		this.estadoBO = estadoBO;
	}

	public CidadeBO getCidadeBO() {
		return cidadeBO;
	}

	public void setCidadeBO(CidadeBO cidadeBO) {
		this.cidadeBO = cidadeBO;
	}

	public String removerCidade() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = null;
		try {
			cidadeBO.excluir(cidade);
			message = new FacesMessage("Cidade excluída com sucesso");
			message.setSeverity(FacesMessage.SEVERITY_WARN);
			context.addMessage("cidadeExclusaoForm:cmbRemover", message);

		} catch (Exception e) {
			// TODO: handle exception
			message = new FacesMessage("Erro interno na aplicação, entre em contato com o suporte do sistema por favor");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage("cidadeExclusaoForm:cmbRemover", message);
		}

		return null;
	}

}
