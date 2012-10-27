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

import de.neofonie.mobile.app.android.widget.crouton.Crouton;
import de.neofonie.mobile.app.android.widget.crouton.Style;

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

		globalPrefs = getSharedPreferences(
				ConfigGlobal.GLOBAL_SHARED_PREFERENCES, MODE_PRIVATE);
		emailUser = globalPrefs.getString(
				ConfigGlobal.SHARED_PREFERENCES_EMAIL_USER, null);

		final EditText edLogin = (EditText) findViewById(R.id.loginInputLogin);
		final EditText edSenha = (EditText) findViewById(R.id.loginInputSenhaLogin);
		chLoginAuto = (CheckBox) findViewById(R.id.loginAutoCheckLogin);
		Button btLogin = (Button) findViewById(R.id.loginBtLogarLogin);

		if (ValidacaoGeral.validaCampoVazio(emailUser)) {
			edLogin.setText(emailUser);
		}

		final SiDIMControllerServer controller = SiDIMControllerServer
				.getInstance(instance);

		btLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(!ValidacaoGeral.validate(edLogin.getText().toString().trim())){					
					Crouton.makeText(instance, "Campo Login precisa ser um e-mail", Style.ALERT).show();
					return;
				}

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
						if (ValidacaoGeral.validaCampoVazio(msgerror)) {
							Crouton.makeText(instance, msgerror, Style.ALERT).show();
						}

						if (progressDialog != null) {
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

						boolean sentLastUpdate = globalPrefs
								.getBoolean(
										ConfigGlobal.SHARED_PREFERENCES_SENT_USER_PROFILE,
										true);

						try {
							if (controller.validarLogin(cliente) != null) {
								SessionUserSidim.criarContaClienteLocal(
										instance, cliente);
								verifyAutoLogin();
								handler.sendEmptyMessage(2);
								startActivity(new Intent(instance,
										MenuPrincipalActivity.class));
								
								finish();
							} else {

							}
						} catch (SiDIMException e) {
							if (e.getMessage().contains("Invalido")) {
								if (sentLastUpdate) {

									Bundle data = new Bundle();
									data.putString("msgerror", e.getMessage());
									Message msg = new Message();
									msg.setData(data);
									handler.sendMessage(msg);
								} else {
									if (validarLoginLocal(userEmail, senha)) {
										try {
											controller
													.atualizarConta(SessionUserSidim
															.getContaCliente(getBaseContext()));
											SharedPreferences.Editor editor = globalPrefs
													.edit();
											editor.putBoolean(
													ConfigGlobal.SHARED_PREFERENCES_SENT_USER_PROFILE,
													true);
											editor.commit();											
										} catch (SiDIMException e1) {
											e1.printStackTrace();
										}
										handler.sendEmptyMessage(2);
										verifyAutoLogin();
										startActivity(new Intent(instance,
												MenuPrincipalActivity.class));
										finish();
									} else {
										Bundle data = new Bundle();
										data.putString("msgerror",
												"Login ou Senha Inv‡lido");
										Message msg = new Message();
										msg.setData(data);
										handler.sendMessage(msg);
									}

								}

							} else {

								if (validarLoginLocal(userEmail, senha)) {
									handler.sendEmptyMessage(2);
									verifyAutoLogin();
									startActivity(new Intent(instance,
											MenuPrincipalActivity.class));
									finish();
								} else {
									Bundle data = new Bundle();
									data.putString("msgerror",
											"Login ou Senha Inv‡lido");
									Message msg = new Message();
									msg.setData(data);
									handler.sendMessage(msg);
								}

							}
						}

					}
				};

				thread.start();
			}
		});

		TextView textCriarConta = (TextView) findViewById(R.id.loginTextCriarConta);
		textCriarConta.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivity(new Intent(instance, CriarContaActivity.class));

			}
		});

		TextView textEnviarSenha = (TextView) findViewById(R.id.loginTextEnviarSenha);
		textEnviarSenha.setOnClickListener(new OnClickListener() {

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
						if (ValidacaoGeral.validaCampoVazio(msgerror)) {
							Crouton.makeText(instance, msgerror, Style.ALERT).show();
						}

						String enviado = msgs.getData().getString("enviado");
						if (ValidacaoGeral.validaCampoVazio(enviado)) {
							Crouton.makeText(instance, enviado, Style.CONFIRM).show();
						}
						
						if (progressDialog != null) {
							progressDialog.dismiss();
						}

					}
				};

				Thread thread = new Thread() {

					@Override
					public void run() {

						SiDIMControllerServer controller = SiDIMControllerServer
								.getInstance(instance);
						try {
							if (controller.enviarSenha(emailUser)) {
								Bundle data = new Bundle();
								data.putString("enviado",
										"Senha Enviada para Email digitado.");
								Message msg = new Message();
								msg.setData(data);
								handler.sendMessage(msg);
							}
						} catch (SiDIMException e) {
							Bundle data = new Bundle();
							data.putString("msgerror",
									e.getMessage());
							Message msg = new Message();
							msg.setData(data);
							handler.sendMessage(msg);
						}
					}
				};

				thread.start();

			}

		});

	}

	private boolean validarLoginLocal(String userEmail, String senha) {
		if (validarLogin(userEmail) && validarSenha(senha)) {

			verifyAutoLogin();

			return true;
		} else {
			return false;
		}
	}
	
	private void verifyAutoLogin(){
		if (chLoginAuto.isChecked()) {
			SharedPreferences.Editor editor = globalPrefs.edit();
			editor.putBoolean(ConfigGlobal.SHARED_PREFERENCES_LOGIN_AUTO,
					true);
			editor.commit();
		}
	}

	private boolean validarLogin(String login) {

		String emailArmazenado = globalPrefs.getString(
				ConfigGlobal.SHARED_PREFERENCES_EMAIL_USER, null);
		if (ValidacaoGeral.validaCampoVazio(login)) {
			if (emailArmazenado.equals(login)) {
				return true;
			} else {

				return false;
			}
		} else {

			return false;
		}
	}

	private boolean validarSenha(String senha) {

		String senhaArmazenado = globalPrefs.getString(
				ConfigGlobal.SHARED_PREFERENCES_SENHA_USER, null);
		if (ValidacaoGeral.validaCampoVazio(senha)) {
			if (senhaArmazenado.equals(senha)) {
				return true;
			} else {				
				return false;
			}
		} else {			
			return false;
		}
	}

	private void showDialog() {
		progressDialog = ProgressDialog.show(this, "", "Aguarde...", true,
				false);
	}
	
	@Override
	  protected void onDestroy() {
	    // Workaround until there's a way to detach the Activity from Crouton while
	    // there are still some in the Queue.
	    Crouton.clearCroutonsForActivity(this);
	    super.onDestroy();
	  }

}
