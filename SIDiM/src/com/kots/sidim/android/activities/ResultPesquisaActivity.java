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
import com.kots.sidim.android.exception.SiDIMException;
import com.kots.sidim.android.model.Bairro;
import com.kots.sidim.android.model.Cidade;
import com.kots.sidim.android.model.Estado;
import com.kots.sidim.android.model.FiltroImovel;
import com.kots.sidim.android.model.ImovelMobile;
import com.kots.sidim.android.model.TipoImovel;
import com.kots.sidim.android.server.SiDIMControllerServer;

public class ResultPesquisaActivity extends MainBarActivity {
	
	ListView listResult;
	List<ImovelMobile> imoveis;
	FiltroImovel filtro;
	SiDIMControllerServer sidimController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_pesquisa,
				ConfigGlobal.MENU_INDEX_PESQUISAR_IMOVEL);
		
		sidimController = SiDIMControllerServer.getInstance(instance);
		
		if (getIntent() != null) {
			if (getIntent().getSerializableExtra("filtroimovel") instanceof FiltroImovel) {
				filtro = (FiltroImovel) getIntent().getSerializableExtra(
						"filtroimovel");
				
			}
		}
		
		if(filtro != null){
			
			try {
				imoveis = sidimController.buscarImoveis(filtro);
			} catch (SiDIMException e) {
				
			}
		}
		
		listResult = (ListView) findViewById(R.id.resultPesquisaListImoveis);
		
		
//		imoveis.add(getNewImovel());
//		imoveis.add(getNewImovel());
//		imoveis.add(getNewImovel());
//		imoveis.add(getNewImovel());
//		imoveis.add(getNewImovel());
//		imoveis.add(getNewImovel());
//		imoveis.add(getNewImovel());
//		imoveis.add(getNewImovel());
//		imoveis.add(getNewImovel());
//		imoveis.add(getNewImovel());
//		imoveis.add(getNewImovel());
//		imoveis.add(getNewImovel());
//		imoveis.add(getNewImovel());
		
		ImovelAdapter adapter = new ImovelAdapter(this, imoveis);
		
		listResult.setAdapter(adapter);
		
		listResult.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				 Intent intent = new Intent(getBaseContext(), VisualizarImovelActivity.class);
                 intent.putExtra("imoveldetalhes", imoveis.get(arg2));
				startActivity(intent);
				
				
			}
		});		
		
		
		
	}
	
//public Imovel getNewImovel(){
//		
//		Imovel imovel = new Imovel();
//		imovel.setIdImovel(1);
//		imovel.setEstado(new Estado("SP","São Paulo"));
//		imovel.setCidade(new Cidade(1, new Estado("SP","São Paulo"), "Sumaré", ""));
//		imovel.setArea(40);
//		imovel.setDormitorios((short)3);
//		imovel.setSuites((short) 2);
//		imovel.setGaragens((byte) 1);
//		imovel.setBairro(new Bairro(1, null, "Jd. Amanda", ""));
//		imovel.setDescricao("Linda Casa");
//		imovel.setPreco(new BigDecimal(130000));
//		imovel.setTipoImovel(new TipoImovel((short) 1, "Casa"));
//		
//		return imovel;
//		
//	}

}
