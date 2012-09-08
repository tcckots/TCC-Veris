package com.kots.sidim.android.config;

import com.kots.sidim.android.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class ConfigGlobal {
	
	public static final String[] menuList = {"Home","Pesquisar Imóvel","Favoritos","Perfil","Configurações","Contato"};
	
	public static final int MENU_INDEX_HOME = 0;
	public static final int MENU_INDEX_PESQUISAR_IMOVEL = 1;
	public static final int MENU_INDEX_FAVORITOS = 2;
	public static final int MENU_INDEX_PERFIL = 3;
	public static final int MENU_INDEX_CONFIGURACOES = 4;
	public static final int MENU_INDEX_CONTATO = 5;
	
	
	
	public static final String GLOBAL_SHARED_PREFERENCES        = "preferences_sidim";
	
	public static final String SHARED_PREFERENCES_EMAIL_USER    = "email_user";
	public static final String SHARED_PREFERENCES_SENHA_USER    = "senha_user";
	public static final String SHARED_PREFERENCES_NOME_USER     = "nome_user";
	public static final String SHARED_PREFERENCES_CIDADE_USER   = "cidade_user";
	public static final String SHARED_PREFERENCES_TELEFONE_USER = "telefone_user";
	
	public static final String SHARED_PREFERENCES_LOGIN_AUTO    = "login_auto";
	public static final String SHARED_PREFERENCES_RECEBER_NOTIFIC    = "recebe_notific";
	
	public static final String SHARED_PREFERENCES_SENT_USER_PROFILE    = "sent_user_profile";
	
	
	public static String getUserEmail(Activity activity){
		
		SharedPreferences globalPrefs = activity.getSharedPreferences(ConfigGlobal.GLOBAL_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		return globalPrefs.getString(SHARED_PREFERENCES_EMAIL_USER, "");
			
	}
	
	public static String getUserSenha(Activity activity){
		
		SharedPreferences globalPrefs = activity.getSharedPreferences(ConfigGlobal.GLOBAL_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		return globalPrefs.getString(SHARED_PREFERENCES_SENHA_USER, "");
			
	}
	
	public static int getResourceMenu(int position){
		
		switch(position){
			case MENU_INDEX_HOME: return R.drawable.homemenu;
			case MENU_INDEX_CONTATO: return R.drawable.contactmenu;
			case MENU_INDEX_FAVORITOS: return R.drawable.favormenu;
			case MENU_INDEX_PERFIL: return R.drawable.perfilmenu;
			case MENU_INDEX_PESQUISAR_IMOVEL: return R.drawable.searchmenu;
			case MENU_INDEX_CONFIGURACOES: return R.drawable.configmenu;		
		}
		
		return R.drawable.homemenu;
		
	}
		
	
	

}
