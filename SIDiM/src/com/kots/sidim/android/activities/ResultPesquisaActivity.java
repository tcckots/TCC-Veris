package com.kots.sidim.android.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.kots.sidim.android.R;
import com.kots.sidim.android.adapter.ImovelAdapter;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.config.ValidacaoGeral;
import com.kots.sidim.android.exception.SiDIMException;
import com.kots.sidim.android.model.FiltroImovel;
import com.kots.sidim.android.model.ImovelMobile;
import com.kots.sidim.android.server.SiDIMControllerServer;
import com.kots.sidim.android.util.SessionUserSidim;

import de.neofonie.mobile.app.android.widget.crouton.Crouton;
import de.neofonie.mobile.app.android.widget.crouton.Style;

public class ResultPesquisaActivity extends MainBarActivity {

	ListView listResult;
	List<ImovelMobile> imoveis;
	List<ImovelMobile> newImoveis;
	FiltroImovel filtro;
	SiDIMControllerServer sidimController;
	ProgressBar progressBar;
	ImovelAdapter adapter;
	ProgressDialog progressDialog;
	boolean buscando, paraBusca;
	
	LinearLayout layoutLoading;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_pesquisa,
				ConfigGlobal.MENU_INDEX_PESQUISAR_IMOVEL);

		paraBusca = false;
		
		sidimController = SiDIMControllerServer.getInstance(instance);
		
		imoveis = new ArrayList<ImovelMobile>();
		adapter = new ImovelAdapter(instance, imoveis);
		
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.progress_bar, null);
		layoutLoading = (LinearLayout) v;
		progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
		
	//	layoutLoading = new LinearLayout(getApplicationContext());
	//	layoutLoading.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
	//	progressBar.setLayoutParams( new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
	//	layoutLoading.addView(progressBar);


		if (getIntent() != null) {
			if (getIntent().getSerializableExtra("filtroimovel") instanceof FiltroImovel) {
				filtro = (FiltroImovel) getIntent().getSerializableExtra(
						"filtroimovel");

			}
		}

		if (filtro != null) {

			instance.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					progressBar.setVisibility(View.VISIBLE);
					
					showDialog();
				}
			});
			
			

			final Handler handler = new Handler() {
				@Override
				public void handleMessage(final Message msgs) {

					String msgerror = msgs.getData().getString("msgerror");
					if (ValidacaoGeral.validaCampoVazio(msgerror)) {						
						Crouton.makeText(instance, msgerror, Style.ALERT).show();
					} else {
						List<ImovelMobile> newImoveis = (List<ImovelMobile>) msgs.obj;
						if (newImoveis != null && newImoveis.size() > 0) {
							imoveis.addAll(newImoveis);	
						adapter = new ImovelAdapter(instance, imoveis);
							
						listResult.setAdapter(adapter);
						adapter.notifyDataSetChanged();
						
						}
					}


					//progressBar.setVisibility(View.INVISIBLE);
					
					if(progressDialog != null){
						progressDialog.dismiss();
					}
					
					buscando = false;

				}
			};

			Thread thread = new Thread() {

				@Override
				public void run() {

					try {
						buscando = true;
						newImoveis = sidimController.buscarImoveis(filtro);
						filtro.setIndexBuscas(filtro.getIndexBuscas() + 15);
						
						Message message = handler.obtainMessage(1, newImoveis);
                        handler.sendMessage(message);
						

					} catch (SiDIMException e) {

						Bundle data = new Bundle();
						data.putString("msgerror", e.getMessage());
						Message msg = new Message();
						msg.setData(data);
						handler.sendMessage(msg);

					}

					//handler.sendEmptyMessage(2);

				}
			};

			thread.start();
		}

		listResult = (ListView) findViewById(R.id.resultPesquisaListImoveis);

		listResult.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				Log.i("ListView Results",
						"(firstVisibleItem + visibleItemCount) = "
								+ (firstVisibleItem + visibleItemCount));
				Log.i("ListView Results", "visibleItemCount="
						+ visibleItemCount + " totalIemCount=" + totalItemCount);
				
				if ((firstVisibleItem + visibleItemCount) == totalItemCount && !buscando && totalItemCount > 0 && !paraBusca) {
					buscando = true;
					Log.i("ListView Results", "ENTROU PRA CARRGAR");
					
					instance.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							//listResult.addFooterView(layoutLoading);
							progressBar.setVisibility(View.VISIBLE);
							
							//showDialog();
						}
					});

					final Handler handler2 = new Handler() {
						@Override
						public void handleMessage(final Message msgs) {

							String msgerror = msgs.getData().getString("msgerror");
							if (!ValidacaoGeral.validaCampoVazio(msgerror)) {
				
								if (imoveis != null && imoveis.size() > 0) {
									
									SessionUserSidim.clearImages();
									
									if(msgs.obj != null){
									
											List<ImovelMobile> newImoveis = (List<ImovelMobile>)msgs.obj ;
											if(newImoveis != null && newImoveis.size() > 0){
												adapter = new ImovelAdapter(instance,
														imoveis);
												listResult.setAdapter(adapter);
												adapter.notifyDataSetChanged();
												
												Parcelable state = listResult.onSaveInstanceState();
												listResult.onRestoreInstanceState(state);
											}
		
											buscando = false;									
											} else {
												buscando = true;
												
											}
									}																
							}

								progressBar.setVisibility(View.INVISIBLE);

						}
					};

					Thread thread = new Thread() {

						@Override
						public void run() {

							if (filtro != null) {

								try {
									newImoveis = sidimController.buscarImoveis(filtro);
									if(newImoveis == null || newImoveis.size() <= 0){
										paraBusca = true;
									} else {
										filtro.setIndexBuscas(filtro.getIndexBuscas() + 15);
										imoveis.addAll(newImoveis);
										Message message = handler2.obtainMessage(1, newImoveis);
										handler2.sendMessage(message);
									}
									
									
								} catch (SiDIMException e) {
									paraBusca = true;
									Bundle data = new Bundle();
									data.putString("msgerror", e.getMessage());
									Message msg = new Message();
									msg.setData(data);
									handler2.sendMessage(msg);

								}
								Message message = handler2.obtainMessage(1, null);
								handler2.sendMessage(message);
							}
						}
					};

					thread.start();

				}

			}
		});

		listResult.addFooterView(layoutLoading);


	}
	
	
	private void showDialog() {
		progressDialog = ProgressDialog.show(this, "", "Buscando Im—veis...",
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
