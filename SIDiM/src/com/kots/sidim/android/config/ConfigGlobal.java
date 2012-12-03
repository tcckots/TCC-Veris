package com.kots.sidim.android.config;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.kots.sidim.android.R;
import com.kots.sidim.android.server.SiDIMServerAPI;

public class ConfigGlobal {
			
	public static final String MENU_HOME = "Home";
	public static final String MENU_PESQUISAR_IMOVEL = "Pesquisar Imóvel";
	public static final String MENU_FAVORITOS = "Favoritos";	
	public static final String MENU_CONFIGURACOES = "Configurações";
	public static final String MENU_CONTATO = "Contato";
	
	public static final int MENU_INDEX_HOME = 0;
	public static final int MENU_INDEX_PESQUISAR_IMOVEL = 1;
	public static final int MENU_INDEX_FAVORITOS = 2;
	public static final int MENU_INDEX_CONFIGURACOES = 3;
	public static final int MENU_INDEX_CONTATO = 4;
	
	
	public static  String BASE_URL_PHOTOS;
	
public static final String[] menuList = {MENU_HOME,MENU_PESQUISAR_IMOVEL,MENU_FAVORITOS,MENU_CONFIGURACOES,MENU_CONTATO};
	
	public static final String[] menuPrincipalList = {MENU_PESQUISAR_IMOVEL,MENU_FAVORITOS,MENU_CONFIGURACOES,MENU_CONTATO};
	
	public static Map<String,Integer> resourcesImages = new HashMap<String, Integer>();
	
	
	public static final String GLOBAL_SHARED_PREFERENCES        = "preferences_sidim";
	
	public static final String SHARED_PREFERENCES_EMAIL_USER    = "email_user";
	public static final String SHARED_PREFERENCES_SENHA_USER    = "senha_user";
	public static final String SHARED_PREFERENCES_NOME_USER     = "nome_user";
	public static final String SHARED_PREFERENCES_CIDADE_USER   = "cidade_user";
	public static final String SHARED_PREFERENCES_TELEFONE_USER = "telefone_user";
	
	public static final String SHARED_PREFERENCES_LOGIN_AUTO    = "login_auto";
	public static final String SHARED_PREFERENCES_RECEBER_NOTIFIC    = "recebe_notific";
	
	public static final String SHARED_PREFERENCES_SENT_USER_PROFILE    = "sent_user_profile";
	
	public static final String SHARED_PREFERENCES_LAST_SEARCH_BAIRROS    = "last_search_bairros";
	
	
	public static String getUserEmail(Activity activity){
		
		SharedPreferences globalPrefs = activity.getSharedPreferences(ConfigGlobal.GLOBAL_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		return globalPrefs.getString(SHARED_PREFERENCES_EMAIL_USER, "");
			
	}
	
	public static String getUserSenha(Activity activity){
		
		SharedPreferences globalPrefs = activity.getSharedPreferences(ConfigGlobal.GLOBAL_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		return globalPrefs.getString(SHARED_PREFERENCES_SENHA_USER, "");
			
	}
	
	public static int getResourceMenu(String menu){
		
		if(resourcesImages.isEmpty()){
			resourcesImages.put(MENU_CONFIGURACOES, R.drawable.configmenu);
			resourcesImages.put(MENU_CONTATO, R.drawable.contactmenu);
			resourcesImages.put(MENU_FAVORITOS, R.drawable.favormenu);
			resourcesImages.put(MENU_HOME, R.drawable.homemenu);			
			resourcesImages.put(MENU_PESQUISAR_IMOVEL, R.drawable.searchmenu);
		}
		
		return resourcesImages.get(menu);				
		
	}
	
	public static void init(Context context){
		SiDIMServerAPI api = new SiDIMServerAPI(context);
		BASE_URL_PHOTOS = api.URL_SERVER;
	}
	
	

}
