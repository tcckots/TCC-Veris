package com.kots.sidim.android.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.kots.sidim.android.model.Imovel;
import com.kots.sidim.android.util.LoadImagesSDCard;

public class ImovelFavoritoAdapter extends BaseAdapter {
	
	private Context context;
	private List<Imovel> imoveis;
	
	
	
	public ImovelFavoritoAdapter(Context context, List<Imovel> imoveis){
		
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
				

		final Imovel imovel = imoveis.get(arg0);
		
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.item_list, null);
	
		TextView txtTitleContent = (TextView) v.findViewById(R.id.favorItemInputTitle);
		txtTitleContent.setText(imovel.getBairro().getNome() + ", " + imovel.getTipoImovel().getDescricao() + imovel.getDormitorios() + " Dorm");
		
		TextView txtDescriptionContent = (TextView) v.findViewById(R.id.favorItemInputCity);
		txtDescriptionContent.setText(imovel.getCidade().getNome() + "-" + imovel.getEstado().getUf());
		
		TextView txtPreco = (TextView) v.findViewById(R.id.favorItemInputPrice);
		txtPreco.setText("Venda " + ": " + imovel.getPreco().doubleValue());
		
		ImageView imgPreview = (ImageView) v.findViewById(R.id.favorItemImgFoto);
		Bitmap image = null;//LoadImagesSDCard.ShowPicture(imovel.getFotoslocal());
		LoadImagesSDCard.getFirstImageFromSdCard(imovel.getFotoslocal(), image);
		
		
		imgPreview.setImageBitmap(image);
		
		
		
		
		
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
