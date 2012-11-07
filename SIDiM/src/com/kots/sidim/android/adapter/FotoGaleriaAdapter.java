package com.kots.sidim.android.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.kots.sidim.android.R;
import com.kots.sidim.android.util.DrawableConnectionManager;
import com.kots.sidim.android.util.SessionUserSidim;

public class FotoGaleriaAdapter extends BaseAdapter {
	
	private Context context;
	private List<String> list;
	DrawableConnectionManager drawManager;
	
	
	public FotoGaleriaAdapter(Context context, List<String> list){
		
		this.context = context;
		this.list = list;
		drawManager = new DrawableConnectionManager();	
		
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
				
		if(arg1 != null){
			return arg1;
		}

		final String url = list.get(arg0);	
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.foto_item_galeria, null);
		
		ImageView imgOption = (ImageView) v.findViewById(R.id.fotogaleriaitem);
		
		if(SessionUserSidim.images.containsKey(url)){
			imgOption.setImageBitmap(SessionUserSidim.images.get(url));
		} else {
		    drawManager.fetchDrawableOnThread(url, imgOption);                                       
		}
													
		
		return v;
		
	}
	
	

}
