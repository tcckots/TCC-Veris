package com.kots.sidim.android.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.kots.sidim.android.R;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.views.WheelView;
import com.kots.sidim.android.views.adapters.ArrayWheelAdapter;

public class PesquisarImovelActivity extends MainBarActivity {

	Activity instance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pesquisarimovel,
				ConfigGlobal.MENU_INDEX_PESQUISAR_IMOVEL);
		
		instance = this;

		final WheelView city = (WheelView) findViewById(R.id.pesquisaWhellUf);
		city.setVisibleItems(3);

		final WheelView intencao = (WheelView) findViewById(R.id.pesquisarWhellIntencao);
		intencao.setVisibleItems(3);

		String[] ufs = { "AL", "AM", "SP", "SC", "RJ", "RS" };
		String[] sIntencao = { "Comprar", "Alugar", "Todos" };

		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				ufs);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(0);

		ArrayWheelAdapter<String> adapter2 = new ArrayWheelAdapter<String>(
				this, sIntencao);
		adapter.setTextSize(18);
		intencao.setViewAdapter(adapter2);
		intencao.setCurrentItem(0);

		LinearLayout linearBairro = (LinearLayout) findViewById(R.id.pesquisarLinearBairro);
		linearBairro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				String [] bairros = {"Jd. Aurelia", "Paulicéia","Centro"};
				showDialogBairros(bairros);
			}
		});

	}

	private void showDialogBairros(String[] bairrosSelec) {

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, bairrosSelec);

		// set up dialog
		final Dialog dialog = new Dialog(this, R.style.myDialogStyleSearch);

		dialog.setContentView(R.layout.dialog_bairro);
		dialog.setTitle("Bairros");

		final ListView list = (ListView) dialog
				.findViewById(R.id.dialogBairroListBairro);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(instance, "Para remover mantenha o bairro pressionado", Toast.LENGTH_SHORT).show();
				// Remover Item

			}
		});

		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String [] a = {"Campinas"};
				list.setAdapter(new ArrayAdapter<String>(instance,
						android.R.layout.simple_list_item_1, a));
				
				return false;
			}
		});
		dialog.show();
	}

}
