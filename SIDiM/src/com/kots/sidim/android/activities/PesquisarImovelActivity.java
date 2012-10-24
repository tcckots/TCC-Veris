package com.kots.sidim.android.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.kots.sidim.android.R;
import com.kots.sidim.android.adapter.TipoImovelAdapter;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.config.ValidacaoGeral;
import com.kots.sidim.android.exception.SiDIMException;
import com.kots.sidim.android.model.Bairro;
import com.kots.sidim.android.model.Cidade;
import com.kots.sidim.android.model.FiltroImovel;
import com.kots.sidim.android.model.TipoImovelMobile;
import com.kots.sidim.android.server.SiDIMControllerServer;
import com.kots.sidim.android.views.OnWheelChangedListener;
import com.kots.sidim.android.views.WheelView;
import com.kots.sidim.android.views.adapters.ArrayWheelAdapter;

public class PesquisarImovelActivity extends MainBarActivity {

	Activity instance;

	WheelView estados, intencao;

	List<Cidade> cidades = new ArrayList<Cidade>();

	List<String> bairros = new ArrayList<String>();

	List<Bairro> bairrosToAutoComplete = new ArrayList<Bairro>();

	List<TipoImovelMobile> tipos = new ArrayList<TipoImovelMobile>();

	TextView txtBairros, txtTipos, txtPreco;

	AutoCompleteTextView autoEditCidades, autoEditBairros;

	SiDIMControllerServer controller;

	LinearLayout linearBairro, linearTipos;

	Button btPesquisar;

	FiltroImovel filtro;

	String[] ufs = { "SP" };
	String[] sIntencao = { "Comprar", "Alugar" };

	EditText ediTextQtdQuartos, ediTextQtdSuites, editTextGaragens;

	SeekBar barPreco;

	List<TipoImovelMobile> newListTipoImovelMobile;

	int faixaPreco;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pesquisarimovel,
				ConfigGlobal.MENU_INDEX_PESQUISAR_IMOVEL);

		instance = this;
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
				filtro.setCidade(autoEditCidades.getText().toString());
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
					filtro.setQtdDorm(Integer.parseInt(ediTextQtdQuartos
							.getText().toString().trim()));
				} catch (Exception e) {
					filtro.setQtdDorm(0);
				}

				try {
					filtro.setQtdSuite(Integer.parseInt(ediTextQtdSuites
							.getText().toString().trim()));
				} catch (Exception e) {
					filtro.setQtdSuite(0);
				}

				try {
					filtro.setQtdGaragens(Integer.parseInt(editTextGaragens
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

		//autoEditCidades.setThreshold(3);
		autoEditCidades.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				
				if (!hasFocus) {
					// loadBairros(autoEditCidades.getText().toString());
				} else {
					loadCidades(ufs[estados.getCurrentItem()]);
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

		// set up dialog
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
			}
		});

		final ListView list = (ListView) dialog
				.findViewById(R.id.dialogBairroListBairro);
		updateBairros(list);

		autoEditBairros = (AutoCompleteTextView) dialog
				.findViewById(R.id.dialogBairroInputBairro);

		Button btAdicionar = (Button) dialog
				.findViewById(R.id.dialogBairroBtAdd);
		btAdicionar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String bairro = autoEditBairros.getText().toString();
				if (ValidacaoGeral.validaCampoVazio(bairro)) {
					if (bairros.contains(bairro)) {
						Toast.makeText(instance, "Bairro j‡ inserido",
								Toast.LENGTH_LONG).show();
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
				Toast.makeText(instance,
						"Para remover mantenha o bairro pressionado",
						Toast.LENGTH_SHORT).show();
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
		loadBairros(autoEditCidades.getText().toString());
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

				// loadCidades(ufs[newValue]);

			}
		});

		estados.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				Toast.makeText(instance, "Estados FOCUS " + hasFocus,
						Toast.LENGTH_LONG).show();
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

	@SuppressWarnings("unchecked")
	public void loadCidades(final String uf) {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(final Message msgs) {

				String msgerror = msgs.getData().getString("msgerror");
				if (ValidacaoGeral.validaCampoVazio(msgerror)) {
					Toast.makeText(instance, msgerror, Toast.LENGTH_LONG)
							.show();
				} else {
					autoEditCidades.setAdapter((ArrayAdapter<String>) msgs.obj);
				}

			}
		};

		Thread thread = new Thread() {

			@Override
			public void run() {

				try {

					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							instance,
							android.R.layout.simple_dropdown_item_1line,
							controller.getCidades(uf));
					Message message = handler.obtainMessage(1, adapter);
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

	public void loadBairros(final String cidade) {

		final Handler handler2 = new Handler() {
			@Override
			public void handleMessage(final Message msgs) {

				String msgerror = msgs.getData().getString("msgerror");
				if (ValidacaoGeral.validaCampoVazio(msgerror)) {
					Toast.makeText(instance, msgerror, Toast.LENGTH_LONG)
							.show();
				} else {
					autoEditBairros.setAdapter((ArrayAdapter<String>) msgs.obj);
				}

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

	private void findIds() {

		txtBairros = (TextView) findViewById(R.id.pesquisarTextBairros);
		txtTipos = (TextView) findViewById(R.id.pesquisarTxtTipoImovel);
		autoEditCidades = (AutoCompleteTextView) findViewById(R.id.pesquisarAutoEditCidades);
		linearBairro = (LinearLayout) findViewById(R.id.pesquisarLinearBairro);
		linearTipos = (LinearLayout) findViewById(R.id.pesquisarLinearTipoImovel);
		txtPreco = (TextView) findViewById(R.id.pesquisarTxtPreco);
		barPreco = (SeekBar) findViewById(R.id.pesquisarSeekBarPreco);
		btPesquisar = (Button) findViewById(R.id.pesquisarBtPesquisar);
		ediTextQtdQuartos = (EditText) findViewById(R.id.pesquisarInputQtdDorm);
		ediTextQtdSuites = (EditText) findViewById(R.id.pesquisarInputQtdSuites);
		editTextGaragens = (EditText) findViewById(R.id.pesquisarInputQtdGaragem);
	}

}
