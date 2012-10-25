package com.kots.sidim.android.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacaoGeral {

	
	public static boolean validaCampoVazio(String text){
		
		if(text != null && !text.equals(""))
			return true;
		return false;
		
	}
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
	}
	
}
