package com.kots.sidim.android.activities;

import java.util.ArrayList;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.SpinnerAdapter;

import com.kots.sidim.android.R;
import com.kots.sidim.android.adapter.FotoGaleriaAdapter;
import com.kots.sidim.android.adapter.ImovelAdapter;
import com.kots.sidim.android.config.ConfigGlobal;

public class VisualizarImovelActivity extends MainBarActivity {
		
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_visualizar_imovel,
					ConfigGlobal.MENU_INDEX_PESQUISAR_IMOVEL);
			
			Gallery gallery = (Gallery) findViewById(R.id.visualizarImovelGallery);
			
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(R.drawable.casabonita);
			list.add(R.drawable.casabonita);
			list.add(R.drawable.casabonita);
			list.add(R.drawable.casabonita);
			list.add(R.drawable.casabonita);
			
			FotoGaleriaAdapter adapter = new FotoGaleriaAdapter(this, list);
			
			gallery.setAdapter(adapter);
		}
	
	

}
