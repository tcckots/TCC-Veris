package com.kots.sidim.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.kots.sidim.android.R;
import com.kots.sidim.android.config.ConfigGlobal;

public class MenuPrincipalActivity extends Activity {

	Activity instance;
	SharedPreferences globalPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_principal);

		instance = this;
		
		globalPrefs = getSharedPreferences(
				ConfigGlobal.GLOBAL_SHARED_PREFERENCES, MODE_PRIVATE);
		
		String nome = globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_NOME_USER,null);
		
		
//		List<ImovelMobile> imoveis = new ArrayList<ImovelMobile>();
//		
//		
//		ImovelAdapter adapter = new ImovelAdapter(this, imoveis);
//
//		
//		
//        ListView listMenu = (ListView) findViewById(R.id.menuListOptionHome);
//        listMenu.setAdapter(new MenuAdapter(this, ConfigGlobal.menuPrincipalList));
//		
//		ListView list = (ListView) findViewById(R.id.menulist3dImoveis);
//		list.setDivider( null ); 
//        list.setAdapter(adapter);
		
		TextView textBemvindo = (TextView) findViewById(R.id.menuPrincipalTextBemVindo);
		if(nome != null){
			textBemvindo.setText("Bem-vindo " + nome);
		} else {
			textBemvindo.setText("Bem-vindo");
		}
        
		LinearLayout btFavor = (LinearLayout) findViewById(R.id.menuPrincipalBtFavor);
		btFavor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(new Intent(instance,FavoritosActivity.class));
			}
		});
		
		LinearLayout btPesquisar = (LinearLayout) findViewById(R.id.menuPrincipalBtPesquisar);
		btPesquisar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(new Intent(instance,PesquisarImovelActivity.class));
			}
		});
		
		LinearLayout btConfig = (LinearLayout) findViewById(R.id.menuPrincipalBtConfig);
		btConfig.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(new Intent(instance,ConfiguracoesActivity.class));
			}
		});
		
		LinearLayout btContato = (LinearLayout) findViewById(R.id.menuPrincipalBtContato);
		btContato.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(new Intent(instance,ContatoActivity.class));
			}
		});


	}
       
        
}
