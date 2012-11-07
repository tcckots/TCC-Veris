package com.kots.sidim.android.util;

import java.io.File;
import java.util.List;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;

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
	
	public static void getFirstImageFromSdCard(List<String> images, ImageView imageView){
		
		
		
		if(images == null || images.size() == 0)
			return;
			
		String image = images.get(0);
	
			
			if(SessionUserSidim.images.containsKey(image)){
				imageView.setImageBitmap(SessionUserSidim.images.get(image));										
				return;
			}
			
			try{
				
				String path = Environment.getExternalStorageDirectory() + File.separator + "appsidim" + File.separator + image;
				Drawable draw = BitmapDrawable.createFromPath(path);
				imageView.setImageDrawable(draw);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		
		
	}
	
	public static void getImageFromSdCard(String nameImage, ImageView image){
			
		if(SessionUserSidim.images.containsKey(nameImage)){
			image.setImageBitmap(SessionUserSidim.images.get(nameImage));										
			return;
		}
		
			try{
				
				String path = Environment.getExternalStorageDirectory() + File.separator + "appsidim" + File.separator + nameImage;
				Drawable draw = BitmapDrawable.createFromPath(path);
				SessionUserSidim.images.put(nameImage, ((BitmapDrawable) draw).getBitmap());
				image.setImageDrawable(draw);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
