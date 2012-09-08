package com.kots.sidim.android.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
public class SessionUserSidim {
        
    public static Map<String,Bitmap> images = new HashMap<String, Bitmap>();

    public static void clearImages(){
        images.clear();
        System.gc();
    }
               
    
}
