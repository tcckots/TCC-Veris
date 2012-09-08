package com.kots.sidim.android.activities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kots.sidim.android.R;
import com.kots.sidim.android.adapter.ImovelAdapter;
import com.kots.sidim.android.adapter.MenuAdapter;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.model.Bairro;
import com.kots.sidim.android.model.Cidade;
import com.kots.sidim.android.model.Estado;
import com.kots.sidim.android.model.Imovel;
import com.kots.sidim.android.model.TipoImovel;
import com.kots.sidim.android.views.CoverFlow;

public class MenuPrincipalActivity extends Activity {

	Activity instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		instance = this;
		
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

		
		
        ListView listMenu = (ListView) findViewById(R.id.menuListOptionHome);
        listMenu.setAdapter(new MenuAdapter(this, ConfigGlobal.menuList));
		
		ListView list = (ListView) findViewById(R.id.menulist3dImoveis);
		list.setDivider( null ); 
        list.setAdapter(adapter);
        

//        coverFlow.setAdapter(new ImageAdapter(this));
//
//        ImageAdapter coverImageAdapter =  new ImageAdapter(this);
//        
//        coverImageAdapter.createReflectedImages();
//        
//        coverFlow.setAdapter(coverImageAdapter);
//        
//        coverFlow.setSpacing(-25);
//        coverFlow.setSelection(4, true);
//        coverFlow.setAnimationDuration(1000);


        listMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				switch(arg2){
				case ConfigGlobal.MENU_INDEX_FAVORITOS: startActivity(new Intent(instance,FavoritosActivity.class)); break;
				case ConfigGlobal.MENU_INDEX_CONFIGURACOES: startActivity(new Intent(instance,ConfiguracoesActivity.class)); break;
				case ConfigGlobal.MENU_INDEX_PESQUISAR_IMOVEL: startActivity(new Intent(instance,PesquisarImovelActivity.class)); break;
				
				}
				
				
			}
		});
        
//		Button btConfig = (Button) findViewById(R.id.menuPrincipalBtConfig);
//		btConfig.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				startActivity(new Intent(instance, ConfiguracoesActivity.class));
//
//			}
//		});
//		
//		
//		Button btfavor = (Button) findViewById(R.id.menuPrincipalBtFavor);
//		btfavor.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				startActivity(new Intent(instance, FavoritosActivity.class));
//
//			}
//		});

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
