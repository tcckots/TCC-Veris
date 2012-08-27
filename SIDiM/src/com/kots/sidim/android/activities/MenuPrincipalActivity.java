package com.kots.sidim.android.activities;

import com.kots.sidim.android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuPrincipalActivity extends Activity {

	Activity instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menuprincipal);

		instance = this;

		Button btConfig = (Button) findViewById(R.id.menuPrincipalBtConfig);
		btConfig.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivity(new Intent(instance, ConfiguracoesActivity.class));

			}
		});

	}
}
