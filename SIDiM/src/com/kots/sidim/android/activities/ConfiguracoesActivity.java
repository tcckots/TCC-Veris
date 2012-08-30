package com.kots.sidim.android.activities;

import android.app.Activity;
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

public class ConfiguracoesActivity extends MainBarActivity {
	
	Activity instance;
	EditText edSenhaAtual, edNovaSenha, edConfirmaSenha, edEmail, edCidade, edTelefone;
	Button btSalvar, btCancel;
	CheckBox chNotific;

	SharedPreferences globalPrefs;
	SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracoes, ConfigGlobal.MENU_INDEX_CONFIGURACOES);		
		
		globalPrefs = getSharedPreferences(ConfigGlobal.GLOBAL_SHARED_PREFERENCES, MODE_PRIVATE);
		editor = globalPrefs.edit();			
		
		loadIDs();
		carregarDadosUsuario();
		
		btCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				finish();
				
			}
		});
		
		btSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				
				
				editor.putBoolean(ConfigGlobal.SHARED_PREFERENCES_RECEBER_NOTIFIC, chNotific.isChecked());
				editor.putString(ConfigGlobal.SHARED_PREFERENCES_CIDADE_USER, edCidade.getText().toString());
				editor.putString(ConfigGlobal.SHARED_PREFERENCES_TELEFONE_USER, edTelefone.getText().toString());
				editor.putBoolean(ConfigGlobal.SHARED_PREFERENCES_SENT_USER_PROFILE, false);
				
				editor.commit();
				
				//Atualizar no Servidor
				
				if(validarSenha(edSenhaAtual.getText().toString(), edNovaSenha.getText().toString(), edConfirmaSenha.getText().toString())){
					 //editor.putString(ConfigGlobal.SHARED_PREFERENCES_SENHA_USER, edNovaSenha.getText().toString());
					finish();
				}
				
				
				
				
			}
		});
		
	}
	
	private void loadIDs(){
		edSenhaAtual = (EditText) findViewById(R.id.configInputSenha);
		edNovaSenha = (EditText) findViewById(R.id.configInputNovaSenha);
		edConfirmaSenha = (EditText) findViewById(R.id.configInputConfSenha);
		
		edEmail = (EditText) findViewById(R.id.configInputEmail);
		edCidade = (EditText) findViewById(R.id.configInputCidade);
		
		edTelefone = (EditText) findViewById(R.id.configInputTelefone);
		
		btSalvar = (Button) findViewById(R.id.configBtSalvar);
		btCancel = (Button) findViewById(R.id.configBtCancel);
		
		chNotific = (CheckBox) findViewById(R.id.configCheckNotific);
		
		edEmail.setEnabled(false);
	}
	
	private void carregarDadosUsuario(){
		
		edEmail.setText(globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_EMAIL_USER, ""));
		edCidade.setText(globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_CIDADE_USER, ""));
		edTelefone.setText(globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_TELEFONE_USER, ""));
		chNotific.setChecked(globalPrefs.getBoolean(ConfigGlobal.SHARED_PREFERENCES_RECEBER_NOTIFIC, false));				
		
	}
	
	private boolean validarSenha(String senhaAtual, String novaSenha, String confirmaSenha){
		
		boolean valid = false;
		
		if(ValidacaoGeral.validaCampoVazio(senhaAtual)){
			String senhaValida = globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_SENHA_USER, "");
			if(senhaValida.equals(senhaAtual)){
				if(novaSenha.length() > 6){
					if(novaSenha.equals(confirmaSenha)){
						editor.putString(ConfigGlobal.SHARED_PREFERENCES_SENHA_USER, edNovaSenha.getText().toString());
						editor.commit();
						valid = true;
					} else {
						Toast.makeText(this, "Nova Senha e Campo Confirmar senha n‹o conferem", Toast.LENGTH_LONG).show();
						valid = false;
					}
				} else {
					Toast.makeText(this, "Senha precisa ter no min’mo 6 caracteres", Toast.LENGTH_LONG).show();
					valid = false;
				}
			} else {
				Toast.makeText(this, "Senha Atual Digitada n‹o Ž v‡lida", Toast.LENGTH_LONG).show();
				valid = false;
			}
		} else {
			valid = true;
		}
		
		
		return valid;
		
	}
	
	

}
