package com.kots.sidim.android.dao;

import java.math.BigDecimal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.kots.sidim.android.model.Bairro;
import com.kots.sidim.android.model.Cidade;
import com.kots.sidim.android.model.Estado;
import com.kots.sidim.android.model.Imovel;
import com.kots.sidim.android.model.TipoImovel;

public class FavoritosDAO {
	
	
	 private SQLiteDatabase database;
	    private SiDIMSQLiteHelper dbHelper;
//	    private String [] allColumns = {ZeeweTVSQLiteHelper.COLUMN_KEY, ZeeweTVSQLiteHelper.COLUMN_VALUE,
//	            ZeeweTVSQLiteHelper.COLUMN_EXP_DATE, ZeeweTVSQLiteHelper.COLUMN_TYPE};
	    
	    public FavoritosDAO (Context context){
	        dbHelper = new SiDIMSQLiteHelper(context);
	    }
	    
	    public void open() throws SQLException {
	        database = dbHelper.getWritableDatabase();
	    }
	    
	    public void close(){
	        dbHelper.close();
	    }
	    
	    public int insertFavorito(Imovel imovel, String photos){
	        
	        ContentValues values = new ContentValues();
	        values.put(SiDIMSQLiteHelper.COLUMN_ID_MOVEL, imovel.getIdImovel());
	        values.put(SiDIMSQLiteHelper.COLUMN_ESTADO, imovel.getEstado().getUf());
	        values.put(SiDIMSQLiteHelper.COLUMN_TIPO_IMOVEL, imovel.getTipoImovel().getDescricao());
	        values.put(SiDIMSQLiteHelper.COLUMN_CIDADE, imovel.getCidade().getNome());
	        values.put(SiDIMSQLiteHelper.COLUMN_BAIRRO, imovel.getBairro().getNome());
	        values.put(SiDIMSQLiteHelper.COLUMN_QTD_DORM, imovel.getDormitorios());
	        values.put(SiDIMSQLiteHelper.COLUMN_AREA, imovel.getArea());
	        values.put(SiDIMSQLiteHelper.COLUMN_QTD_GARAG, imovel.getGaragens());
	        values.put(SiDIMSQLiteHelper.COLUMN_QTD_SUITES, imovel.getSuites());
	        values.put(SiDIMSQLiteHelper.COLUMN_CEP, imovel.getCep());
	        values.put(SiDIMSQLiteHelper.COLUMN_RUA, imovel.getRua());
	        values.put(SiDIMSQLiteHelper.COLUMN_PRECO, imovel.getPreco().doubleValue());
	        values.put(SiDIMSQLiteHelper.COLUMN_DESCRICAO, imovel.getDescricao());
	        values.put(SiDIMSQLiteHelper.COLUMN_FOTOS, photos);
	        
	        
	        
	        
	        database.insert(SiDIMSQLiteHelper.TABLE_FAVORITOS, null, values);
	        
	        Cursor cursor = database.query(SiDIMSQLiteHelper.TABLE_FAVORITOS, null, SiDIMSQLiteHelper.COLUMN_ID_MOVEL + " = " + imovel.getIdImovel(), null, null, null, null);
	        int id = 0;
	        if(cursor.moveToFirst()){
	            id = cursor.getInt(SiDIMSQLiteHelper.COLUMN_ID_MOVEL_INDEX);
	        }
	        cursor.close();
	        return id;
	                
	    }
	    
	    public Imovel getResponseAPI(int idMovel){
	        
	    	Cursor cursor = database.query(SiDIMSQLiteHelper.TABLE_FAVORITOS, null, SiDIMSQLiteHelper.COLUMN_ID_MOVEL + " = " + idMovel , null, null, null, null);
	        Imovel imovel = null;

	        if(cursor.moveToFirst()){	            

	            try{
	            	imovel = new Imovel();
	            	imovel.setIdImovel(cursor.getInt(SiDIMSQLiteHelper.COLUMN_ID_MOVEL_INDEX));
	            	imovel.setEstado(new Estado(cursor.getString(SiDIMSQLiteHelper.COLUMN_ID_MOVEL_INDEX), ""));
	            	imovel.setTipoImovel(new TipoImovel((short) 0,cursor.getString(SiDIMSQLiteHelper.COLUMN_TIPO_IMOVEL_INDEX)));
	            	imovel.setCidade(new Cidade(0,null,cursor.getString(SiDIMSQLiteHelper.COLUMN_CIDADE_INDEX),""));
	            	imovel.setBairro(new Bairro( 0,null, cursor.getString(SiDIMSQLiteHelper.COLUMN_BAIRRO_INDEX),""));
	            	imovel.setDormitorios((short) cursor.getInt(SiDIMSQLiteHelper.COLUMN_QTD_DORM_INDEX));
	            	imovel.setArea(cursor.getInt(SiDIMSQLiteHelper.COLUMN_AREA_INDEX));
	            	imovel.setSuites((short) cursor.getInt(SiDIMSQLiteHelper.COLUMN_QTD_SUITES_INDEX));
	            	imovel.setGaragens((byte) cursor.getInt(SiDIMSQLiteHelper.COLUMN_QTD_SUITES_INDEX));
	            	imovel.setCep(cursor.getString(SiDIMSQLiteHelper.COLUMN_CEP_INDEX));
	            	imovel.setRua(cursor.getString(SiDIMSQLiteHelper.COLUMN_RUA_INDEX));
	            	imovel.setPreco(new BigDecimal(cursor.getDouble(SiDIMSQLiteHelper.COLUMN_PRECO_INDEX)));
	            	imovel.setDescricao(cursor.getString(SiDIMSQLiteHelper.COLUMN_DESCRICAO_INDEX));
	                
	               



	            } catch(Exception e){ imovel = null; }
	              finally {
	                cursor.close();
	            }
	        }



	        return imovel;
	               
	    }
	    
	    public void removerImovel(Imovel imovel){
	        
	        database.delete(SiDIMSQLiteHelper.TABLE_FAVORITOS, SiDIMSQLiteHelper.COLUMN_ID_MOVEL + " = " + imovel.getIdImovel() , null);
	     
	    }
	

}
