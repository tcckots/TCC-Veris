package com.kots.sidim.android.adapter;

import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kots.sidim.android.R;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.model.TipoImovelMobile;

public class TipoImovelAdapter extends BaseAdapter {
	
	private Context context;
	private List<TipoImovelMobile> tipos;
	SharedPreferences globalPrefs;
	SharedPreferences.Editor editor;
	
	
	public TipoImovelAdapter(Context context, List<TipoImovelMobile> tipos){
		
		this.context = context;
		this.tipos = tipos;
		globalPrefs = context.getSharedPreferences(ConfigGlobal.GLOBAL_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		editor = globalPrefs.edit();
		
	}

	@Override
	public int getCount() {

		return tipos.size();
	}

	@Override
	public Object getItem(int arg0) {

		return tipos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
				

		final TipoImovelMobile tipo = tipos.get(arg0);
		
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.item_tipoimovel, null);
	
		TextView txtTipo = (TextView) v.findViewById(R.id.itemListTipoImovelTextTipo);
		txtTipo.setText(tipo.getDescricao());
		
		final CheckBox check = (CheckBox) v.findViewById(R.id.itemListTipoImovelCheck);
		check.setChecked(tipo.isCheck());
		
		check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				persistCheck(tipo, check);
				
			}
		});

		
		RelativeLayout linearDescription = (RelativeLayout) v.findViewById(R.id.itemListTipoImovelRelative);
		linearDescription.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                    	
                    	setCheckTipo(tipo, check);
                        
                    }
                });
		
											
		
		return v;
		
	}
	
	private void setCheckTipo(TipoImovelMobile tipo, CheckBox check){
		
		check.setChecked(!check.isChecked());
		persistCheck(tipo, check);
	}
	
	private void persistCheck(TipoImovelMobile tipo, CheckBox check){

		tipo.setCheck(check.isChecked());
		
	}
	
	public List<TipoImovelMobile> getList(){
		return tipos;
	}
	
	
	

}
