package com.kots.sidim.android.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.widget.ImageView;

import com.kots.sidim.android.R;
import com.kots.sidim.android.exception.SiDIMException;
import com.kots.sidim.android.model.Bairro;
import com.kots.sidim.android.model.Cidade;
import com.kots.sidim.android.model.Estado;
import com.kots.sidim.android.model.Imovel;
import com.kots.sidim.android.model.TipoImovel;
import com.kots.sidim.android.util.DrawableConnectionManager;
import com.kots.sidim.android.util.SessionUserSidim;

public class FavoritosDAO {

	private SQLiteDatabase database;
	private SiDIMSQLiteHelper dbHelper;
	DrawableConnectionManager drawManager;
	private Context context;

	// private String [] allColumns = {ZeeweTVSQLiteHelper.COLUMN_KEY,
	// ZeeweTVSQLiteHelper.COLUMN_VALUE,
	// ZeeweTVSQLiteHelper.COLUMN_EXP_DATE, ZeeweTVSQLiteHelper.COLUMN_TYPE};

	public FavoritosDAO(Context context) {
		dbHelper = new SiDIMSQLiteHelper(context);
		drawManager = new DrawableConnectionManager();
		this.context = context;
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public int insertFavorito(Imovel imovel, List<String> photos) throws SiDIMException {

		removerImovel(imovel);
		
		ContentValues values = new ContentValues();
		values.put(SiDIMSQLiteHelper.COLUMN_ID_MOVEL, imovel.getIdImovel());
		values.put(SiDIMSQLiteHelper.COLUMN_ESTADO, imovel.getEstado().getUf());
		values.put(SiDIMSQLiteHelper.COLUMN_TIPO_IMOVEL, imovel.getTipoImovel()
				.getDescricao());
		values.put(SiDIMSQLiteHelper.COLUMN_CIDADE, imovel.getCidade()
				.getNome());
		values.put(SiDIMSQLiteHelper.COLUMN_BAIRRO, imovel.getBairro()
				.getNome());
		values.put(SiDIMSQLiteHelper.COLUMN_QTD_DORM, imovel.getDormitorios());
		values.put(SiDIMSQLiteHelper.COLUMN_AREA, imovel.getArea());
		values.put(SiDIMSQLiteHelper.COLUMN_QTD_GARAG, imovel.getGaragens());
		values.put(SiDIMSQLiteHelper.COLUMN_QTD_SUITES, imovel.getSuites());
		values.put(SiDIMSQLiteHelper.COLUMN_CEP, imovel.getCep());
		values.put(SiDIMSQLiteHelper.COLUMN_RUA, imovel.getRua());
		values.put(SiDIMSQLiteHelper.COLUMN_PRECO, imovel.getPreco()
				.doubleValue());
		values.put(SiDIMSQLiteHelper.COLUMN_DESCRICAO, imovel.getDescricao());
		
				

				
		
		
		// SALVAR FOTOS NO SDCARD
		try{
			File root = new File(Environment.getExternalStorageDirectory(), "appsidim");
	        if (!root.exists()) {
	            root.mkdirs();
	        }
	        
	        FileOutputStream os;
	        String allPhotos = "";
	        ImageView image = new ImageView(context);
	        Bitmap bitmap;
	        int i = 0;
	        for(String urlImage : photos){
	        	if(SessionUserSidim.images.containsKey(urlImage)){
	        		bitmap = SessionUserSidim.images.get(urlImage);
	    		} else {
	    			bitmap = drawManager.fetchDrawable(urlImage);                                      
	    		}
	        	
	        	
	        	
	        	String nameFile = imovel.getIdImovel() + "_"  + i + ".jpg";
	        	File file = new File(root, imovel.getIdImovel() + nameFile);
	        	allPhotos = nameFile + ";";
	        	os = new FileOutputStream(file);
	        	
	        	bitmap.compress(CompressFormat.JPEG, 100, os);
	            os.flush();
	            os.close();
	            
	            image.setImageResource(R.drawable.casabonita);
	            
	            i++;
	            
	        }
	        
	        values.put(SiDIMSQLiteHelper.COLUMN_FOTOS, allPhotos);
	        
	        
	     } catch(Exception e)
	     {
	    	 throw new SiDIMException("Seu SDCard n‹o est‡ dispon’vel para salvar as fotos");
	     }
		
		
		open();		
		database.insert(SiDIMSQLiteHelper.TABLE_FAVORITOS, null, values);

		Cursor cursor = database.query(
				SiDIMSQLiteHelper.TABLE_FAVORITOS,
				null,
				SiDIMSQLiteHelper.COLUMN_ID_MOVEL + " = "
						+ imovel.getIdImovel(), null, null, null, null);
		int id = 0;
		if (cursor.moveToFirst()) {
			id = cursor.getInt(SiDIMSQLiteHelper.COLUMN_ID_MOVEL_INDEX);
		}
		cursor.close();
		close();

		
		
		
		return 0;

	}

	public Imovel getResponseAPI(int idMovel) {

		Cursor cursor = database.query(SiDIMSQLiteHelper.TABLE_FAVORITOS, null,
				SiDIMSQLiteHelper.COLUMN_ID_MOVEL + " = " + idMovel, null,
				null, null, null);
		Imovel imovel = null;

		if (cursor.moveToFirst()) {

			try {
				imovel = new Imovel();
				imovel.setIdImovel(cursor
						.getInt(SiDIMSQLiteHelper.COLUMN_ID_MOVEL_INDEX));
				imovel.setEstado(new Estado(cursor
						.getString(SiDIMSQLiteHelper.COLUMN_ID_MOVEL_INDEX), ""));
				imovel.setTipoImovel(new TipoImovel((short) 0, cursor
						.getString(SiDIMSQLiteHelper.COLUMN_TIPO_IMOVEL_INDEX)));
				imovel.setCidade(new Cidade(0, null, cursor
						.getString(SiDIMSQLiteHelper.COLUMN_CIDADE_INDEX), ""));
				imovel.setBairro(new Bairro(0, null, cursor
						.getString(SiDIMSQLiteHelper.COLUMN_BAIRRO_INDEX), ""));
				imovel.setDormitorios((short) cursor
						.getInt(SiDIMSQLiteHelper.COLUMN_QTD_DORM_INDEX));
				imovel.setArea(cursor
						.getInt(SiDIMSQLiteHelper.COLUMN_AREA_INDEX));
				imovel.setSuites((short) cursor
						.getInt(SiDIMSQLiteHelper.COLUMN_QTD_SUITES_INDEX));
				imovel.setGaragens((byte) cursor
						.getInt(SiDIMSQLiteHelper.COLUMN_QTD_SUITES_INDEX));
				imovel.setCep(cursor
						.getString(SiDIMSQLiteHelper.COLUMN_CEP_INDEX));
				imovel.setRua(cursor
						.getString(SiDIMSQLiteHelper.COLUMN_RUA_INDEX));
				imovel.setPreco(new BigDecimal(cursor
						.getDouble(SiDIMSQLiteHelper.COLUMN_PRECO_INDEX)));
				imovel.setDescricao(cursor
						.getString(SiDIMSQLiteHelper.COLUMN_DESCRICAO_INDEX));

			} catch (Exception e) {
				imovel = null;
			} finally {
				cursor.close();
			}
		}

		return imovel;

	}

	public void removerImovel(Imovel imovel) {

		open();
		
		database.delete(
				SiDIMSQLiteHelper.TABLE_FAVORITOS,
				SiDIMSQLiteHelper.COLUMN_ID_MOVEL + " = "
						+ imovel.getIdImovel(), null);
		close();

	}

	public List<Imovel> getImoveis() {

		open();
		
		List<Imovel> imoveis = new ArrayList<Imovel>();

		Cursor cursor = database.query(SiDIMSQLiteHelper.TABLE_FAVORITOS, null,
				null, null, null, null, null);
		Imovel imovel = null;

		while (cursor.moveToNext()) {

			try {
				imovel = new Imovel();
				imovel.setIdImovel(cursor
						.getInt(SiDIMSQLiteHelper.COLUMN_ID_MOVEL_INDEX));
				imovel.setEstado(new Estado(cursor
						.getString(SiDIMSQLiteHelper.COLUMN_ESTADO_INDEX), ""));
				imovel.setTipoImovel(new TipoImovel((short) 0, cursor
						.getString(SiDIMSQLiteHelper.COLUMN_TIPO_IMOVEL_INDEX)));
				imovel.setCidade(new Cidade(0, null, cursor
						.getString(SiDIMSQLiteHelper.COLUMN_CIDADE_INDEX), ""));
				imovel.setBairro(new Bairro(0, null, cursor
						.getString(SiDIMSQLiteHelper.COLUMN_BAIRRO_INDEX), ""));
				imovel.setDormitorios((short) cursor
						.getInt(SiDIMSQLiteHelper.COLUMN_QTD_DORM_INDEX));
				imovel.setArea(cursor
						.getInt(SiDIMSQLiteHelper.COLUMN_AREA_INDEX));
				imovel.setSuites((short) cursor
						.getInt(SiDIMSQLiteHelper.COLUMN_QTD_SUITES_INDEX));
				imovel.setGaragens((byte) cursor
						.getInt(SiDIMSQLiteHelper.COLUMN_QTD_GARAG_INDEX));
				imovel.setCep(cursor
						.getString(SiDIMSQLiteHelper.COLUMN_CEP_INDEX));
				imovel.setRua(cursor
						.getString(SiDIMSQLiteHelper.COLUMN_RUA_INDEX));
				imovel.setPreco(new BigDecimal(cursor
						.getDouble(SiDIMSQLiteHelper.COLUMN_PRECO_INDEX)));
				imovel.setDescricao(cursor
						.getString(SiDIMSQLiteHelper.COLUMN_DESCRICAO_INDEX));
				imovel.setFotoslocal(cursor.getString(SiDIMSQLiteHelper.COLUMN_FOTOS_INDEX));

				imoveis.add(imovel);
				
				
			} catch (Exception e) {
				imovel = null;
			} finally {
				cursor.close();
				close();
				
			}
		}

		return imoveis;

	}
	
	

}
