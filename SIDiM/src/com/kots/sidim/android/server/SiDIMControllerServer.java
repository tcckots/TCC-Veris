package com.kots.sidim.android.server;

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
import com.kots.sidim.android.model.Imovel;
import com.kots.sidim.android.model.InteresseCliente;
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
				
		return sidimAPI.criarConta(conta);
	}
	
	public boolean atualizarConta(Cliente conta) throws SiDIMException{		
		
		return sidimAPI.atualizarConta(conta);
	}
	
	public List<Imovel> buscarImoveis(FiltroImovel filtro) throws SiDIMException{
		
		return sidimAPI.buscarImoveis(filtro);
	}
	
	public boolean enviarInteresse(InteresseCliente interesse) throws SiDIMException{
		
		return sidimAPI.enviarInteresse(interesse);
		
	}
	
	public boolean enviarSenha(String login) throws SiDIMException {
		
		return sidimAPI.enviarSenha(login);
	}
	
	public List<Imovel> getRandomImoveis(String cidade) throws SiDIMException{
		
		return sidimAPI.getRandomImoveis(cidade);
	}
	
	public List<String> getCidades(String uf) throws SiDIMException{
		
//		ArrayList<Cidade> cidades = new ArrayList<Cidade>();
//		cidades.add(new Cidade(1, new Estado("SP", "S‹o Paulo"), "Campinas", "N"));
//		cidades.add(new Cidade(1, new Estado("SP", "S‹o Paulo"), "Campina Grande", "N"));
//		cidades.add(new Cidade(1, new Estado("SP", "S‹o Paulo"), "Campina do Sul", "N"));
//		cidades.add(new Cidade(1, new Estado("SP", "S‹o Paulo"), "Campos do Jordao", "N"));
		
		
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
	
	
	

}
