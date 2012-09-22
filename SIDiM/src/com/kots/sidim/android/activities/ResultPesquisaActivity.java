package com.kots.sidim.android.activities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kots.sidim.android.R;
import com.kots.sidim.android.adapter.ImovelAdapter;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.model.Bairro;
import com.kots.sidim.android.model.Cidade;
import com.kots.sidim.android.model.Estado;
import com.kots.sidim.android.model.Imovel;
import com.kots.sidim.android.model.TipoImovel;

public class ResultPesquisaActivity extends MainBarActivity {
	
	ListView listResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_pesquisa,
				ConfigGlobal.MENU_INDEX_PESQUISAR_IMOVEL);
		
		listResult = (ListView) findViewById(R.id.resultPesquisaListImoveis);
		
		List<Imovel> imoveis = new ArrayList<Imovel>();
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		imoveis.add(getNewImovel());
		
		ImovelAdapter adapter = new ImovelAdapter(this, imoveis);
		
		listResult.setAdapter(adapter);
		
		listResult.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				startActivity(new Intent(getBaseContext(), VisualizarImovelActivity.class));
				
				
			}
		});		
		
		
		
	}
	
public Imovel getNewImovel(){
		
		Imovel imovel = new Imovel();
		imovel.setIdImovel(1);
		imovel.setEstado(new Estado("SP","São Paulo"));
		imovel.setCidade(new Cidade(1, new Estado("SP","São Paulo"), "Campinas", ""));
		imovel.setArea(40);
		imovel.setDormitorios((short)3);
		imovel.setSuites((short) 2);
		imovel.setGaragens((byte) 1);
		imovel.setBairro(new Bairro(1, null, "Jd. Aurélia", ""));
		imovel.setDescricao("Linda Casa");
		imovel.setPreco(new BigDecimal(130000));
		imovel.setTipoImovel(new TipoImovel((short) 1, "Casa"));
		
		return imovel;
		
	}

}
