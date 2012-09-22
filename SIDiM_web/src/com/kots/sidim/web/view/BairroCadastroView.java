package com.kots.sidim.web.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import com.kots.sidim.web.controller.BairroBO;
import com.kots.sidim.web.controller.CidadeBO;
import com.kots.sidim.web.controller.EstadoBO;
import com.kots.sidim.web.model.Bairro;
import com.kots.sidim.web.model.Cidade;
import com.kots.sidim.web.model.Estado;

;

@ManagedBean(name = "bairroCadastroView")
@ViewScoped
public class BairroCadastroView {

	private Estado estado;
	private Cidade cidade;
	private Bairro bairro;
	private EstadoBO estadoBO;
	private CidadeBO cidadeBO;
	private BairroBO bairroBO;
	private List<Estado> estados;
	private List<Cidade> cidades;

	public BairroCadastroView() {
		if (bairro == null)
			bairro = new Bairro();
		estadoBO = new EstadoBO();
		cidadeBO = new CidadeBO();
		bairroBO = new BairroBO();
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

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
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

	public BairroBO getBairroBO() {
		return bairroBO;
	}

	public void setBairroBO(BairroBO bairroBO) {
		this.bairroBO = bairroBO;
	}

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

	public String cadastrarBairro() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = null;
		try {

			if (!bairroBO.verificarBairroExiste(bairro.getNome(), cidade)) {
				cidade.setEstado(estado);
				bairro.setCidade(cidade);
				bairro.setPadrao("N");
				bairroBO.salvar(bairro);
				bairro = new Bairro();
				message = new FacesMessage("Bairro cadastrado com sucesso!");
				message.setSeverity(FacesMessage.SEVERITY_INFO);
				context.addMessage("bairroCadastroForm:cmbConfirmar", message);

			} else {
				message = new FacesMessage("Esse bairro já está cadastrado!");
				message.setSeverity(FacesMessage.SEVERITY_WARN);
				context.addMessage("bairroCadastroForm:cmbConfirmar", message);
			}

		} catch (Exception e) {
			// TODO: handle exception
			message = new FacesMessage("Erro interno na aplicação, entre em contato com o suporte do sistema por favor");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage("bairroCadastroForm:cmbConfirmar", message);
		}

		return null;
	}

}
