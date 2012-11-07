package com.kots.sidim.android.adapter;

import java.text.DecimalFormat;
import java.util.List;
import com.kots.sidim.android.R;
import com.kots.sidim.android.activities.VisualizarImovelActivity;
import com.kots.sidim.android.model.ImovelMobile;
import com.kots.sidim.android.util.DrawableConnectionManager;
import com.kots.sidim.android.util.SessionUserSidim;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
//		
//		if(arg1 != null){
//			return arg1;
//		}
//		

		final ImovelMobile imovel = imoveis.get(arg0);
		
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(arg1 == null){
			arg1 = inflater.inflate(R.layout.item_list, null);
		}
	
		TextView txtTitleContent = (TextView) arg1.findViewById(R.id.favorItemInputTitle);
		txtTitleContent.setText(imovel.getBairro().getNome() + ", " + imovel.getTipoImovel().getDescricao() + " - " + imovel.getDormitorios() + " Dorm");
		
		TextView txtDescriptionContent = (TextView) arg1.findViewById(R.id.favorItemInputCity);
		txtDescriptionContent.setText(imovel.getCidade().getNome() + "-" + imovel.getCidade().getEstado().getUf());
		
		DecimalFormat df = new DecimalFormat("###,###,###.00");
		
		TextView txtPreco = (TextView) arg1.findViewById(R.id.favorItemInputPrice);
				
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
		
		ImageView imgPreview = (ImageView) arg1.findViewById(R.id.favorItemImgFoto);
		imgPreview.setImageResource(R.drawable.carregandofoto2);		
		
		if(imovel.getFotos() != null & imovel.getFotos().size() > 0){
			if(SessionUserSidim.images.containsKey(imovel.getFotos().get(0))){
			    imgPreview.setImageBitmap(SessionUserSidim.images.get(imovel.getFotos().get(0)));
			} else {
			    drawManager.fetchDrawableOnThread(imovel.getFotos().get(0), imgPreview);                                   
			}
		}
		

		
		RelativeLayout linearDescription = (RelativeLayout) arg1.findViewById(R.id.favorItemLayoutDesc);
		linearDescription.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, VisualizarImovelActivity.class);
                        intent.putExtra("imoveldetalhes", imovel);
        				context.startActivity(intent);
                        
                    }
                });
		
		FrameLayout layoutPreview = (FrameLayout) arg1.findViewById(R.id.favorItemLayoutImg);
		layoutPreview.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                       
                    	Intent intent = new Intent(context, VisualizarImovelActivity.class);
                        intent.putExtra("imoveldetalhes", imovel);
        				context.startActivity(intent);                    	
                    	
                    }
                });									
		
		return arg1;
		
	}
	
	

}
