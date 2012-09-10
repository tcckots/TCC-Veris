package com.kots.sidim.android.mapview;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class PaduaOverlay extends Overlay {
	
	private Paint paint = new Paint();
	private final int imagemId;
	private final GeoPoint geoPoint;
	
	
	public PaduaOverlay(GeoPoint geoPoint, int resId){
		this.geoPoint = geoPoint;
		this.imagemId = resId;
	
	}
	
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent e, MapView mapView) {
		
		Log.i("BBBB", "TOUCHH");
		
		return super.onTouchEvent(e, mapView);
	}




	@Override
	public boolean onTap(GeoPoint arg0, MapView arg1) {
		
		Log.i("AAAA", "TAPPP");
		
		return super.onTap(arg0, arg1);
	}




	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow){
		super.draw(canvas, mapView, shadow);
		
		android.graphics.Point p = mapView.getProjection().toPixels(geoPoint, null);
		Bitmap bitmap = BitmapFactory.decodeResource(mapView.getResources(), this.imagemId);
		RectF r = new RectF(p.x,p.y,p.x+bitmap.getWidth(),p.y+bitmap.getHeight());
		canvas.drawBitmap(bitmap, null,r, paint);
	}

	

}
