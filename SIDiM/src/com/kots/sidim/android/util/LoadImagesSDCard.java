package com.kots.sidim.android.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
	
	public static Drawable getFirstImageFromSdCard(List<String> images){
		
		
		
		for(String image : images){
			
			try{
				
				String path = Environment.getExternalStorageDirectory() + File.separator + "appsidim" + File.separator + image;
				Drawable draw = BitmapDrawable.createFromPath(path);
				
				return draw;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 		
		
		return null;
	}
	
	public static Drawable getImageFromSdCard(String nameImage){
		
		
		
		
			
			try{
				
				String path = Environment.getExternalStorageDirectory() + File.separator + "appsidim" + File.separator + nameImage;
				Drawable draw = BitmapDrawable.createFromPath(path);
				
				return draw;
			} catch (Exception e) {
				e.printStackTrace();
			}
		 		
		
		return null;
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
