package com.kots.sidim.android.activities;

import java.text.DecimalFormat;
import android.annotation.SuppressLint;
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
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.kots.sidim.android.R;
import com.kots.sidim.android.adapter.FotoGaleriaAdapter;
import com.kots.sidim.android.adapter.FotoGaleriaFavorAdapter;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.config.ValidacaoGeral;
import com.kots.sidim.android.dao.FavoritosDAO;
import com.kots.sidim.android.exception.SiDIMException;
import com.kots.sidim.android.model.Cliente;
import com.kots.sidim.android.model.ImovelMobile;
import com.kots.sidim.android.model.InteresseClienteId;
import com.kots.sidim.android.server.SiDIMControllerServer;
import com.kots.sidim.android.util.DrawableConnectionManager;
import com.kots.sidim.android.util.LoadImagesSDCard;
import com.kots.sidim.android.util.SessionUserSidim;

import de.neofonie.mobile.app.android.widget.crouton.Crouton;
import de.neofonie.mobile.app.android.widget.crouton.Style;

@SuppressLint("HandlerLeak")
public class VisualizarImovelFavoritosActivity extends MainBarActivity {

	Button btEnviarInteresse, addFavoritos;
	SiDIMControllerServer controller;
	ImovelMobile imovel;
	SharedPreferences globalPrefs;

	ImageView imgMain;
	TextView txtTitle, txtBairro, txtCidadeEstado, txtQtdsDorm, txtQtdGarag, txtAreaConstruida, txtAreaTotal,
			txtObservacao, txtIntencao, txtPreco;
	FavoritosDAO favoritosDao;

	DrawableConnectionManager drawManager;
	
	boolean cameFavoritosScreen, removeFavor, wasAddFavor;

	//private ArrayList<String> list;

	ProgressDialog progressDialog;

	Gallery gallery;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

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
			
			// Verificar se veio do favoritos e setar aqui
			if(favoritosDao.getImovel(imovel) != null){
				addFavoritos.setText("- Favoritos");	
				removeFavor = true;
			}
			
			cameFavoritosScreen = getIntent().getBooleanExtra("cameFavoriteScreen", false);
		}

		controller = SiDIMControllerServer.getInstance(this);

		btEnviarInteresse = (Button) findViewById(R.id.visualizarImovelButtonInteresse);
		gallery = (Gallery) findViewById(R.id.visualizarImovelGallery);
		imgMain = (ImageView) findViewById(R.id.visualizarImovelImageMain);



		if(imovel != null && imovel.getFotos() != null && imovel.getFotos().size() > 0){
			if (SessionUserSidim.images.containsKey(imovel.getFotos().get(0))) {				
					imgMain.setImageBitmap(SessionUserSidim.images.get(imovel.getFotos().get(0)));				
			} else {
				if(cameFavoritosScreen){
					LoadImagesSDCard.getFirstImageFromSdCard(imovel.getFotos(),imgMain);
					//imgMain.setImageDrawable(draw);
				}else{
					drawManager.fetchDrawableOnThread(imovel.getFotos().get(0), imgMain);
				}
			}
			if(cameFavoritosScreen){
				FotoGaleriaFavorAdapter adapter = new FotoGaleriaFavorAdapter(this, imovel.getFotos());
				gallery.setAdapter(adapter);
			} else {
				FotoGaleriaAdapter adapter = new FotoGaleriaAdapter(this, imovel.getFotos());
				gallery.setAdapter(adapter);
			}

		}

		
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// imgMain.setImageResource(list.get(arg2));
				if (SessionUserSidim.images.containsKey(imovel.getFotos().get(arg2))) {
					imgMain.setImageBitmap(SessionUserSidim.images.get(imovel.getFotos().get(arg2)));
				} else {
			//		if(cameFavoritosScreen){
						LoadImagesSDCard.getImageFromSdCard(imovel.getFotos().get(arg2),imgMain);
						//imgMain.setImageDrawable(draw);
//					}else{
//						drawManager.fetchDrawableOnThread(imovel.getFotos().get(arg2), imgMain);
//					}
				}

			}
		});

		btEnviarInteresse.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enviarInteresse();

			}
		});

		
		if(cameFavoritosScreen || removeFavor){
			addFavoritos.setText("- Favoritos");			
		}
		addFavoritos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(cameFavoritosScreen || removeFavor){
					removeFavoritos();
					removeFavor = false;
				} else {
					addFavoritos();
					
				}

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

		
		
				try {
					controller.enviarInteresse(interesse);
					
					Bundle data = new Bundle();
					data.putString("enviado", "Interesse Enviado, Em breve entraremos em contato");
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
		addFavoritos = (Button) findViewById(R.id.visualizarImovelBtAddFavoritos);
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
				+ imovel.getSuites() + " suíte");
		txtAreaConstruida.setText(imovel.getAreaConstruida() + "m2 Construído");
		txtAreaTotal.setText(imovel.getAreaTotal() + "m2 Total");
	
		txtObservacao.setText(imovel.getDescricao());
		txtQtdGarag.setText(imovel.getGaragens() + "");
	}

	
	@SuppressLint({ "HandlerLeak", "HandlerLeak", "HandlerLeak", "HandlerLeak" })
	private void addFavoritos(){
		
		final SpinnerAdapter adapterFotos = (SpinnerAdapter) gallery.getAdapter();
		
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
					Crouton.makeText(instance, msgerror, Style.ALERT).show();
				}else {
					Crouton.makeText(instance, "Imóvel adicionado aos favoritos", Style.CONFIRM).show();
					
					addFavoritos.setText("- Favoritos");
					cameFavoritosScreen = true;
					wasAddFavor = true;
				}
				
				gallery.setAdapter(adapterFotos);
				

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
				} catch (NumberFormatException e2){
					
					Bundle data = new Bundle();
					data.putString("msgerror", "Por favor conecte-se a uma rede para adicionar favoritos");
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
		};
		
		thread.start();
	}
	
	@SuppressLint("HandlerLeak")
	private void removeFavoritos(){
		
		instance.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				showDialog("Removendo...");
			}
		});

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(final Message msgs) {

				String msgerror = msgs.getData().getString("msgerror");
				if (ValidacaoGeral.validaCampoVazio(msgerror)) {					
					Crouton.makeText(instance, msgerror, Style.ALERT).show();
					
				}else {
					
					Crouton.makeText(instance, "Imóvel removido dos favoritos", Style.CONFIRM).show();					
					addFavoritos.setText("+Favoritos");
					cameFavoritosScreen = false;
					wasAddFavor = false;
				}
				
				
				
				if (progressDialog != null) {
					progressDialog.dismiss();
				}

			}
		};

		Thread thread = new Thread() {

			@Override
			public void run() {

				
					favoritosDao.removerImovel(imovel);
					handler.sendEmptyMessage(2);				
					
				
			}
		};
		
		thread.start();
	}
	
	private void showDialog(String msg) {
		progressDialog = ProgressDialog.show(this, "", msg,
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
