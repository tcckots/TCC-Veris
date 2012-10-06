package com.kots.sidim.android.adapter;

import java.util.List;
import com.kots.sidim.android.R;
import com.kots.sidim.android.model.ImovelMobile;
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

public class ImovelAdapter extends BaseAdapter {
	
	private Context context;
	private List<ImovelMobile> imoveis;
	DrawableConnectionManager drawManager;
	
	
	public ImovelAdapter(Context context, List<ImovelMobile> imoveis){
		
		this.context = context;
		this.imoveis = imoveis;
		drawManager = new DrawableConnectionManager();
	
		
	}

	@Override
	public int getCount() {

		return imoveis.size();
	}

	@Override
	public Object getItem(int arg0) {

		return imoveis.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
				

		final ImovelMobile imovel = imoveis.get(arg0);
		
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.item_list, null);
	
		TextView txtTitleContent = (TextView) v.findViewById(R.id.favorItemInputTitle);
		txtTitleContent.setText(imovel.getBairro().getNome() + ", " + imovel.getTipoImovel().getDescricao() + imovel.getDormitorios() + " Dorm");
		
		TextView txtDescriptionContent = (TextView) v.findViewById(R.id.favorItemInputCity);
		txtDescriptionContent.setText(imovel.getCidade().getNome() + "-" + imovel.getCidade().getEstado().getUf());
		
		TextView txtPreco = (TextView) v.findViewById(R.id.favorItemInputPrice);
		txtPreco.setText("Venda " + ": " + imovel.getPreco().doubleValue());
		
		ImageView imgPreview = (ImageView) v.findViewById(R.id.favorItemImgFoto);
		
		
		
		
//		if(SessionUserSidim.images.containsKey(imovel.getIdImovel())){
//		    imgPreview.setImageBitmap(SessionUserSidim.images.get(imovel.getIdImovel()));
//		} else {
//		    drawManager.fetchDrawableOnThread("", imgPreview);                                   
//		}
		

		
		RelativeLayout linearDescription = (RelativeLayout) v.findViewById(R.id.favorItemLayoutDesc);
		linearDescription.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(context, ContentDetailsActivity.class);
//                        intent.putExtra("contentdetails", content);
//                        context.startActivity(intent);
                        
                    }
                });
		
		FrameLayout layoutPreview = (FrameLayout) v.findViewById(R.id.favorItemLayoutImg);
		layoutPreview.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                       
//                        Intent lVideoIntent = new Intent(null, Uri.parse("ytv://"+ content.getId()), context, OpenYouTubePlayerActivity.class);
//                        context.startActivity(lVideoIntent);
                        
                    }
                });									
		
		return v;
		
	}
	
	

}
