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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kots.sidim.android.R;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.config.ValidacaoGeral;
import com.kots.sidim.android.exception.SiDIMException;
import com.kots.sidim.android.model.Cliente;
import com.kots.sidim.android.server.SiDIMControllerServer;

public class CriarContaActivity extends Activity {

	SharedPreferences globalPrefs;
	Activity instance;
	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_criarconta);
		instance = this;

		globalPrefs = getSharedPreferences(
				ConfigGlobal.GLOBAL_SHARED_PREFERENCES, MODE_PRIVATE);

		final EditText edEmail = (EditText) findViewById(R.id.criarContaInputEmail);
		final EditText edSenha = (EditText) findViewById(R.id.criarContaInputSenha);
		final EditText edConfSenha = (EditText) findViewById(R.id.criarContaInputConfSenha);
		final EditText edNome = (EditText) findViewById(R.id.criarContaInputNome);
		final EditText edCidade = (EditText) findViewById(R.id.criarContaAutoComplCidade);
		final EditText edTelefone = (EditText) findViewById(R.id.criarContaInputTelefone);

		Button btCriarConta = (Button) findViewById(R.id.criarContaBtCriarConta);
		Button btCancel = (Button) findViewById(R.id.criarContaBtCancelar);

		btCriarConta.setOnClickListener(new OnClickListener() {

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

						String email = edEmail.getText().toString();
						String senha = edSenha.getText().toString();
						String confSenha = edConfSenha.getText().toString();
						String nome = edNome.getText().toString();
						String cidade = edCidade.getText().toString();
						String telefone = edTelefone.getText().toString();

						if (validaEmail(email) && validaSenha(senha, confSenha)
								&& validarNome(nome)) {

							Cliente cliente = new Cliente(email, nome, senha);
							cliente.setCidade(cidade);
							cliente.setTelefone(telefone);
							SiDIMControllerServer controller = SiDIMControllerServer
									.getInstance(instance);

							try {

								controller.criarConta(cliente);
								gravarDadosPreferences(email, confSenha, nome,
										telefone, cidade);
								startActivity(new Intent(instance,
										MenuPrincipalActivity.class));
								finish();

							} catch (SiDIMException e) {
								
								//handler.sendEmptyMessage(1);
								Bundle data = new Bundle();
								data.putString("msgerror", e.getMessage());
								Message msg = new Message();
								msg.setData(data);
								handler.sendMessage(msg);
							}
						}

						handler.sendEmptyMessage(2);
						// Handler handler = new Handler();
						// handler.post(new Runnable() {
						// @Override
						// public void run() {
						// if (progressDialog != null)
						// progressDialog.dismiss();
						// }
						// });

					}

				};

				thread.start();

			}
		});

		btCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();

			}
		});

	}

	private boolean validarNome(String nome) {

		if (ValidacaoGeral.validaCampoVazio(nome)) {

			return true;

		} else {
			Toast.makeText(this, "Campo Nome Ž obrigat—rio", Toast.LENGTH_LONG)
					.show();
			return false;
		}

	}

	private boolean validaEmail(String email) {

		if (ValidacaoGeral.validaCampoVazio(email)) {
			if (email.contains("@"))
				return true;
			else {
				Toast.makeText(this, "N‹o Ž um e-mail v‡lido",
						Toast.LENGTH_LONG).show();
				return false;
			}
		} else {
			Toast.makeText(this, "Campo e-mail Ž obrigat—rio",
					Toast.LENGTH_LONG).show();
			return false;
		}

	}

	private boolean validaSenha(String senha, String confirmarSenha) {

		if (ValidacaoGeral.validaCampoVazio(senha)
				&& ValidacaoGeral.validaCampoVazio(confirmarSenha)) {

			if (senha.length() < 6) {

				Toast.makeText(this,
						"Campo Senha precisa ter no min’mo 6 caracteres",
						Toast.LENGTH_LONG).show();
				return false;

			}

			if (senha.equals(confirmarSenha)) {
				return true;
			} else {
				Toast.makeText(this,
						"Campo Senha e Confirmar Senha n‹o conferem",
						Toast.LENGTH_LONG).show();
				return false;
			}

		} else {
			Toast.makeText(this,
					"Campo Senha e Confirmar Senha s‹o obrigat—rio",
					Toast.LENGTH_LONG).show();
			return false;
		}

	}

	private void gravarDadosPreferences(String email, String senha,
			String nome, String telefone, String cidade) {

		SharedPreferences.Editor editor = globalPrefs.edit();
		editor.putString(ConfigGlobal.SHARED_PREFERENCES_EMAIL_USER, email);
		editor.putString(ConfigGlobal.SHARED_PREFERENCES_SENHA_USER, senha);

		if (ValidacaoGeral.validaCampoVazio(nome)) {
			editor.putString(ConfigGlobal.SHARED_PREFERENCES_NOME_USER, nome);
		}

		if (ValidacaoGeral.validaCampoVazio(telefone)) {
			editor.putString(ConfigGlobal.SHARED_PREFERENCES_TELEFONE_USER,
					telefone);
		}

		if (ValidacaoGeral.validaCampoVazio(cidade)) {
			editor.putString(ConfigGlobal.SHARED_PREFERENCES_CIDADE_USER,
					cidade);
		}

		editor.putBoolean(ConfigGlobal.SHARED_PREFERENCES_SENT_USER_PROFILE,
				false);

		editor.commit();

	}

	private void showDialog() {
		progressDialog = ProgressDialog.show(this, "", "Criando Conta...",
				true, false);
	}

}
