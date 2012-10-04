package com.kots.sidim.android.activities;

import java.util.ArrayList;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kots.sidim.android.R;
import com.kots.sidim.android.adapter.FotoGaleriaAdapter;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.config.ValidacaoGeral;
import com.kots.sidim.android.dao.FavoritosDAO;
import com.kots.sidim.android.exception.SiDIMException;
import com.kots.sidim.android.model.Cliente;
import com.kots.sidim.android.model.Imovel;
import com.kots.sidim.android.model.InteresseCliente;
import com.kots.sidim.android.server.SiDIMControllerServer;
import com.kots.sidim.android.util.DrawableConnectionManager;
import com.kots.sidim.android.util.SessionUserSidim;

public class VisualizarImovelActivity extends MainBarActivity {

	Button btEnviarInteresse, addFavoritos;
	SiDIMControllerServer controller;
	Imovel imovel;
	SharedPreferences globalPrefs;

	ImageView imgMain;
	TextView txtTitle, txtBairro, txtCidadeEstado, txtQtds, txtArea,
			txtObservacao;
	FavoritosDAO favoritosDao;

	DrawableConnectionManager drawManager;

	private ArrayList<String> list;

	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visualizar_imovel,
				ConfigGlobal.MENU_INDEX_PESQUISAR_IMOVEL);

		drawManager = new DrawableConnectionManager();
		favoritosDao = new FavoritosDAO(instance);

		if (getIntent() != null) {
			if (getIntent().getSerializableExtra("imoveldetalhes") instanceof Imovel) {
				imovel = (Imovel) getIntent().getSerializableExtra(
						"imoveldetalhes");
				loadIds();
			}
		}

		controller = SiDIMControllerServer.getInstance(this);

		btEnviarInteresse = (Button) findViewById(R.id.visualizarImovelButtonInteresse);
		Gallery gallery = (Gallery) findViewById(R.id.visualizarImovelGallery);
		imgMain = (ImageView) findViewById(R.id.visualizarImovelImageMain);

		list = new ArrayList<String>();
		list.add("http://www.atlantycaimoveis.com.br/imoveis/7_4.jpg");
		list.add("http://www.atlantycaimoveis.com.br/imoveis/4_7.jpg");
		list.add("http://www.atlantycaimoveis.com.br/imoveis/8_6.jpg");
		list.add("http://www.atlantycaimoveis.com.br/imoveis/25_8.jpg");
		list.add("http://www.atlantycaimoveis.com.br/imoveis/21_3.jpg");

		if (SessionUserSidim.images.containsKey(list.get(0))) {
			imgMain.setImageBitmap(SessionUserSidim.images.get(list.get(0)));
		} else {
			drawManager.fetchDrawableOnThread(list.get(0), imgMain);
		}

		FotoGaleriaAdapter adapter = new FotoGaleriaAdapter(this, list);

		gallery.setAdapter(adapter);

		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// imgMain.setImageResource(list.get(arg2));
				if (SessionUserSidim.images.containsKey(list.get(arg2))) {
					imgMain.setImageBitmap(SessionUserSidim.images.get(list
							.get(arg2)));
				} else {
					drawManager.fetchDrawableOnThread(list.get(arg2), imgMain);
				}

			}
		});

		btEnviarInteresse.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enviarInteresse();

			}
		});

		addFavoritos = (Button) findViewById(R.id.visualizarImovelBtAddFavoritos);
		addFavoritos.setOnClickListener(new OnClickListener() {

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
							Toast.makeText(instance, msgerror,
									Toast.LENGTH_LONG).show();
						}

						if (progressDialog != null) {
							progressDialog.dismiss();
						}

					}
				};

				Thread thread = new Thread() {

					@Override
					public void run() {

						try {
							favoritosDao.insertFavorito(imovel, list);
							handler.sendEmptyMessage(2);
						} catch (SiDIMException e) {
							Bundle data = new Bundle();
							data.putString("msgerror", e.getMessage());
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

	private void enviarInteresse() {

		globalPrefs = getSharedPreferences(
				ConfigGlobal.GLOBAL_SHARED_PREFERENCES, MODE_PRIVATE);

		Cliente cliente = new Cliente();
		cliente.setLogin(globalPrefs.getString(
				ConfigGlobal.SHARED_PREFERENCES_EMAIL_USER, ""));
		InteresseCliente interesse = new InteresseCliente(imovel, cliente,
				new Date());
		try {
			controller.enviarInteresse(interesse);
			Toast.makeText(this,
					"Interesse Enviado, Em breve entraremos em contato",
					Toast.LENGTH_LONG).show();
		} catch (SiDIMException e) {
			Toast.makeText(this,
					"N‹o foi poss’vel enviar Interesse, verifique sua conex‹o",
					Toast.LENGTH_LONG).show();
		}

	}

	private void loadIds() {
		txtTitle = (TextView) findViewById(R.id.visualizarImovelTextTitle);
		txtBairro = (TextView) findViewById(R.id.visualizarImovelTextBairro);
		txtCidadeEstado = (TextView) findViewById(R.id.visualizarImovelTextCidadeEstado);
		txtQtds = (TextView) findViewById(R.id.visualizarImovelTextQtds);
		txtArea = (TextView) findViewById(R.id.visualizarImovelTextArea);
		txtObservacao = (TextView) findViewById(R.id.visualizarImovelTextObservacao);

		txtTitle.setText(imovel.getTipoImovel().getDescricao() + " - "
				+ imovel.getBairro().getNome() + " " + imovel.getDormitorios()
				+ " Dorm");
		txtBairro.setText(imovel.getBairro().getNome());
		txtCidadeEstado.setText(imovel.getCidade().getNome() + "-"
				+ imovel.getEstado().getNome());
		txtQtds.setText(imovel.getDormitorios() + " Dorm, sendo "
				+ imovel.getSuites() + " su’tes - com " + imovel.getGaragens()
				+ " Garagens");
		txtArea.setText("çrea: " + imovel.getArea() + " Constru’da - "
				+ imovel.getArea() + " Total");
		txtObservacao.setText(imovel.getDescricao());
	}

	private void showDialog() {
		progressDialog = ProgressDialog.show(this, "", "Adicionando...",
				true, false);
	}
}
