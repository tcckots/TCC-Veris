package com.kots.sidim.web.webservice.util;
import java.util.HashMap;
import java.util.Map;


public class Util {

	public static Map<String, String> getQueryMap(String query)  
    {   
	    query = query.split("\\?")[1];
        char firstChar = query.charAt(0);
        if(firstChar == '&'){
            query = query.substring(1);
        }
        
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();  
        for (String param : params)  
        {  
            String name = param.split("=")[0];  
            String value = param.split("=")[1];  
            map.put(name, value);  
        }  
        return map;  
    } 

}
