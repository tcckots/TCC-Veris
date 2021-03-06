package com.kots.sidim.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.kots.sidim.android.R;
import com.kots.sidim.android.adapter.MenuAdapter;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.util.SessionUserSidim;
import com.kots.sidim.android.views.MyHorizontalScrollView;
import com.kots.sidim.android.views.MyHorizontalScrollView.SizeCallback;


public class MainBarActivity extends Activity {
	


	MyHorizontalScrollView scrollView;
    View menu;
    View app;
    View bar;    
    ViewGroup mergeViews;
    ImageButton btnSlide;
    boolean menuOut = false;
    Handler handler = new Handler();
    int btnWidth;
    static boolean menuOn = false;
    ListView listMenu;
    int indexActivity;
    Activity instance;
    
    


	@Override
	protected void onPause() {
		super.onPause();
		SessionUserSidim.clearImages();
	}
	

	@Override
	public void setContentView(int layoutResID) {		
		instance = this;
		LayoutInflater inflater = LayoutInflater.from(this);
        scrollView = (MyHorizontalScrollView) inflater.inflate(R.layout.horz_scroll_with_list_menu, null);
        scrollView.setBackgroundColor(R.color.color_background_screens);
        setContentView(scrollView);				
		
		menu = inflater.inflate(R.layout.horz_scroll_menu, null);
        app = inflater.inflate(layoutResID, null);
        bar = inflater.inflate(R.layout.layout_bar_app, null);
        
        LinearLayout linear = new LinearLayout(this);
        linear.setOrientation(LinearLayout.VERTICAL);
        linear.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        linear.addView(bar);
        linear.addView(app);
        linear.setBackgroundColor(R.color.color_background_screens);
        
        listMenu = (ListView) menu.findViewById(R.id.menuListView);
        //listMenu.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ConfigGlobal.menuList));
        listMenu.setAdapter(new MenuAdapter(this, ConfigGlobal.menuList));
        listMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				if(menuOn){
					btnSlide.performClick();
				}
				
				switch(arg2){
				case ConfigGlobal.MENU_INDEX_HOME: startActivity(new Intent(instance,MenuPrincipalActivity.class)); break;
				case ConfigGlobal.MENU_INDEX_FAVORITOS: startActivity(new Intent(instance,FavoritosActivity.class)); break;
				case ConfigGlobal.MENU_INDEX_CONFIGURACOES: startActivity(new Intent(instance,ConfiguracoesActivity.class)); break;
				case ConfigGlobal.MENU_INDEX_PESQUISAR_IMOVEL: startActivity(new Intent(instance,PesquisarImovelActivity.class)); break;
				case ConfigGlobal.MENU_INDEX_CONTATO: startActivity(new Intent(instance,ContatoActivity.class)); break;
				
				}
			}});
                
        
        ViewGroup tabBar = (ViewGroup) bar.findViewById(R.id.layoutBarApp);
        //tabBar.setBackgroundColor(R.color.color_background_screens);
        
        btnSlide = (ImageButton) tabBar.findViewById(R.id.barImgBtSlideMenu);
        btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView, menu));
        
        TextView currentScreen = (TextView) tabBar.findViewById(R.id.barTextCurrentScreen);
        currentScreen.setText(ConfigGlobal.menuList[indexActivity]);
        
        
        final View[] children = new View[] { menu, linear};
        

        // Scroll to app (view[1]) when layout finished.
        int scrollToViewIdx = 1;
        scrollView.initViews(children, scrollToViewIdx, new SizeCallbackForMenu(btnSlide));
                
	}
	
	public void setContentView(int layoutResID, int indexActivity) {
		
		this.indexActivity = indexActivity;	
		this.setContentView(layoutResID);		
		listMenu.setSelection(indexActivity);
		listMenu.setItemChecked(indexActivity, true);
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
	}
	
	
	@Override
	public void onBackPressed() {
		if(menuOn){
			btnSlide.performClick();
		} else{
			super.onBackPressed();
		}
	}


	
//	private void setListviewSelection(final ListView list, final int pos) {
//		list.post(new Runnable() 
//		   {
//		    @Override
//		    public void run() 
//		      {
//		        list.setSelection(pos);
//		        View v = list.getChildAt(pos);
//		        if (v != null) 
//		        {
//		            v.requestFocus();
//		        }
//		    }
//		});
//		}




	/**
     * Helper for examples with a HSV that should be scrolled by a menu View's width.
     */
    static class ClickListenerForScrolling implements OnClickListener {
        HorizontalScrollView scrollView;
        View menu;
        /**
         * Menu must NOT be out/shown to start with.
         */
        boolean menuOut = false;

        public ClickListenerForScrolling(HorizontalScrollView scrollView, View menu) {
            super();
            this.scrollView = scrollView;
            this.menu = menu;
        }

        @Override
        public void onClick(View v) {                    

            int menuWidth = menu.getMeasuredWidth();            

            // Ensure menu is visible
            menu.setVisibility(View.VISIBLE);

            if (!menuOn) {
                // Scroll to 0 to reveal menu
                int left = 0;
                scrollView.smoothScrollTo(left, 0);
                menuOn = true;
            } else {
                // Scroll to menuWidth so menu isn't on screen.
                int left = menuWidth;
                scrollView.smoothScrollTo(left, 0);
                menuOn = false;
            }
            menuOut = !menuOut;
            
        }
    }

    
    static class SizeCallbackForMenu implements SizeCallback {
        int btnWidth;
        View btnSlide;

        public SizeCallbackForMenu(View btnSlide) {
            super();
            this.btnSlide = btnSlide;
        }

        @Override
        public void onGlobalLayout() {
            btnWidth = btnSlide.getMeasuredWidth();
            System.out.println("btnWidth=" + btnWidth);
        }

        @Override
        public void getViewSize(int idx, int w, int h, int[] dims) {
            dims[0] = w;
            dims[1] = h;
            final int menuIdx = 0;
            if (idx == menuIdx) {
                dims[0] = w - btnWidth;
            }
        }
    }

}
