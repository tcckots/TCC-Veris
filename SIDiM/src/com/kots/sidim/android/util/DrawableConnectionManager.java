package com.kots.sidim.android.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;


public class DrawableConnectionManager {
	

	    public DrawableConnectionManager() {
	        SessionUserSidim.images = new HashMap<String, Bitmap>();
	    }


	    public void fetchDrawableOnThread(final String urlString, final ImageView imageView) {
	        if (SessionUserSidim.images.containsKey(urlString)) {
	            imageView.setImageBitmap(SessionUserSidim.images.get(urlString));
	            Log.i("DrawableConnectionManagger", "Already download " + urlString);
	        }

	        final Handler handler = new Handler() {
	            @Override
	            public void handleMessage(Message message) {
	                Log.i("DrawableConnectionManagger", "Download Image " + urlString);
	                imageView.setImageBitmap((Bitmap) message.obj);
	                //imageView.setImageResource(R.drawable.ch_london_2012);
	            }
	        };

	        Thread thread = new Thread() {
	            @Override
	            public void run() {
	                Bitmap drawable;
                    try {
                        drawable = drawableFromUrl(urlString);
                        SessionUserSidim.images.put(urlString, drawable);
                        Message message = handler.obtainMessage(1, drawable);
                        handler.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
	                
	            }
	        };
	        thread.start();
	    }

	    
	    
	    public static Bitmap drawableFromUrl(String url) throws IOException {
	        Bitmap x;

	        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	        connection.connect();
	        InputStream input = connection.getInputStream();

	        x = BitmapFactory.decodeStream(input);	        
	        return x;
	    }

	}

