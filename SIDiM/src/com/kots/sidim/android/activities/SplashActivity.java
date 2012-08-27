package com.kots.sidim.android.activities;

import com.kots.sidim.android.R;
import com.kots.sidim.android.R.layout;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.config.ValidacaoGeral;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;




public class SplashActivity extends Activity {

    private Handler handler = new Handler();        
    private SplashActivity instance;
    ProgressDialog progressDialog;
    SharedPreferences globalPrefs;



        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        instance = this;
        globalPrefs = getSharedPreferences(ConfigGlobal.GLOBAL_SHARED_PREFERENCES, MODE_PRIVATE);
        

        setContentView(R.layout.activity_splash);

        int minMillisToShowSplash = 2000;

        final long now = System.currentTimeMillis();
        final long finalMinMillisToShowSplash = minMillisToShowSplash;


        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... params) {
				return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                long remainingTime = Math.max(finalMinMillisToShowSplash - (System.currentTimeMillis() - now), 0);

                handler.postDelayed(new Runnable() {
                    public void run() {

                        Intent intent = null;
                        String userEmail = globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_EMAIL_USER, null);
                        boolean loginAuto = globalPrefs.getBoolean(ConfigGlobal.SHARED_PREFERENCES_LOGIN_AUTO, false);
                        if(ValidacaoGeral.validaCampoVazio(userEmail) && loginAuto){                        	
                        	intent = new Intent(instance,MenuPrincipalActivity.class);
                        } else if(ValidacaoGeral.validaCampoVazio(userEmail)) {
                        	intent = new Intent(instance,LoginActivity.class);                        	
                        } else {
                        	intent = new Intent(instance,CriarContaActivity.class);
                        }
                        		                                            
                        startActivity(intent);
                        finish();
                    }}, remainingTime);
            }
        };

        // noinspection unchecked
        asyncTask.execute();
    }
 
}
