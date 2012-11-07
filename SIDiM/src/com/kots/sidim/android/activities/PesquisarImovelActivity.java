package com.kots.sidim.android.activities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.kots.sidim.android.R;
import com.kots.sidim.android.adapter.TipoImovelAdapter;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.config.ValidacaoGeral;
import com.kots.sidim.android.exception.SiDIMException;
import com.kots.sidim.android.model.Bairro;
import com.kots.sidim.android.model.FiltroImovel;
import com.kots.sidim.android.model.TipoImovelMobile;
import com.kots.sidim.android.server.SiDIMControllerServer;
import com.kots.sidim.android.views.OnWheelChangedListener;
import com.kots.sidim.android.views.WheelView;
import com.kots.sidim.android.views.adapters.ArrayWheelAdapter;

import de.neofonie.mobile.app.android.widget.crouton.Crouton;
import de.neofonie.mobile.app.android.widget.crouton.Style;

@SuppressLint("HandlerLeak")
public class PesquisarImovelActivity extends MainBarActivity {

	Activity instance;

	WheelView estados, intencao;

	List<String> cidades = new ArrayList<String>();

	List<String> bairros = new ArrayList<String>();

	List<Bairro> bairrosToAutoComplete = new ArrayList<Bairro>();

	List<TipoImovelMobile> tipos = new ArrayList<TipoImovelMobile>();

	TextView txtBairros, txtTipos, txtPreco;

	AutoCompleteTextView /* autoEditCidades, */ autoEditBairros;
	
	Spinner spinnerCidade;

	SiDIMControllerServer controller;

	LinearLayout linearBairro, linearTipos;

	Button btPesquisar;

	FiltroImovel filtro;

	String[] ufs = { "SP" };
	String[] sIntencao = { "Comprar", "Alugar" };

	

	Button btDorm, btSuite, btGarag;
	
	SeekBar barPreco;
	
	ProgressBar progressBarCidade, progressBarBairro;
	ProgressDialog progressDialog;

	List<TipoImovelMobile> newListTipoImovelMobile;

	int faixaPreco;
	
	int valueNumberPicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pesquisarimovel,
				ConfigGlobal.MENU_INDEX_PESQUISAR_IMOVEL);

		instance = this;
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		findIds();
		controller = SiDIMControllerServer.getInstance(instance);
		prepareWhellViews();
		faixaPreco = 1;

		try {
			tipos = controller.getTipos();

		} catch (SiDIMException e) {

			e.printStackTrace();
		}

		linearBairro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showDialogBairros();
			}
		});

		linearTipos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showDialogTipos();
			}
		});

		barPreco.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

				if (progress < 20) {
					txtPreco.setText("AtŽ R$ 100.000,00");
					faixaPreco = 1;
				} else if (progress >= 20 && progress < 40) {
					txtPreco.setText("AtŽ R$ 150.000,00");
					faixaPreco = 2;
				} else if (progress >= 40 && progress < 60) {
					txtPreco.setText("AtŽ R$ 200.000,00");
					faixaPreco = 3;
				} else if (progress >= 60 && progress < 75) {
					txtPreco.setText("AtŽ R$ 500.000,00");
					faixaPreco = 4;
				} else if (progress >= 75 && progress < 85) {
					txtPreco.setText("AtŽ R$ 800.000,00");
					faixaPreco = 5;
				} else if (progress >= 85 && progress < 95) {
					txtPreco.setText("AtŽ R$ 1.000.000,00");
					faixaPreco = 6;
				} else if (progress >= 95) {
					txtPreco.setText("Acima de R$ 1.000.000,00");
					faixaPreco = 7;
				}

			}
		});
		
		barPreco.setProgress(21);

		btPesquisar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				filtro = new FiltroImovel();
				try{
					filtro.setCidade(spinnerCidade.getSelectedItem().toString());
				} catch(Exception e){
					filtro.setCidade("Campinas");
				}
				filtro.setUf(ufs[estados.getCurrentItem()]);

				ArrayList<String> filtroBairros = new ArrayList<String>();

				for (String sBairro : bairros) {
					filtroBairros.add(sBairro);
				}
				filtro.setIdsBairro(filtroBairros);

				ArrayList<Integer> filtroTipos = new ArrayList<Integer>();
				if (newListTipoImovelMobile != null
						&& newListTipoImovelMobile.size() > 0) {
					for (int i = 0; i < newListTipoImovelMobile.size(); i++) {
						filtroTipos.add((int) newListTipoImovelMobile.get(i)
								.getIdTipoImovel());
					}
				} else {
					filtroTipos.add(0);
				}
				filtro.setIdsTipo(filtroTipos);
				if (intencao.getCurrentItem() == 0) {
					filtro.setIntencao("C");
				} else {
					filtro.setIntencao("A");
				}

				try {
					filtro.setQtdDorm(Integer.parseInt(btDorm
							.getText().toString().trim()));
				} catch (Exception e) {
					filtro.setQtdDorm(0);
				}

				try {
					filtro.setQtdSuite(Integer.parseInt(btSuite
							.getText().toString().trim()));
				} catch (Exception e) {
					filtro.setQtdSuite(0);
				}

				try {
					filtro.setQtdGaragens(Integer.parseInt(btGarag
							.getText().toString().trim()));
				} catch (Exception e) {
					filtro.setQtdGaragens(0);
				}

				filtro.setFaixaPreco(faixaPreco);
				filtro.setIndexBuscas(0);

				Intent intent = new Intent(getBaseContext(),
						ResultPesquisaActivity.class);
				intent.putExtra("filtroimovel", filtro);
				startActivity(intent);

			}
		});
		
		//loadCidades(ufs[estados.getCurrentItem()]);


		spinnerCidade.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				
				if (!hasFocus) {
					// loadBairros(autoEditCidades.getText().toString());
				} else {
					
				}

			}
		});
	}

	private void showDialogTipos() {

		final Dialog dialog = new Dialog(this, R.style.myDialogStyleSearch);

		dialog.setContentView(R.layout.dialog_tipoimovel);
		dialog.setTitle("Tipos Im—veis");

		final ListView list = (ListView) dialog
				.findViewById(R.id.dialogTipoImovelList);
		TipoImovelAdapter adapter = new TipoImovelAdapter(instance, tipos);
		list.setAdapter(adapter);

		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {

				String sTipos = "Tipos Im—veis : ";
				newListTipoImovelMobile = getTiposCheckeds(tipos);

				if (newListTipoImovelMobile.size() == 0) {
					sTipos += "Todos";
				} else if (newListTipoImovelMobile.size() >= 1) {
					sTipos = newListTipoImovelMobile.get(0).getDescricao();
					if (newListTipoImovelMobile.size() >= 2) {
						sTipos += ","
								+ newListTipoImovelMobile.get(1).getDescricao();
						if (newListTipoImovelMobile.size() >= 3) {
							sTipos += ","
									+ newListTipoImovelMobile.get(2)
											.getDescricao();
							if (newListTipoImovelMobile.size() >= 4) {
								sTipos += "...";
							}
						}
					}
				}
				txtTipos.setText(sTipos);
				
			}
		});

		Button btConcluido = (Button) dialog
				.findViewById(R.id.dialogTipoImovelBtConcluido);
		btConcluido.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog.dismiss();

			}
		});

		Button btTodosTipos = (Button) dialog
				.findViewById(R.id.dialogTipoImovelBtTodosTipos);
		btTodosTipos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				for (TipoImovelMobile tipo : tipos) {
					tipo.setCheck(false);
				}

				dialog.dismiss();
			}
		});

		Button btLimpar = (Button) dialog
				.findViewById(R.id.dialogTipoImovelBtLimpar);
		btLimpar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				for (TipoImovelMobile tipo : tipos) {
					tipo.setCheck(false);
				}

				TipoImovelAdapter adapter = new TipoImovelAdapter(instance,
						tipos);
				list.setAdapter(adapter);

			}
		});

		dialog.show();

	}

	private void showDialogBairros() {

		fixMenu();
		
		final Dialog dialog = new Dialog(this, R.style.myDialogStyleSearch);

		dialog.setContentView(R.layout.dialog_bairro);
		dialog.setTitle("Bairros");

		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {

				String sBairros = "Bairros : ";

				if (bairros.size() == 0) {
					sBairros += "Todos";
				} else if (bairros.size() >= 1) {
					sBairros = bairros.get(0);
					if (bairros.size() >= 2) {
						sBairros += "," + bairros.get(1);
						if (bairros.size() >= 3) {
							sBairros += "," + bairros.get(2);
							if (bairros.size() >= 4) {
								sBairros += "...";
							}
						}
					}
				}
				txtBairros.setText(sBairros);
				fixMenuAppear();
			}
		});

		final ListView list = (ListView) dialog
				.findViewById(R.id.dialogBairroListBairro);
		updateBairros(list);

		autoEditBairros = (AutoCompleteTextView) dialog
				.findViewById(R.id.dialogBairroInputBairro);
		
		autoEditBairros.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				setMenuOn(true);
				
				
			}
		});
		
		progressBarBairro = (ProgressBar) dialog.findViewById(R.id.dialogBairroProgressBarBairro);

		Button btAdicionar = (Button) dialog
				.findViewById(R.id.dialogBairroBtAdd);
		btAdicionar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String bairro = autoEditBairros.getText().toString();
				if (ValidacaoGeral.validaCampoVazio(bairro)) {
					if (bairros.contains(bairro)) {
						Crouton.makeText(instance, "Bairro j‡ inserido", Style.ALERT).show();						
					} else {
						bairros.add(0, bairro);
						updateBairros(list);
					}
					autoEditBairros.setText("");
				}

			}
		});

		Button btConcluido = (Button) dialog
				.findViewById(R.id.dialogBairroBtConcluido);
		btConcluido.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog.dismiss();

			}
		});

		Button btTodosBairros = (Button) dialog
				.findViewById(R.id.dialogBairroBtTodosBairros);
		btTodosBairros.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				bairros.clear();
				dialog.dismiss();
			}
		});

		Button btLimpar = (Button) dialog
				.findViewById(R.id.dialogBairroBtLimpar);
		btLimpar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				bairros.clear();
				updateBairros(list);

			}
		});

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {				
				Toast.makeText(instance, "Para remover mantenha o bairro pressionado", Toast.LENGTH_SHORT).show();
			}
		});

		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				bairros.remove(arg2);
				list.setAdapter(new ArrayAdapter<String>(instance,
						android.R.layout.simple_list_item_1, bairros));

				return false;
			}
		});
		dialog.show();
		loadBairros(spinnerCidade.getSelectedItem().toString());
	}

	private void updateBairros(ListView listView) {

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, bairros);
		listView.setAdapter(adapter);

	}

	private void prepareWhellViews() {
		estados = (WheelView) findViewById(R.id.pesquisaWhellUf);
		estados.setVisibleItems(3);

		intencao = (WheelView) findViewById(R.id.pesquisarWhellIntencao);
		intencao.setVisibleItems(3);

		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				ufs);
		adapter.setTextSize(18);
		estados.setViewAdapter(adapter);
		estados.setCurrentItem(24);
		loadCidades(ufs[estados.getCurrentItem()]);

		ArrayWheelAdapter<String> adapter2 = new ArrayWheelAdapter<String>(
				this, sIntencao);
		adapter.setTextSize(18);
		intencao.setViewAdapter(adapter2);
		intencao.setCurrentItem(0);

		intencao.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {

				if (newValue > 0) {
					barPreco.setEnabled(false);
					barPreco.setVisibility(View.INVISIBLE);
					txtPreco.setVisibility(View.INVISIBLE);
				} else {
					barPreco.setEnabled(true);
					barPreco.setVisibility(View.VISIBLE);
					txtPreco.setVisibility(View.VISIBLE);
				}

			}
		});

		estados.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {


			}
		});

		estados.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (!hasFocus) {
					loadCidades(ufs[estados.getCurrentItem()]);
				}

			}
		});
	}

	public List<TipoImovelMobile> getTiposCheckeds(List<TipoImovelMobile> list) {

		ArrayList<TipoImovelMobile> newList = new ArrayList<TipoImovelMobile>();

		for (TipoImovelMobile tipo : tipos) {

			if (tipo.isCheck()) {
				newList.add(tipo);
			}
		}

		return newList;

	}


	public void loadCidades(final String uf) {
		
		instance.runOnUiThread(new Runnable() {
			@Override
			public void run() {
								
				progressBarCidade.setVisibility(View.VISIBLE);
			}
		});
		

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(final Message msgs) {

				String msgerror = msgs.getData().getString("msgerror");
				if (ValidacaoGeral.validaCampoVazio(msgerror)) {
					Crouton.makeText(instance, msgerror, Style.ALERT).show();					
					
					if(!cidades.contains("Campinas"))
						cidades.add("Campinas");
				}
				
				
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						instance,
						android.R.layout.simple_dropdown_item_1line,cidades);
				
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				spinnerCidade.setAdapter(adapter);
				
				
				progressBarCidade.setVisibility(View.GONE);
				progressBarCidade.clearFocus();
				
				
				

			}
		};

		Thread thread = new Thread() {

			@Override
			public void run() {

				try {

					
					cidades = controller.getCidades(uf);
					Message message = handler.obtainMessage(1);
					handler.sendMessage(message);

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

	@SuppressLint({ "HandlerLeak", "HandlerLeak", "HandlerLeak" })
	public void loadBairros(final String cidade) {
		
		instance.runOnUiThread(new Runnable() {
			@Override
			public void run() {
								
				progressBarBairro.setVisibility(View.VISIBLE);
			}
		});

		final Handler handler2 = new Handler() {
			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(final Message msgs) {

				String msgerror = msgs.getData().getString("msgerror");
				if (ValidacaoGeral.validaCampoVazio(msgerror)) {

					Crouton.makeText(instance, msgerror, Style.ALERT).show();
				} else {
					autoEditBairros.setAdapter((ArrayAdapter<String>) msgs.obj);
				}
				
				progressBarBairro.setVisibility(View.INVISIBLE);

			}
		};

		Thread thread2 = new Thread() {

			@Override
			public void run() {

				try {

					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							instance,
							android.R.layout.simple_dropdown_item_1line,
							controller.getBairro(cidade));
					Message message = handler.obtainMessage(1, adapter);
					handler2.sendMessage(message);
					//autoEditBairros.setAdapter(adapter);

				} catch (SiDIMException e) {
					Bundle data = new Bundle();
					data.putString("msgerror", e.getMessage());
					Message msg = new Message();
					msg.setData(data);
					handler2.sendMessage(msg);
				}

			}
		};
		
		thread2.start();

	}
	
	private void showDialogNumberPicker(final Button buttonNumberPicker, String titleDialog) {

		//valueNumberPicker = valueOrig;
		final Dialog dialog = new Dialog(this, R.style.myDialogStyleSearch);

		dialog.setContentView(R.layout.dialog_numberpicker);		
		dialog.setTitle(titleDialog);
		Button btAdd = (Button) dialog.findViewById(R.id.dialogNumberPickerBtAdd);
		Button btRemove = (Button) dialog.findViewById(R.id.dialogNumberPickerBtRemove);
		final EditText editNumber = (EditText) dialog.findViewById(R.id.dialogNumberPickerEditNumber);
		editNumber.setEnabled(false);
		
		
		
		btAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(valueNumberPicker < 100){
					valueNumberPicker++;
				}
				
				editNumber.setText(valueNumberPicker+"");
				
			}
		});
		
		btRemove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				if(valueNumberPicker > 0){
					valueNumberPicker--;
				}
				
				if(valueNumberPicker > 0){
					editNumber.setText(valueNumberPicker+"");
				} else {
					editNumber.setText("");
				}
				
			}
		});
		

		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {

				
				
			}
		});

		Button btConcluido = (Button) dialog
				.findViewById(R.id.dialogNumberPickerBtConcluido);
		btConcluido.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(valueNumberPicker > 0){
					buttonNumberPicker.setText(valueNumberPicker+"");
				} else {
					buttonNumberPicker.setText("Todos");
				}
				
				dialog.dismiss();

			}
		});

		

		dialog.setOnShowListener(new OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
				
				if(valueNumberPicker > 0){
					editNumber.setText(valueNumberPicker+"");
				}
				
			}
		});
		
		dialog.show();

	}

	private void findIds() {

		txtBairros = (TextView) findViewById(R.id.pesquisarTextBairros);
		txtTipos = (TextView) findViewById(R.id.pesquisarTxtTipoImovel);
		spinnerCidade = (Spinner) findViewById(R.id.pesquisarSpinnerCidade);
		linearBairro = (LinearLayout) findViewById(R.id.pesquisarLinearBairro);
		linearTipos = (LinearLayout) findViewById(R.id.pesquisarLinearTipoImovel);
		txtPreco = (TextView) findViewById(R.id.pesquisarTxtPreco);
		barPreco = (SeekBar) findViewById(R.id.pesquisarSeekBarPreco);
		btPesquisar = (Button) findViewById(R.id.pesquisarBtPesquisar);
		
		btDorm = (Button) findViewById(R.id.pesquisarBtDorm);
		btSuite = (Button) findViewById(R.id.pesquisarBtSuite);
		btGarag = (Button) findViewById(R.id.pesquisarBtGaragem);
		
		progressBarCidade = (ProgressBar) findViewById(R.id.pesquisaProgressBarCidade);
		progressBarCidade.setVisibility(View.INVISIBLE);
		progressBarCidade.setClickable(false);
		progressBarCidade.setEnabled(false);
				
		
		btDorm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				
				try{
					valueNumberPicker = Integer.parseInt(btDorm.getText().toString().trim());
				} catch (Exception e) {
					valueNumberPicker = 0;
				}
				
				showDialogNumberPicker(btDorm,"Quantidade Dormit—ros");
							
			}
				
		});
		
		btSuite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				
				try{
					valueNumberPicker = Integer.parseInt(btSuite.getText().toString().trim());
				} catch (Exception e) {
					valueNumberPicker = 0;
				}
				
				showDialogNumberPicker(btSuite,"Quantidade Su’tes");
							
			}
				
		});
		
		btGarag.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				
				try{
					valueNumberPicker = Integer.parseInt(btGarag.getText().toString().trim());
				} catch (Exception e) {
					valueNumberPicker = 0;
				}
				
				showDialogNumberPicker(btGarag, "Quantidade Garagens");
							
			}
				
		});
		
		
			
				
				
	

	}

	
	@Override
	  protected void onDestroy() {
	    // Workaround until there's a way to detach the Activity from Crouton while
	    // there are still some in the Queue.
	    Crouton.clearCroutonsForActivity(this);
	    super.onDestroy();
	  }

}
