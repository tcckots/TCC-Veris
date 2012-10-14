package com.kots.sidim.android.adapter;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kots.sidim.android.R;
import com.kots.sidim.android.activities.VisualizarImovelActivity;
import com.kots.sidim.android.model.ImovelMobile;
import com.kots.sidim.android.util.LoadImagesSDCard;

public class ImovelFavoritoAdapter extends BaseAdapter {
	
	private Context context;
	private List<ImovelMobile> imoveis;
	
	
	
	public ImovelFavoritoAdapter(Context context, List<ImovelMobile> imoveis){
		
		this.context = context;
		this.imoveis = imoveis;
			
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
		txtDescriptionContent.setText(imovel.getCidade().getNome() + "-" + imovel.getEstado().getUf());				
		
		DecimalFormat df = new DecimalFormat("###,###,###.00");
		
		TextView txtPreco = (TextView) v.findViewById(R.id.favorItemInputPrice);
				
		String preco = "";
		
		if(imovel.getIntencao().equals("C")){
			
			if(imovel.getPreco().doubleValue() == 0){
				preco = "Compra: Entre em contato";
			} else {
				preco = "Compra: R$ " +  df.format(imovel.getPreco().doubleValue());
			}
			txtPreco.setText(preco);
		} else {
			if(imovel.getPreco().doubleValue() == 0){
				preco = "Aluga: Entre em contato";
			} else {
				preco = "Aluga: R$ " +  df.format(imovel.getPreco().doubleValue());
			}
			txtPreco.setText(preco);
		}
		
		ImageView imgPreview = (ImageView) v.findViewById(R.id.favorItemImgFoto);
//		

//		String path = Environment.getExternalStorageDirectory() + File.separator + "fotoss" + File.separator + "testeimage.png";
		if(imovel.getFotos() != null && imovel.getFotos().size() > 0){
			LoadImagesSDCard.getFirstImageFromSdCard(imovel.getFotos(),imgPreview);
			//imgPreview.setImageDrawable(draw);
		}
		
		
		
		RelativeLayout linearDescription = (RelativeLayout) v.findViewById(R.id.favorItemLayoutDesc);
		linearDescription.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, VisualizarImovelActivity.class);
                        intent.putExtra("imoveldetalhes", imovel);
                        intent.putExtra("cameFavoriteScreen", true);
        				context.startActivity(intent);
                        
                    }
                });
		
		FrameLayout layoutPreview = (FrameLayout) v.findViewById(R.id.favorItemLayoutImg);
		layoutPreview.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                       
                    	Intent intent = new Intent(context, VisualizarImovelActivity.class);
                        intent.putExtra("imoveldetalhes", imovel);
                        intent.putExtra("cameFavoriteScreen", true);
        				context.startActivity(intent);                    	
                    	
                    }
                });	
		
				
//		if(SessionUserSidim.images.containsKey(imovel.getIdImovel())){
//		    imgPreview.setImageBitmap(SessionUserSidim.images.get(imovel.getIdImovel()));
//		} else {
//		    drawManager.fetchDrawableOnThread("", imgPreview);                                   
//		}
		

		
								
		
		return v;
		
	}
	
	

}
