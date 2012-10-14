package com.kots.sidim.android.activities;

import java.text.DecimalFormat;
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
import com.kots.sidim.android.model.ImovelMobile;
import com.kots.sidim.android.model.InteresseCliente;
import com.kots.sidim.android.model.InteresseClienteId;
import com.kots.sidim.android.server.SiDIMControllerServer;
import com.kots.sidim.android.util.DrawableConnectionManager;
import com.kots.sidim.android.util.SessionUserSidim;

public class VisualizarImovelActivity2 extends MainBarActivity {

	Button btEnviarInteresse, addFavoritos;
	SiDIMControllerServer controller;
	ImovelMobile imovel;
	SharedPreferences globalPrefs;

	ImageView imgMain;
	TextView txtTitle, txtBairro, txtCidadeEstado, txtQtdsDorm, txtQtdGarag, txtAreaConstruida, txtAreaTotal,
			txtObservacao, txtIntencao, txtPreco;
	FavoritosDAO favoritosDao;

	DrawableConnectionManager drawManager;

	//private ArrayList<String> list;

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
			if (getIntent().getSerializableExtra("imoveldetalhes") instanceof ImovelMobile) {
				imovel = (ImovelMobile) getIntent().getSerializableExtra(
						"imoveldetalhes");
				loadIds();
			}
		}

		controller = SiDIMControllerServer.getInstance(this);

		btEnviarInteresse = (Button) findViewById(R.id.visualizarImovelButtonInteresse);
		Gallery gallery = (Gallery) findViewById(R.id.visualizarImovelGallery);
		imgMain = (ImageView) findViewById(R.id.visualizarImovelImageMain);



		if(imovel != null && imovel.getFotos() != null && imovel.getFotos().size() > 0){
			if (SessionUserSidim.images.containsKey(imovel.getFotos().get(0))) {
				
					imgMain.setImageBitmap(SessionUserSidim.images.get(imovel.getFotos().get(0)));
				
			} else {
				drawManager.fetchDrawableOnThread(imovel.getFotos().get(0), imgMain);
			}
			FotoGaleriaAdapter adapter = new FotoGaleriaAdapter(this, imovel.getFotos());

			gallery.setAdapter(adapter);

		}

		
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// imgMain.setImageResource(list.get(arg2));
				if (SessionUserSidim.images.containsKey(imovel.getFotos().get(arg2))) {
					imgMain.setImageBitmap(SessionUserSidim.images.get(imovel.getFotos().get(arg2)));
				} else {
					drawManager.fetchDrawableOnThread(imovel.getFotos().get(arg2), imgMain);
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
						showDialog("Adicionando...");
					}
				});

				final Handler handler = new Handler() {
					@Override
					public void handleMessage(final Message msgs) {

						String msgerror = msgs.getData().getString("msgerror");
						if (ValidacaoGeral.validaCampoVazio(msgerror)) {
							Toast.makeText(instance, msgerror,
									Toast.LENGTH_LONG).show();
						}else {
							Toast.makeText(instance, "Im—vel adicionado aos favoritos",
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
							favoritosDao.insertFavorito(imovel, imovel.getFotos());
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
//		final InteresseCliente interesse = new InteresseCliente(imovel, cliente,
//				new Date());
		final InteresseClienteId interesse = new InteresseClienteId(cliente.getLogin(), imovel.getIdImovel());
		
		instance.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				showDialog("Enviando Interesse...");
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
					controller.enviarInteresse(interesse);
					
					Bundle data = new Bundle();
					data.putString("msgerror", "Interesse Enviado, Em breve entraremos em contato");
					Message msg = new Message();
					msg.setData(data);
					handler.sendMessage(msg);
										
				} catch (SiDIMException e) {
					
					Bundle data = new Bundle();
					data.putString("msgerror", e.getMessage());
					Message msg = new Message();
					msg.setData(data);
					handler.sendMessage(msg);
										
				}
			}
		};  thread.start();

	}

	private void loadIds() {
		txtTitle = (TextView) findViewById(R.id.visualizarImovelTextTitle);
		txtBairro = (TextView) findViewById(R.id.visualizarImovelTextBairro);
		txtCidadeEstado = (TextView) findViewById(R.id.visualizarImovelTextCidadeEstado);
		txtQtdsDorm = (TextView) findViewById(R.id.visualizarImovelTextQtdsDorm);
		txtQtdGarag = (TextView) findViewById(R.id.visualizarImovelTextQtdGaragens);
		txtAreaConstruida = (TextView) findViewById(R.id.visualizarImovelTextAreaContruida);
		txtAreaTotal = (TextView) findViewById(R.id.visualizarImovelTextAreaTotal);
		txtObservacao = (TextView) findViewById(R.id.visualizarImovelTextObservacao);
		txtIntencao = (TextView) findViewById(R.id.visualizarImovelTextIntencaoValor);

		txtPreco = (TextView) findViewById(R.id.visualizarImovelTextPreco);

		txtTitle.setText(imovel.getTipoImovel().getDescricao() + " - "
				+ imovel.getBairro().getNome() + " " + imovel.getDormitorios()
				+ " Dorm");
		DecimalFormat df = new DecimalFormat("###,###,###.00");
		
		String preco = "";
		
		if(imovel.getIntencao().equals("C")){
			
			txtIntencao.setText("Compra:");
			
			if(imovel.getPreco().doubleValue() == 0){
				preco = "Entre em contato";
			} else {
				preco = "R$ " +  df.format(imovel.getPreco().doubleValue());
			}
			txtPreco.setText(preco);
		} else {
			txtIntencao.setText("Aluga:");
			if(imovel.getPreco().doubleValue() == 0){
				preco = "Entre em contato";
			} else {
				preco = "R$ " +  df.format(imovel.getPreco().doubleValue());
			}
			txtPreco.setText(preco);
		}
		
		txtBairro.setText(imovel.getBairro().getNome());
		txtCidadeEstado.setText(imovel.getCidade().getNome() + "-"
				+ imovel.getEstado().getUf());
		txtQtdsDorm.setText(imovel.getDormitorios() + ", sendo "
				+ imovel.getSuites() + " su’te");
		txtAreaConstruida.setText(imovel.getAreaConstruida() + "m2 Constru’do");
		txtAreaTotal.setText(imovel.getAreaTotal() + "m2 Total");
	
		txtObservacao.setText(imovel.getDescricao());
		txtQtdGarag.setText(imovel.getGaragens() + "");
	}

	private void showDialog(String msg) {
		progressDialog = ProgressDialog.show(this, "", msg,
				true, false);
	}
}
