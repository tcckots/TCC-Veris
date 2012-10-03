package com.kots.sidim.android.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kots.sidim.android.R;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.config.ValidacaoGeral;
import com.kots.sidim.android.exception.SiDIMException;
import com.kots.sidim.android.model.Cliente;
import com.kots.sidim.android.server.SiDIMControllerServer;
import com.kots.sidim.android.util.SessionUserSidim;

public class LoginActivity extends Activity {
	
	SharedPreferences globalPrefs;
	Activity instance;
	String emailUser;
	CheckBox chLoginAuto;
	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		instance = this;
		
		globalPrefs = getSharedPreferences(ConfigGlobal.GLOBAL_SHARED_PREFERENCES, MODE_PRIVATE);
		emailUser = globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_EMAIL_USER, null);
		
		final EditText edLogin = (EditText) findViewById(R.id.loginInputLogin);
		final EditText edSenha = (EditText) findViewById(R.id.loginInputSenhaLogin);
		chLoginAuto = (CheckBox) findViewById(R.id.loginAutoCheckLogin);
		Button btLogin = (Button) findViewById(R.id.loginBtLogarLogin);
		
	
		
		if(ValidacaoGeral.validaCampoVazio(emailUser)){
			edLogin.setText(emailUser);
		}
		
		final SiDIMControllerServer controller = SiDIMControllerServer.getInstance(instance);
		
		btLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				instance.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						showDialog();
					}
				});
				
				final Handler handler = new Handler() {
					@Override
					public void handleMessage(final Message msgs) {
						
						String msgerror = msgs.getData().getString("msgerror");
						if(ValidacaoGeral.validaCampoVazio(msgerror)){
							Toast.makeText(instance, msgerror,
									Toast.LENGTH_LONG).show();
						}
						
						if(progressDialog != null){
							progressDialog.dismiss();
						}
						
					}
				};
				
				Thread thread = new Thread() {

					@Override
					public void run() {

				
					String userEmail = edLogin.getText().toString();
					String senha = edSenha.getText().toString();
					
					Cliente cliente = new Cliente();
					cliente.setLogin(userEmail);
					cliente.setSenha(senha);
					
					boolean sentLastUpdate = globalPrefs.getBoolean(ConfigGlobal.SHARED_PREFERENCES_SENT_USER_PROFILE,true);
					
					try{
						if(controller.validarLogin(cliente) != null){
							SessionUserSidim.criarContaClienteLocal(instance, cliente);							
							handler.sendEmptyMessage(2);
							startActivity(new Intent(instance,MenuPrincipalActivity.class));
							finish();
						} else {
							
						}
					} catch(SiDIMException e){
						if(e.getMessage().contains("Invalido")){
							if(sentLastUpdate){
								
								Bundle data = new Bundle();
								data.putString("msgerror", e.getMessage());
								Message msg = new Message();
								msg.setData(data);
								handler.sendMessage(msg);
							} else {
								if(validarLoginLocal(userEmail, senha)){
									try {
										controller.atualizarConta(SessionUserSidim.getContaCliente(getBaseContext()));
										SharedPreferences.Editor editor = globalPrefs.edit();
										editor.putBoolean(ConfigGlobal.SHARED_PREFERENCES_SENT_USER_PROFILE,true);
										editor.commit();
									} catch (SiDIMException e1) {									
										e1.printStackTrace();
									}
									handler.sendEmptyMessage(2);
									startActivity(new Intent(instance,MenuPrincipalActivity.class));
									finish();
								} else {
									Bundle data = new Bundle();
									data.putString("msgerror", "Login ou Senha Inv‡lido");
									Message msg = new Message();	
									msg.setData(data);
									handler.sendMessage(msg);
								}
								
							}
							
						} else {
							
							if(validarLoginLocal(userEmail, senha)){
								handler.sendEmptyMessage(2);
								startActivity(new Intent(instance,MenuPrincipalActivity.class));
								finish();
							} else {
								Bundle data = new Bundle();
								data.putString("msgerror", "Login ou Senha Inv‡lido");
								Message msg = new Message();
								msg.setData(data);
								handler.sendMessage(msg);
							}
							
						}
					}
					
								
					}};
					
					thread.start();
			}
		});
		
		TextView textCriarConta = (TextView) findViewById(R.id.loginTextCriarConta);
		textCriarConta.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				startActivity(new Intent(instance,CriarContaActivity.class));
				
			}
		});
		
		TextView textEnviarSenha = (TextView) findViewById(R.id.loginTextEnviarSenha);
		textEnviarSenha.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				SiDIMControllerServer controller = SiDIMControllerServer.getInstance(instance);
				try{
					controller.enviarSenha(emailUser);
				} catch (SiDIMException e){
					Toast.makeText(instance, e.getMessage(), Toast.LENGTH_LONG).show();
				}
				
				
			}
		});

	}
	
	private boolean validarLoginLocal(String userEmail, String senha){
		if(validarLogin(userEmail) && validarSenha(senha)){
						
			if(chLoginAuto.isChecked()){
				SharedPreferences.Editor editor = globalPrefs.edit();
				editor.putBoolean(ConfigGlobal.SHARED_PREFERENCES_LOGIN_AUTO, true);
				editor.commit();
			}	
			
			return true;
		} else {
			return false;
		}
	}
	
	private boolean validarLogin(String login){
		
		String emailArmazenado = globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_EMAIL_USER, null);
		if(ValidacaoGeral.validaCampoVazio(login)){
			if(emailArmazenado.equals(login)){
				return true;
			} else {
				//Toast.makeText(this, "E-mail inv‡lido", Toast.LENGTH_LONG).show();
				return false;
			}
		} else {
			//Toast.makeText(this, "E-mail em branco", Toast.LENGTH_LONG).show();
			return false;
		}							
	}
	
	private boolean validarSenha(String senha){
		
		String senhaArmazenado = globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_SENHA_USER, null);
		if(ValidacaoGeral.validaCampoVazio(senha)){
			if(senhaArmazenado.equals(senha)){
				return true;
			} else {
			//Toast.makeText(this, "Senha inv‡lida", Toast.LENGTH_LONG).show();
				return false;
			}
		} else {
			//Toast.makeText(this, "Senha em branco", Toast.LENGTH_LONG).show();
			return false;
		}							
	}
	
	
	
	private void showDialog() {
		progressDialog = ProgressDialog.show(this, "", "Aguarde...",
				true, false);
	}
	
	

}
