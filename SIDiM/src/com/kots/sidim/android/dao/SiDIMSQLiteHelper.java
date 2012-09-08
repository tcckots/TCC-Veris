package com.kots.sidim.android.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SiDIMSQLiteHelper extends SQLiteOpenHelper {

	
	

	
	
	
    
    public static final String TABLE_FAVORITOS = "imoveisfavoritos";
    
    
    public static final String COLUMN_ID_MOVEL = "idmovel";
    public static final int COLUMN_ID_MOVEL_INDEX = 0;
    
    public static final String COLUMN_ESTADO = "estado";
    public static final int COLUMN_ESTADO_INDEX = 1;
    
    public static final String COLUMN_TIPO_IMOVEL = "tipomovel";
    public static final int COLUMN_EXP_DATE_INDEX = 2;
    
    public static final String COLUMN_CIDADE = "cidade";
    public static final int COLUMN_CIDADE_INDEX = 3;
    
    public static final String COLUMN_BAIRRO = "bairro";
    public static final int COLUMN_BAIRRO_INDEX = 4;
    
    public static final String COLUMN_QTD_DORM = "qtddorm";
    public static final int COLUMN_QTD_DORM_INDEX = 5;
    
    public static final String COLUMN_AREA = "area";
    public static final int COLUMN_AREA_INDEX = 6;
    
    public static final String COLUMN_QTD_GARAG = "qtdgarag";
    public static final int COLUMN_QTD_GARAG_INDEX = 7;
    
    public static final String COLUMN_QTD_SUITES = "qtdsuites";
    public static final int COLUMN_QTD_SUITES_INDEX = 8;
    
    public static final String COLUMN_CEP = "cep";
    public static final int COLUMN_CEP_INDEX = 9;
    
    public static final String COLUMN_RUA = "rua";
    public static final int COLUMN_RUA_INDEX = 10;
    
    public static final String COLUMN_PRECO = "qtddorm";
    public static final int COLUMN_PRECO_INDEX = 11;
    
    public static final String COLUMN_DESCRICAO = "descricao";
    public static final int COLUMN_DESCRICAO_INDEX = 12;
    
    public static final String COLUMN_FOTOS = "fotos";
    public static final int COLUMN_FOTOS_INDEX = 13;
    
    
    
    private static final String DATABASE_NAME = "sidim.db";
    private static final int DATABASE_VERSION = 1;
    
    
 // Database creation sql statement
    private static final String DATABASE_CREATE 	= "create table "
                    + TABLE_FAVORITOS 				+ "(" 
                    + COLUMN_ID_MOVEL 				+ " int primary key, " 
                    + COLUMN_ESTADO   				+ " text," 
                    + COLUMN_TIPO_IMOVEL   			+ " text,"
                    + COLUMN_CIDADE				    + " text,"
                    + COLUMN_BAIRRO				    + " text,"
                    + COLUMN_QTD_DORM       	    + " int,"
                    + COLUMN_AREA				    + " int,"
                    + COLUMN_QTD_GARAG      	    + " int,"
                    + COLUMN_QTD_SUITES			    + " int,"
                    + COLUMN_CEP 				    + " text,"
                    + COLUMN_RUA				    + " text,"
                    + COLUMN_PRECO				    + " number,"
                    + COLUMN_DESCRICAO   		    + " text,"
                    + COLUMN_FOTOS                  + " text);";
    
    
    
    
    public SiDIMSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITOS);
        onCreate(db);

    }

}
