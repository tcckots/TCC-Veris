package com.kots.sidim.android.activities;
import java.util.List;
import android.os.Bundle;
import android.widget.ListView;
import com.kots.sidim.android.R;
import com.kots.sidim.android.adapter.ImovelFavoritoAdapter;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.dao.FavoritosDAO;
import com.kots.sidim.android.model.ImovelMobile;

public class FavoritosActivity extends MainBarActivity {
	
	FavoritosDAO favoritosDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favoritos, ConfigGlobal.MENU_INDEX_FAVORITOS);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		favoritosDao = new FavoritosDAO(instance);		
		List<ImovelMobile> imoveis = favoritosDao.getImoveis();		
		ImovelFavoritoAdapter adapter = new ImovelFavoritoAdapter(this, imoveis);
		
		ListView list = (ListView) findViewById(R.id.favoritosListResultados);
		list.setDivider( null ); 
        list.setAdapter(adapter);
	}
	
	

}
