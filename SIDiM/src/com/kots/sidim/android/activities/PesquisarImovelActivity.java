package com.kots.sidim.android.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
	
	List<Bairro> bairrosAutoComplete = new ArrayList<Bairro>();	
	
	List<TipoImovelMobile> tipos = new ArrayList<TipoImovelMobile>();

	TextView txtBairros, txtTipos, txtPreco;
	
	AutoCompleteTextView autoEditCidades;
	
	SiDIMControllerServer controller;

	String[] ufs = { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO",
			"MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ",
			"RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" };
	String[] sIntencao = { "Comprar", "Alugar", "Todos" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pesquisarimovel,
				ConfigGlobal.MENU_INDEX_PESQUISAR_IMOVEL);

		instance = this;
		controller = SiDIMControllerServer.getInstance(instance);
		prepareWhellViews();
		
		
		
		autoEditCidades = (AutoCompleteTextView) findViewById(R.id.pesquisarAutoEditCidades);
		
		
		try {
			tipos = controller.getTipos();
			
			
			
			
		} catch (SiDIMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tipos.add(new TipoImovelMobile((short) 1, "Casa", true));
		tipos.add(new TipoImovelMobile((short) 2, "Apartamento", false));
		tipos.add(new TipoImovelMobile((short) 2, "Chacára", false));
		tipos.add(new TipoImovelMobile((short) 2, "Terreno", false));
		tipos.add(new TipoImovelMobile((short) 2, "Sítio", false));
		tipos.add(new TipoImovelMobile((short) 2, "Fazenda", false));
		tipos.add(new TipoImovelMobile((short) 2, "Comercial", false));

		

		txtBairros = (TextView) findViewById(R.id.pesquisarTextBairros);
		txtTipos = (TextView) findViewById(R.id.pesquisarTxtTipoImovel);

		LinearLayout linearBairro = (LinearLayout) findViewById(R.id.pesquisarLinearBairro);
		linearBairro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showDialogBairros();
			}
		});
		
		LinearLayout linearTipos = (LinearLayout) findViewById(R.id.pesquisarLinearTipoImovel);
		linearTipos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showDialogTipos();
			}
		});
		
		txtPreco = (TextView) findViewById(R.id.pesquisarTxtPreco);
		
		SeekBar barPreco = (SeekBar) findViewById(R.id.pesquisarSeekBarPreco);
		barPreco.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				if(progress < 30){
					txtPreco.setText("Até R$ 150.000,00");
				} else if(progress >= 30 && progress < 60){
					txtPreco.setText("Até R$ 200.000,00");
				} else if(progress >= 60 && progress < 80 ){
					txtPreco.setText("Até R$ 400.000,00");
				} else if(progress >= 80 && progress < 98){
					txtPreco.setText("Até R$ 800.000,00");
				} else if(progress >= 98){
					txtPreco.setText("Qualquer Preço");
				} 
				
				
			}
		});
		
		Button btPesquisar = (Button) findViewById(R.id.pesquisarBtPesquisar);
		btPesquisar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(new Intent(getBaseContext(),ResultPesquisaActivity.class));
				
			}
		});
	}
	
	private void showDialogTipos(){

		
		
		
		final Dialog dialog = new Dialog(this, R.style.myDialogStyleSearch);

		dialog.setContentView(R.layout.dialog_tipoimovel);
		dialog.setTitle("Tipos Imóveis");
		
		final ListView list = (ListView) dialog
				.findViewById(R.id.dialogTipoImovelList);
		TipoImovelAdapter adapter = new TipoImovelAdapter(instance, tipos);
		list.setAdapter(adapter);
		

		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				
				String sTipos = "Tipos Imóveis : ";
				List<TipoImovelMobile> newList = getTiposCheckeds(tipos);

				if (newList.size() == 0) {
					sTipos += "Todos";
				} else if (newList.size() >= 1) {
					sTipos = newList.get(0).getDescricao();
					if (newList.size() >= 2) {
						sTipos += "," + newList.get(1).getDescricao();
						if (newList.size() >= 3) {
							sTipos += "," + newList.get(2).getDescricao();
							if (newList.size() >= 4) {
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
				
				for(TipoImovelMobile tipo : tipos){
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

				for(TipoImovelMobile tipo : tipos){
					tipo.setCheck(false);
				}
				
				TipoImovelAdapter adapter = new TipoImovelAdapter(instance, tipos);
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

		final AutoCompleteTextView autoComplBairro = (AutoCompleteTextView) dialog
				.findViewById(R.id.dialogBairroInputBairro);

		Button btAdicionar = (Button) dialog
				.findViewById(R.id.dialogBairroBtAdd);
		btAdicionar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String bairro = autoComplBairro.getText().toString();
				if (ValidacaoGeral.validaCampoVazio(bairro)) {
					if(bairros.contains(bairro)){
						Toast.makeText(instance, "Bairro já inserido", Toast.LENGTH_LONG).show();
					} else {
						bairros.add(0, bairro);
						updateBairros(list);					
					}					
					autoComplBairro.setText("");					
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

		ArrayWheelAdapter<String> adapter2 = new ArrayWheelAdapter<String>(
				this, sIntencao);
		adapter.setTextSize(18);
		intencao.setViewAdapter(adapter2);
		intencao.setCurrentItem(0);
		
		estados.addChangingListener(new OnWheelChangedListener() {
			
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				
				loadCidades(ufs[newValue]);
				
			}
		});
	}
	
	public List<TipoImovelMobile> getTiposCheckeds(List<TipoImovelMobile> list){
		
		ArrayList<TipoImovelMobile> newList = new ArrayList<TipoImovelMobile>();
		
		for(TipoImovelMobile tipo: tipos){
			
			if(tipo.isCheck()){
				newList.add(tipo);
			}			
		}
		
		return newList;
		
	}
	
	public void loadCidades(String uf){
		
		String[] arrayCidades;
		
		try {
			cidades = controller.getCidades(uf);
			arrayCidades = new String[cidades.size()];
			for(int i=0; i < cidades.size(); i++){
				arrayCidades[i] = cidades.get(i).getNome();
			}
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arrayCidades);
			
			autoEditCidades.setAdapter(adapter);
		} catch (SiDIMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void loadBairros(String cidade, AutoCompleteTextView viewAutoComplete){
		
		String[] arrayBairros;
		
		try {
			bairrosAutoComplete = controller.getBairro(cidade);
			arrayBairros = new String[cidades.size()];
			for(int i=0; i < bairrosAutoComplete.size(); i++){
				arrayBairros[i] = bairrosAutoComplete.get(i).getNome();
			}
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arrayBairros);
			viewAutoComplete.setAdapter(adapter);
		
		} catch (SiDIMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
