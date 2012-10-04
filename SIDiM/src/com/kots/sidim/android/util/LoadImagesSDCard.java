package com.kots.sidim.android.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

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
		File root = new File(Environment.getExternalStorageDirectory(), "appsidim");
		File imageFile;
		
		if(listNamesImages.length > 0){
			imageFile = new File(root, listNamesImages[0]);
			try{
				bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} 
	
	
				
	}

}
