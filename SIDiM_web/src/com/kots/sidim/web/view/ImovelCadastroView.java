package com.kots.sidim.web.view;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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

@ManagedBean(name = "imovelCadastroView")
@ViewScoped
@RequestScoped
public class ImovelCadastroView {

	private Imovel imovel;
	private TipoImovel tipoImovel;
	private Estado estado;
	private Cidade cidade;
	private Bairro bairro;
	private Foto fotoImovel;
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
	private List<String> images;
	private List<String> intencoes;
	private static List<Foto> fotosImovel;
	private static List<byte[]> fotosByte;

	public ImovelCadastroView() {
		if (imovel == null)
			imovel = new Imovel();
		imovelBO = new ImovelBO();
		tipoImovelBO = new TipoImovelBO();
		estadoBO = new EstadoBO();
		cidadeBO = new CidadeBO();
		bairroBO = new BairroBO();
		fotoBO = new FotoBO();
	}

	public Imovel getImovel() {
		return imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}

	public TipoImovel getTipoImovel() {
		return tipoImovel;
	}

	public void setTipoImovel(TipoImovel tipoImovel) {
		this.tipoImovel = tipoImovel;
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

	public Foto getFotoImovel() {
		return fotoImovel;
	}

	public void setFotoImovel(Foto fotoImovel) {
		this.fotoImovel = fotoImovel;
	}

	public StreamedContent getFotoStreamed() {
		return fotoStreamed;
	}

	public void setFotoStreamed(StreamedContent fotoStreamed) {
		this.fotoStreamed = fotoStreamed;
	}

	public ImovelBO getImovelBO() {
		return imovelBO;
	}

	public void setImovelBO(ImovelBO imovelBO) {
		this.imovelBO = imovelBO;
	}

	public TipoImovelBO getTipoImovelBO() {
		return tipoImovelBO;
	}

	public List<Foto> getFotosImovel() {
		return fotosImovel;
	}

	public void setFotosImovel(List<Foto> fotosImovel) {
		this.fotosImovel = fotosImovel;
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
		cidades = cidadeBO.listar(estado);
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public BairroBO getBairroBO() {
		return bairroBO;
	}

	public void setBairroBO(BairroBO bairroBO) {
		this.bairroBO = bairroBO;
	}

	public List<Bairro> getBairros() {
		bairros = bairroBO.listar(cidade);
		return bairros;
	}

	public void setBairros(List<Bairro> bairros) {
		this.bairros = bairros;
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

	// Teste para visualizar imagens no componente do pf
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

	public void handleFileUpload(FileUploadEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = null;
		try {
			fotoStreamed = new DefaultStreamedContent(event.getFile().getInputstream());
			byte[] foto = event.getFile().getContents();
			// this.imovel.setFoto(foto);

			String nomeArquivo = event.getFile().getFileName();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
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
			message = new FacesMessage("Erro interno na aplicação, entre em contato com o suporte do sistema por favor");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage("imovelCadastroForm:cmbConfirmar", message);
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
			message = new FacesMessage("Erro interno na aplicação, entre em contato com o suporte do sistema por favor");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage("imovelCadastroForm:cmbConfirmar", message);
		}
	}

	public String cadastrarImovel() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = null;

		try {
			// Fazer as devidas validações antes da inserção

			imovel.setEstado(estado);
			imovel.setTipoImovel(tipoImovel);
			imovel.setCidade(cidade);
			imovel.setBairro(bairro);
			System.out.println(imovel.getIntencao().substring(0,1));
			imovel.setIntencao(imovel.getIntencao().substring(0,1));
				
			
			imovelBO.salvar(imovel);

			if (fotosImovel != null) {
				for (int i = 0; i < fotosImovel.size(); i++) {
					fotosImovel.get(i).setImovel(imovel);
					fotoBO.salvar(fotosImovel.get(i));
					criaArquivo( fotosByte.get(i) , fotosImovel.get(i).getUrl());
				}
			}

			imovel = new Imovel();
			fotosImovel = null;
			fotosByte = null;
			message = new FacesMessage("Imóvel cadastrado com sucesso.");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			context.addMessage("imovelCadastroForm:cmbConfirmar", message);

		} catch (Exception e) {
			// TODO: handle exception
			message = new FacesMessage("Erro interno na aplicação, entre em contato com o suporte do sistema por favor");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage("imovelCadastroForm:cmbConfirmar", message);
		}

		return null;
	}
}
