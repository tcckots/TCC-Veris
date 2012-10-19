package com.kots.sidim.android.activities;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.ContactsContract.RawContacts;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.kots.sidim.android.R;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.mapview.Point;
import com.kots.sidim.android.mapview.SurfSpotsItemizedOverlay;

public class ContatoActivity extends MainBarActivity {
	
	MapView mapView;

	@Override
	public void onCreate(Bundle savedInstace){
		super.onCreate(savedInstace);
		setContentView(R.layout.activity_contato, ConfigGlobal.MENU_INDEX_CONTATO);

        
        Button btAddContato = (Button) findViewById(R.id.contatoBtAddContato);
        btAddContato.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				insert("Imobili‡ria", "P‡dua Im—veis", "01932525659", null,"paduaimoveis@paduaimovies.com.br","http://www.paduaimoveis.com.br","Campinas","Brasil","R. Barreto Leme, 2345 - Cambu’","13025-085","S‹o Paulo");
				
			}
		});
        
        Button btLigar = (Button) findViewById(R.id.contatoBtLigar);
        btLigar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ligar("01932525659");
				
			}
		});
        
        TextView txtNumber = (TextView) findViewById(R.id.contatoTextTel);
        txtNumber.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ligar("01932525659");
				
			}
		});
		
	}
	
	private void ligar(String number){
		
		String url = "tel:" + number;
	    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
	    startActivity(intent);
		
	}
	
	
	public int insert(String lastName, String firstName, String phoneNumber, String photo_uri, String email, String webSite, String city, String country, String street, String postcode, String state)
	{
		if(getContactDisplayNameByNumber(phoneNumber).equals("?")){
		    ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
	
		    Builder builder = ContentProviderOperation.newInsert(RawContacts.CONTENT_URI);
		    builder.withValue(RawContacts.ACCOUNT_TYPE, null);
		    builder.withValue(RawContacts.ACCOUNT_NAME, null);
		    ops.add(builder.build());
	
		    // Name
		    builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
		    builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0);
		    builder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
		    builder.withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, lastName);
		    builder.withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, firstName);
		    ops.add(builder.build());
	
		    // Number
		    builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
		    builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0);
		    builder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
		    builder.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);
		    builder.withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME);
		    ops.add(builder.build());
		    
		    // Email
		    builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
		    builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0);
		    builder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
		    builder.withValue(ContactsContract.CommonDataKinds.Email.DATA, email);
		    builder.withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
		    ops.add(builder.build());
		    
		    // WebSite
		    builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
		    builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0);
		    builder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
		    builder.withValue(ContactsContract.CommonDataKinds.Website.DATA, webSite);
		    builder.withValue(ContactsContract.CommonDataKinds.Website.TYPE, ContactsContract.CommonDataKinds.Website.TYPE_WORK);
		    ops.add(builder.build());
		    
		    // WebSite
		    builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
		    builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0);
		    builder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE);
		    builder.withValue(ContactsContract.CommonDataKinds.StructuredPostal.CITY, city);
		    builder.withValue(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY, country);
		    builder.withValue(ContactsContract.CommonDataKinds.StructuredPostal.STREET, street);
		    builder.withValue(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE, postcode);
		    builder.withValue(ContactsContract.CommonDataKinds.StructuredPostal.REGION, state);
		    builder.withValue(ContactsContract.CommonDataKinds.StructuredPostal.TYPE, ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK);
		    ops.add(builder.build());
	
		    // Picture
		    try
		    {
		        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logopadua);
	
		        ByteArrayOutputStream image = new ByteArrayOutputStream();
		        mBitmap.compress(Bitmap.CompressFormat.JPEG , 100, image);
	
		        builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
		        builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0);
		        builder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
		        builder.withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, image.toByteArray());
		        ops.add(builder.build());
		    }
		    catch (Exception e)
		    {
		        e.printStackTrace();
		    }
	
		    // Add the new contact
		    ContentProviderResult[] res;
		    try
		    {
		        res = getApplication().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		        if (res != null && res[0] != null)
		        {
		            String uri = res[0].uri.getPath().substring(14);
		            Toast.makeText(getBaseContext(), "Contato Adicionado com Sucesso", Toast.LENGTH_LONG).show();
		            return new Integer(uri).intValue(); // Book ID
		        }
		    }
		    catch (Exception e)
		    {
		    	Toast.makeText(getBaseContext(), "Falha ao adicionar Contato", Toast.LENGTH_LONG).show();
		        e.printStackTrace();
		    }
		    return 0;
		} else {
			Toast.makeText(getBaseContext(), "Contato J‡ Existe em sua Agenda", Toast.LENGTH_LONG).show();
		}
		return 0;
	}
	
	
	public String getContactDisplayNameByNumber(String number) {
	    Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
	    String name = "?";

	    ContentResolver contentResolver = getContentResolver();
	    Cursor contactLookup = contentResolver.query(uri, new String[] {BaseColumns._ID,
	            ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);

	    try {
	        if (contactLookup != null && contactLookup.getCount() > 0) {
	            contactLookup.moveToNext();
	            name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
	            //String contactId = contactLookup.getString(contactLookup.getColumnIndex(BaseColumns._ID));
	        }
	    } finally {
	        if (contactLookup != null) {
	            contactLookup.close();
	        }
	    }

	    return name;
	}
	
//	@Override
//	protected void onResume() {
//
//		if(menuOn){
//			btnSlide.performClick();
//		} 
//		
//		super.onResume();
//	}
	

}
