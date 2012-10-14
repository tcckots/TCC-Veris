package com.kots.sidim.android.server;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;

import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.exception.SiDIMException;
import com.kots.sidim.android.model.Bairro;
import com.kots.sidim.android.model.Cidade;
import com.kots.sidim.android.model.Cliente;
import com.kots.sidim.android.model.Estado;
import com.kots.sidim.android.model.FiltroImovel;
import com.kots.sidim.android.model.ImovelMobile;
import com.kots.sidim.android.model.InteresseClienteId;
import com.kots.sidim.android.model.TipoImovel;
import com.kots.sidim.android.model.TipoImovelMobile;


public class SiDIMControllerServer {
	
	@SuppressWarnings("unused")
	private Context context;
	SharedPreferences globalPrefs;
	private static SiDIMControllerServer instance;
	SiDIMServerAPI sidimAPI;
		
	
	
	private SiDIMControllerServer(Context context){ 
		this.context = context;
		globalPrefs = context.getSharedPreferences(ConfigGlobal.GLOBAL_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		sidimAPI = new SiDIMServerAPI(context);
	}
	
	public static SiDIMControllerServer getInstance(Context context){
		
		if(instance == null){
			instance = new SiDIMControllerServer(context);
		}
		
		return instance;
		
	}
	
	public Cliente validarLogin(Cliente conta) throws SiDIMException{
		
		return sidimAPI.validarLogin(conta);
	}
	
	public boolean criarConta(Cliente conta) throws SiDIMException{
				
		//return sidimAPI.criarConta(conta);
		return true;
	}
	
	public boolean atualizarConta(Cliente conta) throws SiDIMException{		
		
		return sidimAPI.atualizarConta(conta);
	}
	
	public List<ImovelMobile> buscarImoveis(FiltroImovel filtro) throws SiDIMException{
		
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<ImovelMobile> imoveis = new ArrayList<ImovelMobile>();
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		
		return imoveis;
		
		//return sidimAPI.buscarImoveis(filtro);
		
	}
	
	public boolean enviarInteresse(InteresseClienteId interesse) throws SiDIMException{
		
		return sidimAPI.enviarInteresse(interesse);
		
	}
	
	public boolean enviarSenha(String login) throws SiDIMException {
		
		return sidimAPI.enviarSenha(login);
	}
	
	public List<ImovelMobile> getRandomImoveis(String cidade) throws SiDIMException{
		
		return sidimAPI.getRandomImoveis(cidade);
	}
	
	public List<String> getCidades(String uf) throws SiDIMException{
		
//		ArrayList<Cidade> cidades = new ArrayList<Cidade>();
//		cidades.add(new Cidade(1, new Estado("SP", "São Paulo"), "Campinas", "N"));
//		cidades.add(new Cidade(1, new Estado("SP", "São Paulo"), "Campina Grande", "N"));
//		cidades.add(new Cidade(1, new Estado("SP", "São Paulo"), "Campina do Sul", "N"));
//		cidades.add(new Cidade(1, new Estado("SP", "São Paulo"), "Campos do Jordao", "N"));
		
		
		List<Cidade> cidades = sidimAPI.getCidades(uf);
		List<String> stringCidades = new ArrayList<String>();
		for(Cidade cidade : cidades){
			stringCidades.add(cidade.getNome());
		}
		
		
		return stringCidades;
		//return cidades;
	}
	
	public List<String> getBairro(String cidade) throws SiDIMException{
		
		List<String> sBairros = new ArrayList<String>();
		List<Bairro> bairros = sidimAPI.getBairro(cidade);
		
		for(Bairro item : bairros){
			sBairros.add(item.getNome());
		}
		
		return sBairros;
	}
	
	public List<TipoImovelMobile> getTipos() throws SiDIMException{
		
		//return sidimAPI.getTipos();
		
		List<TipoImovelMobile> tipos = new ArrayList<TipoImovelMobile>();
		tipos.add(new TipoImovelMobile((short) 1, "Apartamento", true));
		tipos.add(new TipoImovelMobile((short) 2, "Casa", false));
		tipos.add(new TipoImovelMobile((short) 3, "Ponto", false));
		tipos.add(new TipoImovelMobile((short) 4, "Terreno", false));
		
		
		return tipos;
		
	}
	
	
	 public ImovelMobile getNewImovel(){
			
		 ImovelMobile imovel = new ImovelMobile();
		 imovel.setIdImovel(1);
		 imovel.setEstado(new Estado("SP","São Paulo"));
		 imovel.setCidade(new Cidade(1, new Estado("SP","São Paulo"), "Sumaré",
		 ""));
		 imovel.setAreaConstruida(40.0);
		 imovel.setAreaTotal(80.0);
		 imovel.setDormitorios((byte)3);
		 imovel.setSuites((byte) 2);
		 imovel.setGaragens((byte) 1);
		 imovel.setBairro(new Bairro(1, null, "Jd. Amanda", ""));
		 imovel.setDescricao("Linda Casa");
		 imovel.setPreco(new BigDecimal(130000));
		 imovel.setTipoImovel(new TipoImovel((short) 1, "Casa"));
		 imovel.setIntencao("C");
		 imovel.setDescricao("Ótima localização, casa linda impecável, acabou de ser reformada");
		 
		 List<String> images = new ArrayList<String>();
		 images.add("http://www.centrina.com.br/fotos/25/1741891.jpg");
		 images.add("http://www.centrina.com.br/fotos/25/1741892.jpg");
		 images.add("http://www.centrina.com.br/fotos/25/1741893.jpg");
		 images.add("http://www.centrina.com.br/fotos/25/1741882.jpg");
		 images.add("http://www.centrina.com.br/fotos/25/1738968.jpg");
		 images.add("http://i.imovelweb.com.br/725264/3273618/550x412_1_6f17a05e84baeb2db247fbcc3033f695.jpg");
		 images.add("http://i.imovelweb.com.br/725264/3273618/550x412_1_1570af0229b294a7376fed7aa6618427.jpg");
		 images.add("http://i.imovelweb.com.br/725264/3273618/550x412_1_0940776368976764c9b2db06ce60d595.jpg");
		 images.add("http://i.imovelweb.com.br/725264/3273618/550x412_1_0e15bc0d91038cc97fbd093942d5d134.jpg");
		 images.add("http://i.imovelweb.com.br/725264/3273618/550x412_1_4c00776c9f46daf14a0fd49d3c2159d1.jpg");
		 
		 imovel.setFotos(images);
		 
		
		 return imovel;
		
		 }

}
