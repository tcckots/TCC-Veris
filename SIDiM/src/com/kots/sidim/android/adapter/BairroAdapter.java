package com.kots.sidim.android.adapter;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import android.view.View;

import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;


import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.model.Bairro;


public class BairroAdapter extends BaseAdapter {
	
	private Context context;
	private List<Bairro> bairros;
	SharedPreferences globalPrefs;
	SharedPreferences.Editor editor;
	
	
	public BairroAdapter(Context context, List<Bairro> bairros){
		
		this.context = context;
		this.bairros = bairros;
		globalPrefs = context.getSharedPreferences(ConfigGlobal.GLOBAL_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		editor = globalPrefs.edit();
		
	}

	@Override
	public int getCount() {

		return bairros.size();
	}

	@Override
	public Object getItem(int arg0) {

		return bairros.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
				

		Bairro bairro = bairros.get(arg0);
					
		TextView txtBairro = new TextView(context);
		txtBairro.setText(bairro.getNome());	

		return txtBairro;
	}
	
}
