package com.kots.sidim.android.activities;


import java.util.ArrayList;
import java.util.Date;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kots.sidim.android.R;
import com.kots.sidim.android.adapter.FotoGaleriaAdapter;
import com.kots.sidim.android.config.ConfigGlobal;
import com.kots.sidim.android.exception.SiDIMException;
import com.kots.sidim.android.model.Cliente;
import com.kots.sidim.android.model.Imovel;
import com.kots.sidim.android.model.InteresseCliente;
import com.kots.sidim.android.server.SiDIMControllerServer;

public class VisualizarImovelActivity extends MainBarActivity {
	
		Button btEnviarInteresse;
		SiDIMControllerServer controller;
		Imovel imovel;
		SharedPreferences globalPrefs;
		
		ImageView imgMain;
		TextView txtTitle, txtBairro, txtCidadeEstado, txtQtds, txtArea, txtObservacao;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_visualizar_imovel,
					ConfigGlobal.MENU_INDEX_PESQUISAR_IMOVEL);
			
			if (getIntent() != null) {
				if (getIntent().getSerializableExtra("imoveldetalhes") instanceof Imovel) {
					imovel = (Imovel) getIntent().getSerializableExtra(
							"imoveldetalhes");		
					loadIds();
				}
			}
			
			controller = SiDIMControllerServer.getInstance(this);
			
			btEnviarInteresse = (Button) findViewById(R.id.visualizarImovelButtonInteresse);
			Gallery gallery = (Gallery) findViewById(R.id.visualizarImovelGallery);
			imgMain = (ImageView) findViewById(R.id.visualizarImovelImageMain);
						
			
			final ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(R.drawable.casabonita);
			list.add(R.drawable.casafera);
			list.add(R.drawable.casabonita);
			list.add(R.drawable.casafera);
			list.add(R.drawable.casabonita);
			
			imgMain.setImageResource(list.get(0));
			
			FotoGaleriaAdapter adapter = new FotoGaleriaAdapter(this, list);
			
			gallery.setAdapter(adapter);
			
			gallery.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

					
					imgMain.setImageResource(list.get(arg2));
					
				}
			});
			
			
			btEnviarInteresse.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					 enviarInteresse();
					
				}
			});
		}
	
		private void enviarInteresse(){
			
			globalPrefs = getSharedPreferences(ConfigGlobal.GLOBAL_SHARED_PREFERENCES, MODE_PRIVATE);
			
			Cliente cliente = new Cliente();
			cliente.setLogin(globalPrefs.getString(ConfigGlobal.SHARED_PREFERENCES_EMAIL_USER, ""));			
			InteresseCliente interesse = new InteresseCliente(imovel, cliente, new Date());
			try {
				controller.enviarInteresse(interesse);
				Toast.makeText(this, "Interesse Enviado, Em breve entraremos em contato", Toast.LENGTH_LONG).show();
			} catch (SiDIMException e) {
				Toast.makeText(this, "N‹o foi poss’vel enviar Interesse, verifique sua conex‹o", Toast.LENGTH_LONG).show();
			}						
			
			
		}
	
		private void loadIds(){
			txtTitle = (TextView) findViewById(R.id.visualizarImovelTextTitle);
			txtBairro = (TextView) findViewById(R.id.visualizarImovelTextBairro);
			txtCidadeEstado = (TextView) findViewById(R.id.visualizarImovelTextCidadeEstado);
			txtQtds = (TextView) findViewById(R.id.visualizarImovelTextQtds); 
			txtArea = (TextView) findViewById(R.id.visualizarImovelTextArea); 
			txtObservacao = (TextView) findViewById(R.id.visualizarImovelTextObservacao);
			
			txtTitle.setText(imovel.getTipoImovel().getDescricao() + " - " + imovel.getBairro().getNome() + " " + imovel.getDormitorios() + " Dorm");
			txtBairro.setText(imovel.getBairro().getNome());
			txtCidadeEstado.setText(imovel.getCidade().getNome() + "-" + imovel.getEstado().getNome());			
			txtQtds.setText(imovel.getDormitorios() + " Dorm, sendo " + imovel.getSuites() + " su’tes - com " + imovel.getGaragens() + " Garagens");
			txtArea.setText("çrea: " + imovel.getArea() + " Constru’da - " + imovel.getArea() + " Total");
			txtObservacao.setText(imovel.getDescricao());
		}

}
