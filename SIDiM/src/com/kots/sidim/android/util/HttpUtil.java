package com.kots.sidim.android.util;

/**
 * 
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class HttpUtil {

    private static final int DEFAULT_TIMEOUT = 15;
    

    public static String doHttpGet(String url) throws Exception {
        return doHttpGet(url, DEFAULT_TIMEOUT);
    }

    public static String doHttpGet(String urlParam, Integer timeout) {

        // Send data
        URL url;
        HttpURLConnection conn = null;
        BufferedReader br;
        StringBuilder resp = new StringBuilder();
        String respStr = null;
        try {
            url = new URL(urlParam);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDefaultUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setConnectTimeout(timeout * 1000);
            conn.setReadTimeout(timeout * 1000);
            conn.setUseCaches(false);
                                    
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));            

            String line;

            while ((line = br.readLine()) != null) {
                resp.append(line);
            }

            br.close();

            respStr = resp.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if(e.getMessage().contains("403")){
                System.out.println("[ERROR] 403 Forbidden");
            }
            else{
                Log.e("Erro ao conectar", e.getMessage());
                e.printStackTrace();
            }
        }
        
        return respStr;
    }
    
    
    public static String doHttpPost(String url, String json) {
    	
    	BufferedReader br;
    	StringBuilder resp = new StringBuilder();
        String respStr = null;
    	
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(json,"utf-8"));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json; charset=utf-8");
            
            HttpResponse response = new DefaultHttpClient().execute(httpPost);
            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            
            String line;

            while ((line = br.readLine()) != null) {
                resp.append(line);
            }

            br.close();

            respStr = resp.toString();
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return respStr;
    }
}

