package com.kots.sidim.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.kots.sidim.android.R;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.config.ValidacaoGeral;

public class LoginActivity extends Activity {
	
	SharedPreferences globalPrefs;
	Activity instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		instance = this;
		
		globalPrefs = getSharedPreferences(ConfigGlobal.GLOBAL_SHARED_PREFERENCES, MODE_PRIVATE);
		String emailUser = globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_EMAIL_USER, null);
		
		final EditText edLogin = (EditText) findViewById(R.id.loginInputLogin);
		final EditText edSenha = (EditText) findViewById(R.id.loginInputSenhaLogin);
		final CheckBox chLoginAuto = (CheckBox) findViewById(R.id.loginAutoCheckLogin);
		Button btLogin = (Button) findViewById(R.id.loginBtLogarLogin);
		
	
		
		if(ValidacaoGeral.validaCampoVazio(emailUser)){
			edLogin.setText(emailUser);
		}
		
		btLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String userEmail = edLogin.getText().toString();
				String senha = edSenha.getText().toString();
				
				if(validarLogin(userEmail) && validarSenha(senha)){
					if(chLoginAuto.isChecked()){
						SharedPreferences.Editor editor = globalPrefs.edit();
						editor.putBoolean(ConfigGlobal.SHARED_PREFERENCES_LOGIN_AUTO, true);
						editor.commit();
					}
					
					startActivity(new Intent(instance,MenuPrincipalActivity.class));
					finish();
				}
				
			}
		});
		
		
		

	}
	
	private boolean validarLogin(String login){
		
		String emailArmazenado = globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_EMAIL_USER, null);
		if(ValidacaoGeral.validaCampoVazio(login)){
			if(emailArmazenado.equals(login)){
				return true;
			} else {
				Toast.makeText(this, "E-mail inv‡lido", Toast.LENGTH_LONG).show();
				return false;
			}
		} else {
			Toast.makeText(this, "E-mail em branco", Toast.LENGTH_LONG).show();
			return false;
		}							
	}
	
	private boolean validarSenha(String senha){
		
		String senhaArmazenado = globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_SENHA_USER, null);
		if(ValidacaoGeral.validaCampoVazio(senha)){
			if(senhaArmazenado.equals(senha)){
				return true;
			} else {
				Toast.makeText(this, "Senha inv‡lida", Toast.LENGTH_LONG).show();
				return false;
			}
		} else {
			Toast.makeText(this, "Senha em branco", Toast.LENGTH_LONG).show();
			return false;
		}							
	}
	
	
	
	
	
	

}
