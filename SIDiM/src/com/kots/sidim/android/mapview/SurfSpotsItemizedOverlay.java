package com.kots.sidim.android.mapview;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class SurfSpotsItemizedOverlay extends ItemizedOverlay {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	Context mContext;

	public SurfSpotsItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	public SurfSpotsItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}
	
	public void addListOverlay(List<OverlayItem> overlays) {
		mOverlays.addAll(overlays);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	@Override
	protected boolean onTap(int index) {
//		OverlayItem item = mOverlays.get(index);
//		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
//		dialog.setTitle(item.getTitle());
//		dialog.setMessage(item.getSnippet());
//		dialog.show();
		
//		Intent it = new Intent(mContext,PointDialogActivity.class);
//		mContext.startActivity(it);
		return true;
	}

}