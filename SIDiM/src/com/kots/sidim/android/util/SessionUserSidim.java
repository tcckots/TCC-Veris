package com.kots.sidim.android.util;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.model.Cliente;
public class SessionUserSidim {
        
    public static Map<String,Bitmap> images = new HashMap<String, Bitmap>();

    public static void clearImages(){
        images.clear();
        System.gc();
    }
    
    public static Cliente getContaCliente(Context context){
    	
    	SharedPreferences globalPrefs = context.getSharedPreferences(ConfigGlobal.GLOBAL_SHARED_PREFERENCES, Context.MODE_PRIVATE);    	    	
    	
    	Cliente cliente = new Cliente();
    	cliente.setLogin(globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_EMAIL_USER, ""));
    	cliente.setSenha(globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_SENHA_USER, ""));
    	cliente.setCidade(globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_CIDADE_USER, ""));
    	cliente.setTelefone(globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_TELEFONE_USER, ""));
    	
    	return cliente;
    	
    }
    
    public static void criarContaClienteLocal(Context context, Cliente conta){
    	
    	SharedPreferences globalPrefs = context.getSharedPreferences(ConfigGlobal.GLOBAL_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    	String emailLocal = globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_EMAIL_USER, null);
    	
    	if(emailLocal == null || !emailLocal.equals(conta.getLogin())){
    	
	    	SharedPreferences.Editor editor = globalPrefs.edit();
	    	editor.putString(ConfigGlobal.SHARED_PREFERENCES_EMAIL_USER, conta.getLogin());
	    	editor.putString(ConfigGlobal.SHARED_PREFERENCES_SENHA_USER, conta.getSenha());
	    	editor.putString(ConfigGlobal.SHARED_PREFERENCES_NOME_USER, conta.getNome());
	    	editor.putString(ConfigGlobal.SHARED_PREFERENCES_TELEFONE_USER, conta.getTelefone());
	    	editor.putString(ConfigGlobal.SHARED_PREFERENCES_CIDADE_USER, conta.getCidade());
	    	
	    	editor.commit();
    	}    	    	
    }
               
    
}
