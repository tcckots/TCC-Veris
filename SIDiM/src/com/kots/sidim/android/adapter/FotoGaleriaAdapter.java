package com.kots.sidim.android.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kots.sidim.android.R;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.util.DrawableConnectionManager;

public class FotoGaleriaAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<Integer> list;
	DrawableConnectionManager drawManager;
	
	
	public FotoGaleriaAdapter(Context context, ArrayList<Integer> list){
		
		this.context = context;
		this.list = list;
			
		
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int arg0) {

		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
				

		final int item = list.get(arg0);	
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.foto_item_galeria, null);
		
		ImageView imgOption = (ImageView) v.findViewById(R.id.fotogaleriaitem);
		imgOption.setImageResource(item);													
		
		return v;
		
	}
	
	

}
