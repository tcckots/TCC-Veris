package com.kots.sidim.android.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class LoadImagesSDCard {
	
	public static void getImagesFromSdCard(String images, List<Bitmap> bitmaps){
		
		String[] listNamesImages = images.split(";");
		
		
		Bitmap bitmap = null;
		File root = new File(Environment.getExternalStorageDirectory(), "appsidim");
		File imageFile;
		
		for(int i = 0; i < listNamesImages.length; i++){
			imageFile = new File(root, listNamesImages[i]);
			bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
			bitmaps.add(bitmap);
		}
				
	}
	
	public static void getFirstImageFromSdCard(String images, Bitmap bitmap){
		
		String[] listNamesImages = images.split(";");
		
		
		//Bitmap bitmap = null;
		
		
		if(listNamesImages.length > 0){
			
			try{
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				String path = Environment.getExternalStorageDirectory().toString()  + File.separator + listNamesImages[0];
				bitmap = BitmapFactory.decodeFile(path,options);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 			
	}
	
	public static Bitmap ShowPicture(String fileName) { 
		
		String[] listNamesImages = fileName.split(";");
		
		File root = new File(Environment.getExternalStorageDirectory(), "appsidim");
		File imageFileimage = new File(root, listNamesImages[0]);
		
		if(imageFileimage.exists()){
			FileInputStream is = null; 
		    try { 
		        is = new FileInputStream(imageFileimage); 
		        Bitmap bitmap = BitmapFactory.decodeStream(is, null, null); 
			    return bitmap;
		        
		    } catch (FileNotFoundException e) {
		        Log.d("error: ",String.format( "ShowPicture.java file[%s]Not Found",fileName)); 
		        return null; 
		    }
			
		}
	     
	     return null;
	    
	    
	}
	
	

}
