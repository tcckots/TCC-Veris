package com.kots.sidim.android.server;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kots.sidim.android.R;
import com.kots.sidim.android.exception.SiDIMException;
import com.kots.sidim.android.model.Bairro;
import com.kots.sidim.android.model.Cidade;
import com.kots.sidim.android.model.Cliente;
import com.kots.sidim.android.model.FiltroImovel;
import com.kots.sidim.android.model.ImovelMobile;
import com.kots.sidim.android.model.InteresseClienteId;
import com.kots.sidim.android.model.ResultWebService;
import com.kots.sidim.android.model.TipoImovelMobile;
import com.kots.sidim.android.util.HttpUtil;

public class SiDIMServerAPI {

	//public static final String BASE_URL_PHOTOS = "http://sidim.no-ip.info/";
	//private static final String URL_SERVER_API = "http://23.22.250.78/sidim/ws/service";
	//public static final String URL_SERVER = "http://10.1.1.3/";
	public  String URL_SERVER = "Sidim";
	public  String URL_SERVER_API = URL_SERVER + "sidim/ws/service";
	
	
	private static Gson GSON = new Gson();
	Type typeOfImovel = new TypeToken<ArrayList<ImovelMobile>>() {}.getType();
	Type typeOfImovelId = new TypeToken<ImovelMobile>() {}.getType();
	Type typeOfCidade = new TypeToken<ArrayList<Cidade>>() {}.getType();
	Type typeOfBairro = new TypeToken<ArrayList<Bairro>>() {}.getType();
	Type typeOfTipo = new TypeToken<ArrayList<TipoImovelMobile>>() {}.getType();
	Type typeContaUsuario = new TypeToken<Cliente>() {}.getType();
	Type typeInteresseCliente = new TypeToken<ResultWebService>() {}.getType();
	Type typeResultAPI = new TypeToken<ResultWebService>() {}.getType();
	Type typeOfT = new TypeToken<Map<String, String>>() {}.getType();

	

	public SiDIMServerAPI(Context context) {
		URL_SERVER = context.getString(R.string.hostserver);
		URL_SERVER_API = URL_SERVER + "sidim/ws/service";
	}

	public boolean criarConta(Cliente conta) throws SiDIMException {

		Cliente cliente = null;
		String url = URL_SERVER_API + "/criarConta";		
		String response = "";
		
        try {
            response = HttpUtil.doHttpPost(url, GSON.toJson(conta));
            cliente = GSON.fromJson(response, typeContaUsuario);                       
            
        } catch (Exception e) {            
            e.printStackTrace();
            throw new SiDIMException("Por favor conecte-se a uma rede para criar uma conta");
        }
        
        if(cliente != null && cliente.isSuccess()){
        	return true;
        } else {
        	if(cliente != null && cliente.getMensagem() != null)
        		throw new SiDIMException(cliente.getMensagem());
        	else
        		throw new SiDIMException("Por favor conecte-se a uma rede para criar uma conta");
        }
				
	}

	public boolean atualizarConta(Cliente conta) throws SiDIMException {
		
		Cliente cliente = null;
		String url = URL_SERVER_API + "/atualizarConta";		
		String response = "";
		
        try {
            response = HttpUtil.doHttpPost(url, GSON.toJson(conta));
            cliente = GSON.fromJson(response, typeContaUsuario);                       
            
        } catch (Exception e) {            
            e.printStackTrace();
        }
        
        if(cliente != null && cliente.isSuccess()){
        	return true;
        } else {
        	if(cliente != null && cliente.getMensagem() != null)
        		throw new SiDIMException(cliente.getMensagem());
        	else
        		throw new SiDIMException("Servidor Indisponível, tente mais tarde");
        }				
	}
	
	public Cliente validarLogin(Cliente conta) throws SiDIMException {
		
		Cliente cliente = null;
		String url = URL_SERVER_API + "/validarLogin";		
		String response = "";
		
        try {
            response = HttpUtil.doHttpPost(url, GSON.toJson(conta));
            cliente = GSON.fromJson(response, typeContaUsuario);                       
            
        } catch (Exception e) {            
            e.printStackTrace();
        }
        
        if(cliente != null && cliente.isSuccess()){
        	return cliente;
        } else {
        	if(cliente != null && cliente.getMensagem() != null)
        		throw new SiDIMException(cliente.getMensagem());
        	else
        		throw new SiDIMException("Servidor Indisponível, tente mais tarde");
        }				
	}

	public List<ImovelMobile> buscarImoveis(FiltroImovel filtro)
			throws SiDIMException {

		
		ArrayList<ImovelMobile> imoveis = null;
		String url = URL_SERVER_API + "/buscarImoveis";		
		String response = "";
		
        try {
            response = HttpUtil.doHttpPost(url, GSON.toJson(filtro));
            imoveis = GSON.fromJson(response, typeOfImovel);                       
            
        } catch (Exception e) {            
            e.printStackTrace();
        }
        
        if(imoveis == null){
        	throw new SiDIMException("Você não está conectado, conecte-se a uma rede");
        } else if (imoveis.size() == 0){
        	throw new SiDIMException("Nenhum Resultado Encontrado");
        } else {
        	return imoveis;
        }

	}

	public boolean enviarInteresse(InteresseClienteId interesse) throws SiDIMException {

		ResultWebService result = null;
		String url = URL_SERVER_API + "/enviarInteresse";		
		String response = "";
		
        try {
            response = HttpUtil.doHttpPost(url,GSON.toJson(interesse));
            result = GSON.fromJson(response, typeInteresseCliente);                       
            
        } catch (Exception e) {            
            e.printStackTrace();
        }
        
        if(result != null && result.isSuccess()){
        	return true;
        } else {
        	if(result != null){
        		throw new SiDIMException(result.getMensagem());
        	} else {
        		throw new SiDIMException("Houve um problema na conexão, tente novamente ou vá em nossa área de contatos e clique em Ligar");
        	}
        	
        }
		
		
	}

	
	public List<ImovelMobile> getRandomImoveis(String cidade) throws SiDIMException {

		ArrayList<ImovelMobile> imoveis = null;
		String url = URL_SERVER_API + "/buscarImoveisRandom?cidade=" + cidade;		
		String response = "";
		
        try {
            response = HttpUtil.doHttpGet(url);
            imoveis = GSON.fromJson(response, typeOfImovel);                       
            
        } catch (Exception e) {            
            e.printStackTrace();
        }
        
        if(imoveis != null && imoveis.size() > 0){
	        return imoveis;
        } else {
        	throw new SiDIMException("Nenhum Resultado Encontrado");
        }		
	}
	
	public List<Cidade> getCidades(String uf) throws SiDIMException {

		ArrayList<Cidade> cidades = null;
		String url = URL_SERVER_API + "/buscarCidades/" + uf;		
		String response = "";
		
        try {
            response = HttpUtil.doHttpGet(url);
            cidades = GSON.fromJson(response, typeOfCidade);                       
            
        } catch (Exception e) {            
            e.printStackTrace();
        }
        
        if(cidades != null && cidades.size() > 0){
	        return cidades;
        } else {
        	throw new SiDIMException("Você não está conectado, conecte-se a uma rede");
        }
		
		
	}

	public ImovelMobile getImovel(int id) throws SiDIMException {

		ImovelMobile imovel = null;
		String url = URL_SERVER_API + "/getImovel/" + id;		
		String response = "";
		
        try {
            response = HttpUtil.doHttpGet(url);
            imovel = GSON.fromJson(response, typeOfImovelId);                       
            
        } catch (Exception e) {            
            e.printStackTrace();
        }
        
        if(imovel != null){
	        return imovel;
        } else {
        	throw new SiDIMException("Você não está conectado, conecte-se a uma rede");
        }
		
		
	}
	
	public boolean enviarSenha(String login) throws SiDIMException {

		ResultWebService trocaSenha = new ResultWebService();
		trocaSenha.setMensagem(login);
		ResultWebService result = null;
		String url = URL_SERVER_API + "/enviarSenha";		
		String response = "";
		
        try {
            response = HttpUtil.doHttpPost(url,GSON.toJson(trocaSenha));
            result = GSON.fromJson(response, typeResultAPI);                       
            
        } catch (Exception e) {            
            e.printStackTrace();
        }
        
        if(result != null && result.isSuccess()){
        	return true;
        } else {
        	if(result != null){
        		throw new SiDIMException(result.getMensagem());
        	} else {
        		throw new SiDIMException("Você não esté conectado a uma rede.");
        	}
        }

	}


	public List<Bairro> getBairro(ResultWebService result) throws SiDIMException {

		ArrayList<Bairro> bairros = null;
		String url = URL_SERVER_API + "/buscarBairros";		
		String response = "";
		
        try {
            response = HttpUtil.doHttpPost(url,GSON.toJson(result));
            bairros = GSON.fromJson(response, typeOfBairro);                       
            
        } catch (Exception e) {            
            e.printStackTrace();
        }
        
        if(bairros != null && bairros.size() > 0){
	        return bairros;
        } else {
        	throw new SiDIMException("Você não esté conectado a uma rede.");
        }
		
	}

	public List<TipoImovelMobile> getTipos() throws SiDIMException {

		ArrayList<TipoImovelMobile> tipos = null;
		String url = URL_SERVER_API + "/buscarTipos";		
		String response = "";
		
        try {
            response = HttpUtil.doHttpGet(url);
            tipos = GSON.fromJson(response, typeOfTipo);                       
            
        } catch (Exception e) {            
            e.printStackTrace();
        }
        
        if(tipos != null && tipos.size() > 0){
	        return tipos;
        } else {
        	throw new SiDIMException("Nenhum Resultado Encontrado");
        }
				
	}

	

}
