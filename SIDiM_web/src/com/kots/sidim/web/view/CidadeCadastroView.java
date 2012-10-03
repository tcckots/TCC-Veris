package com.kots.sidim.web.view;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;
import com.kots.sidim.web.controller.CidadeBO;
import com.kots.sidim.web.controller.EstadoBO;
import com.kots.sidim.web.model.Cidade;
import com.kots.sidim.web.model.Estado;

@ManagedBean(name = "cidadeCadastroView")
@ViewScoped
public class CidadeCadastroView implements Serializable {

	public CidadeCadastroView() {
		if (cidade == null)
			cidade = new Cidade();
		estadoBO = new EstadoBO();
		cidadeBO = new CidadeBO();
	}

	private Estado estado;
	private Cidade cidade;
	private EstadoBO estadoBO;
	private CidadeBO cidadeBO;
	private List<Estado> estados;

	public List<Estado> getEstados() {
		if (estados == null)
			estados = estadoBO.listar();
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
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

	public String cadastrarCidade() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = null;

		try {
			// Se a cidade nao existir salva no banco
			if (!cidadeBO.verificaCidadeExiste(cidade.getNome(), estado)) {
				if (estado != null)
					cidade.setEstado(estado);
				cidade.setPadrao("N");
				cidadeBO.salvar(cidade);
				cidade = new Cidade();
				message = new FacesMessage("Cidade cadastrada com sucesso!");
				message.setSeverity(FacesMessage.SEVERITY_INFO);
				context.addMessage("cidadeCadastroForm:cmbConfirmar", message);

			} else {
				message = new FacesMessage("Essa cidade já está cadastrada!");
				message.setSeverity(FacesMessage.SEVERITY_WARN);
				context.addMessage("cidadeCadastroForm:cmbConfirmar", message);

			}
		} catch (Exception e) {
			// TODO: handle exception
			message = new FacesMessage("Erro interno na aplicação, entre em contato com o suporte do sistema por favor");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage("cidadeCadastroForm:cmbConfirmar", message);
		}

		return null;
	}
}
