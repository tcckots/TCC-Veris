package com.kots.sidim.android.adapter;

import java.util.List;
import com.kots.sidim.android.R;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.model.Imovel;
import com.kots.sidim.android.util.DrawableConnectionManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {
	
	private Context context;
	private String[] menu;
	DrawableConnectionManager drawManager;
	
	
	public MenuAdapter(Context context, String[] menu){
		
		this.context = context;
		this.menu = menu;
			
		
	}

	@Override
	public int getCount() {

		return menu.length;
	}

	@Override
	public Object getItem(int arg0) {

		return menu[arg0];
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
				

		final String item = menu[arg0];
		
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.item_menu, null);
	
		TextView text = (TextView) v.findViewById(R.id.menuTextOption);
		text.setText(item);				
		
		ImageView imgOption = (ImageView) v.findViewById(R.id.menuImgIcon);
		imgOption.setImageResource(ConfigGlobal.getResourceMenu(arg0));													
		
		return v;
		
	}
	
	

}
