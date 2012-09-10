package com.kots.sidim.android.activities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.kots.sidim.android.R;
import com.kots.sidim.android.adapter.ImovelAdapter;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.model.Bairro;
import com.kots.sidim.android.model.Cidade;
import com.kots.sidim.android.model.Estado;
import com.kots.sidim.android.model.Imovel;
import com.kots.sidim.android.model.TipoImovel;
import com.kots.sidim.android.views.CoverFlow;

public class FavoritosActivity extends MainBarActivity {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favoritos, ConfigGlobal.MENU_INDEX_FAVORITOS);
		
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
//		
//		ListView list = (ListView) findViewById(R.id.favorlist3dImoveis);
//		list.setDivider( null ); 
//        list.setAdapter(adapter);
		
	  CoverFlow coverFlow = (CoverFlow) findViewById(R.id.favorlist3dImoveis);	
      
      coverFlow.setAdapter(adapter);
      
      coverFlow.setSpacing(-25);
      coverFlow.setSelection(4, true);
      coverFlow.setAnimationDuration(1000);

						
	
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
