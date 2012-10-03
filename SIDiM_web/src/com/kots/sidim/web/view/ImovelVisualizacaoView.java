package com.kots.sidim.web.view;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.kots.sidim.web.controller.BairroBO;
import com.kots.sidim.web.controller.CidadeBO;
import com.kots.sidim.web.controller.EstadoBO;
import com.kots.sidim.web.controller.FotoBO;
import com.kots.sidim.web.controller.ImovelBO;
import com.kots.sidim.web.controller.TipoImovelBO;
import com.kots.sidim.web.model.Bairro;
import com.kots.sidim.web.model.Cidade;
import com.kots.sidim.web.model.Estado;
import com.kots.sidim.web.model.Foto;
import com.kots.sidim.web.model.Imovel;
import com.kots.sidim.web.model.TipoImovel;

@ManagedBean(name = "imovelVisualizacaoView")
@ViewScoped
@RequestScoped
public class ImovelVisualizacaoView {

	public ImovelVisualizacaoView() {

		imovelBO = new ImovelBO();
		tipoImovelBO = new TipoImovelBO();
		estadoBO = new EstadoBO();
		cidadeBO = new CidadeBO();
		bairroBO = new BairroBO();
		fotoBO = new FotoBO();
	}

	private List<Imovel> imoveis;
	private Imovel imovelSelecionado;
	private List<String> images;
	private ImovelBO imovelBO;
	private FotoBO fotoBO;
	private TipoImovelBO tipoImovelBO;
	private EstadoBO estadoBO;
	private CidadeBO cidadeBO;
	private BairroBO bairroBO;
	private List<TipoImovel> tiposDeImoveis;
	private List<Estado> estados;
	private List<Cidade> cidades;
	private List<Bairro> bairros;
	private StreamedContent fotoStreamed;
	private UploadedFile file;
	private List<String> intencoes;
	private static List<Foto> fotosImovel;
	private static List<byte[]> fotosByte;
	private Foto fotoImovel;

	public List<Imovel> getImoveis() {
		if (imoveis == null)
			imoveis = imovelBO.listar();
		return imoveis;
	}

	public void setImoveis(List<Imovel> imoveis) {
		this.imoveis = imoveis;
	}

	public Imovel getImovelSelecionado() {
		if (imovelSelecionado != null) {
			if (imovelSelecionado.getIntencao() != null) {
				if (imovelSelecionado.getIntencao().equals("A")) {
					imovelSelecionado.setIntencao("Aluguel");
				} else if (imovelSelecionado.getIntencao().equals("C")) {
					imovelSelecionado.setIntencao("Compra");
				}
			}
		}
		return imovelSelecionado;
	}

	public void setImovelSelecionado(Imovel imovelSelecionado) {
		this.imovelSelecionado = imovelSelecionado;
	}

	public List<String> getImages() {
		if (images == null) {
			images = new ArrayList<String>();

			for (int i = 1; i <= 4; i++) {
				images.add("imovelExemplo" + i + ".jpg");
			}
		}
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public ImovelBO getImovelBO() {
		return imovelBO;
	}

	public void setImovelBO(ImovelBO imovelBO) {
		this.imovelBO = imovelBO;
	}

	public FotoBO getFotoBO() {
		return fotoBO;
	}

	public void setFotoBO(FotoBO fotoBO) {
		this.fotoBO = fotoBO;
	}

	public TipoImovelBO getTipoImovelBO() {
		return tipoImovelBO;
	}

	public void setTipoImovelBO(TipoImovelBO tipoImovelBO) {
		this.tipoImovelBO = tipoImovelBO;
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

	public List<TipoImovel> getTiposDeImoveis() {
		if (tiposDeImoveis == null)
			tiposDeImoveis = tipoImovelBO.listar();
		return tiposDeImoveis;
	}

	public void setTiposDeImoveis(List<TipoImovel> tiposDeImoveis) {
		this.tiposDeImoveis = tiposDeImoveis;
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
//		if (imovelSelecionado.getEstado() != null) {
//			cidades = cidadeBO.listar(imovelSelecionado.getEstado());
//		}
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public List<Bairro> getBairros() {
//		if (imovelSelecionado.getCidade() != null) {
//			bairros = bairroBO.listar(imovelSelecionado.getCidade());
//		}
		return bairros;
	}

	public void setBairros(List<Bairro> bairros) {
		this.bairros = bairros;
	}

	public StreamedContent getFotoStreamed() {
		return fotoStreamed;
	}

	public void setFotoStreamed(StreamedContent fotoStreamed) {
		this.fotoStreamed = fotoStreamed;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public List<String> getIntencoes() {
		if (intencoes == null) {
			intencoes = new ArrayList<String>();
			intencoes.add("Aluguel");
			intencoes.add("Compra");
		}
		return intencoes;
	}

	public void setIntencoes(List<String> intencoes) {
		this.intencoes = intencoes;
	}

	public static List<Foto> getFotosImovel() {
		return fotosImovel;
	}

	public static void setFotosImovel(List<Foto> fotosImovel) {
		ImovelVisualizacaoView.fotosImovel = fotosImovel;
	}

	public static List<byte[]> getFotosByte() {
		return fotosByte;
	}

	public static void setFotosByte(List<byte[]> fotosByte) {
		ImovelVisualizacaoView.fotosByte = fotosByte;
	}

	public Foto getFotoImovel() {
		return fotoImovel;
	}

	public void setFotoImovel(Foto fotoImovel) {
		this.fotoImovel = fotoImovel;
	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = null;
		try {
			fotoStreamed = new DefaultStreamedContent(event.getFile()
					.getInputstream());
			byte[] foto = event.getFile().getContents();
			// this.imovel.setFoto(foto);

			String nomeArquivo = event.getFile().getFileName();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ServletContext scontext = (ServletContext) facesContext
					.getExternalContext().getContext();
			String arquivo = "C:\\fotosSidim\\" + nomeArquivo;
			fotoImovel = new Foto();
			fotoImovel.setUrl(arquivo);
			if (fotosImovel == null)
				fotosImovel = new ArrayList<Foto>();
			if (fotosByte == null)
				fotosByte = new ArrayList<byte[]>();
			fotosImovel.add(fotoImovel);
			fotosByte.add(foto);

		} catch (Exception e) {
			// TODO: handle exception
			message = new FacesMessage(
					"Erro interno na aplicação, entre em contato com o suporte do sistema por favor");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage("ImovelVisualizacaoForm:cmbConfirmar", message);
		}
	}

	public void criaArquivo(byte[] bytes, String arquivo) throws IOException {
		FileOutputStream fos;
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = null;
		try {
			fos = new FileOutputStream(arquivo);
			fos.write(bytes);
			fos.close();
		} catch (FileNotFoundException ex) {
			// TODO: handle exception
			message = new FacesMessage(
					"Erro interno na aplicação, entre em contato com o suporte do sistema por favor");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage("ImovelVisualizacaoForm:cmbConfirmar", message);
		}
	}

	private SelectItem[] createFilterOptions(String[] data) {
		SelectItem[] options = new SelectItem[data.length + 1];

		options[0] = new SelectItem("", "Selecione");
		for (int i = 0; i < data.length; i++) {
			options[i + 1] = new SelectItem(data[i], data[i]);
		}

		return options;
	}

	public String atualizarImovel() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = null;

		try {

		} catch (Exception e) {
			// TODO: handle exception
			message = new FacesMessage(
					"Erro interno na aplicação, entre em contato com o suporte do sistema por favor");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage("ImovelVisualizacaoForm:cmbConfirmar", message);
		}

		return null;
	}

}
