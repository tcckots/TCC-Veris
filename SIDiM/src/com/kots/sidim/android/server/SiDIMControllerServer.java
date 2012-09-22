package com.kots.sidim.android.server;

import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.exception.SiDIMException;
import com.kots.sidim.android.model.Bairro;
import com.kots.sidim.android.model.Cidade;
import com.kots.sidim.android.model.Cliente;
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
	
	public boolean criarConta(Cliente conta) throws SiDIMException{
				
		return sidimAPI.criarConta(conta);
	}
	
	public boolean atualizarConta(Cliente conta) throws SiDIMException{		
		
		return sidimAPI.atualizarConta(conta);
	}
	
	public List<Imovel> buscarImoveis(FiltroImovel filtro) throws SiDIMException{
		
		return sidimAPI.buscarImoveis(filtro);
	}
	
	public boolean enviarInteresse(InteresseCliente “nteresse) throws SiDIMException{
		
		return sidimAPI.enviarInteresse(“nteresse);
		
	}
	
	public boolean enviarSenha(String login) throws SiDIMException {
		
		return sidimAPI.enviarSenha(login);
	}
	
	public List<Imovel> getRandomImoveis(String cidade) throws SiDIMException{
		
		return sidimAPI.getRandomImoveis(cidade);
	}
	
	public List<Cidade> getCidades(String uf) throws SiDIMException{
		
		return sidimAPI.getCidades(uf);
	}
	
	public List<Bairro> getBairro(String cidade) throws SiDIMException{
		
		return sidimAPI.getBairro(cidade);
	}
	
	public List<TipoImovelMobile> getTipos() throws SiDIMException{
		
		return sidimAPI.getTipos();
		
	}
	
	
	

}
