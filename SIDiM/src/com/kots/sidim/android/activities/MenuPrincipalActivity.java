package com.kots.sidim.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
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
