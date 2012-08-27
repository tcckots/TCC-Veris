package com.kots.sidim.android.config;

public class ValidacaoGeral {

	
	public static boolean validaCampoVazio(String text){
		
		if(text != null && !text.equals(""))
			return true;
		return false;
		
	}
	
}
