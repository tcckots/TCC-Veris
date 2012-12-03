package com.kots.sidim.android.activities;

import android.annotation.SuppressLint;
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


import com.kots.sidim.android.R;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.config.ValidacaoGeral;
import com.kots.sidim.android.exception.SiDIMException;
import com.kots.sidim.android.model.Cliente;
import com.kots.sidim.android.server.SiDIMControllerServer;
import com.kots.sidim.android.util.SessionUserSidim;

import de.neofonie.mobile.app.android.widget.crouton.Crouton;
import de.neofonie.mobile.app.android.widget.crouton.Style;

@SuppressLint("HandlerLeak")
public class ConfiguracoesActivity extends MainBarActivity {

	Activity instance;
	EditText edSenhaAtual, edNovaSenha, edConfirmaSenha, edEmail, edCidade,
			edTelefone;
	Button btSalvar, btCancel;
	CheckBox chNotific, chAutoLogin;

	SharedPreferences globalPrefs;
	SharedPreferences.Editor editor;

	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracoes,
				ConfigGlobal.MENU_INDEX_CONFIGURACOES);

		instance = this;

		globalPrefs = getSharedPreferences(
				ConfigGlobal.GLOBAL_SHARED_PREFERENCES, MODE_PRIVATE);
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

				// Atualizar no Servidor

				if (validarSenha(edSenhaAtual.getText().toString(), edNovaSenha
						.getText().toString(), edConfirmaSenha.getText()
						.toString())) {

					instance.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							showDialog();
						}
					});

					final Handler handler = new Handler() {
						@Override
						public void handleMessage(final Message msgs) {
							
							String msgerror = msgs.getData().getString(
									"msgerror");
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

							Cliente cliente = SessionUserSidim
									.getContaCliente(instance);
							SiDIMControllerServer controller = SiDIMControllerServer
									.getInstance(instance);

							try {

								controller.atualizarConta(cliente);
								handler.sendEmptyMessage(2);
								startActivity(new Intent(instance,
										MenuPrincipalActivity.class));
								finish();

							} catch (SiDIMException e) {
								handler.sendEmptyMessage(2);
								finish();
//								Bundle data = new Bundle();
//								data.putString("msgerror", e.getMessage());
//								Message msg = new Message();
//								msg.setData(data);
//								handler.sendMessage(msg);
							}

						}
					};
					
					thread.start();

				}
			}
		});

	}

	private void loadIDs() {
		edSenhaAtual = (EditText) findViewById(R.id.configInputSenha);
		edNovaSenha = (EditText) findViewById(R.id.configInputNovaSenha);
		edConfirmaSenha = (EditText) findViewById(R.id.configInputConfSenha);

		edEmail = (EditText) findViewById(R.id.configInputEmail);
		edCidade = (EditText) findViewById(R.id.configInputCidade);

		edTelefone = (EditText) findViewById(R.id.configInputTelefone);

		btSalvar = (Button) findViewById(R.id.configBtSalvar);
		btCancel = (Button) findViewById(R.id.configBtCancel);

		// = (CheckBox) findViewById(R.id.configCheckNotific);
		chAutoLogin = (CheckBox) findViewById(R.id.configCheckAutoLogin);

		edEmail.setEnabled(false);
	}

	private void carregarDadosUsuario() {

		edEmail.setText(globalPrefs.getString(
				ConfigGlobal.SHARED_PREFERENCES_EMAIL_USER, ""));
		edCidade.setText(globalPrefs.getString(
				ConfigGlobal.SHARED_PREFERENCES_CIDADE_USER, ""));
		edTelefone.setText(globalPrefs.getString(
				ConfigGlobal.SHARED_PREFERENCES_TELEFONE_USER, ""));
//		chNotific.setChecked(globalPrefs.getBoolean(
//				ConfigGlobal.SHARED_PREFERENCES_RECEBER_NOTIFIC, false));
		chAutoLogin.setChecked(globalPrefs.getBoolean(
				ConfigGlobal.SHARED_PREFERENCES_LOGIN_AUTO, false));

	}

	private boolean validarSenha(String senhaAtual, String novaSenha,
			String confirmaSenha) {

		boolean valid = false;

		if (ValidacaoGeral.validaCampoVazio(senhaAtual)) {
			String senhaValida = globalPrefs.getString(
					ConfigGlobal.SHARED_PREFERENCES_SENHA_USER, "");
			if (senhaValida.equals(senhaAtual)) {
				if (novaSenha.length() >= 6) {
					if (novaSenha.equals(confirmaSenha)) {
						editor.putString(
								ConfigGlobal.SHARED_PREFERENCES_SENHA_USER,
								edNovaSenha.getText().toString());

						editor.putBoolean(
								ConfigGlobal.SHARED_PREFERENCES_LOGIN_AUTO,
								chAutoLogin.isChecked());
//						editor.putBoolean(
//								ConfigGlobal.SHARED_PREFERENCES_RECEBER_NOTIFIC,
//								chNotific.isChecked());
						editor.putString(
								ConfigGlobal.SHARED_PREFERENCES_CIDADE_USER,
								edCidade.getText().toString());
						editor.putString(
								ConfigGlobal.SHARED_PREFERENCES_TELEFONE_USER,
								edTelefone.getText().toString());
						editor.putBoolean(
								ConfigGlobal.SHARED_PREFERENCES_SENT_USER_PROFILE,
								false);

						editor.commit();
						valid = true;
					} else {
						Crouton.makeText(instance, "Nova Senha e Campo Confirmar senha não conferem", Style.ALERT).show();
						
						valid = false;
					}
				} else {
					Crouton.makeText(instance, "Senha precisa ter no minímo 6 caracteres", Style.ALERT).show();					
				}
			} else {
				Crouton.makeText(instance, "Senha Atual Digitada não é válida", Style.ALERT).show();
				
				valid = false;
			}
		} else {
			valid = true;
			editor.putBoolean(ConfigGlobal.SHARED_PREFERENCES_LOGIN_AUTO,
					chAutoLogin.isChecked());
//			editor.putBoolean(ConfigGlobal.SHARED_PREFERENCES_RECEBER_NOTIFIC,
//					chNotific.isChecked());
			editor.putString(ConfigGlobal.SHARED_PREFERENCES_CIDADE_USER,
					edCidade.getText().toString());
			editor.putString(ConfigGlobal.SHARED_PREFERENCES_TELEFONE_USER,
					edTelefone.getText().toString());
			editor.putBoolean(
					ConfigGlobal.SHARED_PREFERENCES_SENT_USER_PROFILE, false);
			editor.commit();
		}

		return valid;

	}

	private void showDialog() {
		progressDialog = ProgressDialog.show(this, "", "Atualizando Dados...",
				true, false);
	}
	@Override
	  protected void onDestroy() {
	    // Workaround until there's a way to detach the Activity from Crouton while
	    // there are still some in the Queue.
	    Crouton.clearCroutonsForActivity(this);
	    super.onDestroy();
	  }

}
