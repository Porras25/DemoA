package com.enpoints;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.enpoints.frutaendpoint.model.Fruta;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

public class HolaEndpoints extends Activity {
	public static final int DELETE_FRUTA = 1;
	public static final int LIST_FRUTA = 2;
	public static final int SELECT_FRUTA = 3;
	public static final int UPDATE_FRUTA = 4;
	public static final int INSERT_FRUTA = 5;
	TextView texto;

	ListView listavista;
	CustomAdapter adapte;
	Fruta bean_fruta;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hola_endpoints);
		texto = (TextView) findViewById(R.id.text);
		listavista = (ListView) findViewById(R.id.listView1);
        Button btngetlist=(Button)findViewById(R.id.getlist);
        Button btnget=(Button)findViewById(R.id.read);
        Button btngdelete=(Button)findViewById(R.id.delete);
        Button btnupdate=(Button)findViewById(R.id.update);
        Button btncreate=(Button)findViewById(R.id.create);
        btncreate.setOnClickListener(ClickButton);
        btnupdate.setOnClickListener(ClickButton);
        btngdelete.setOnClickListener(ClickButton);
        btnget.setOnClickListener(ClickButton);
        btngetlist.setOnClickListener(ClickButton);
	}
	OnClickListener ClickButton = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			switch (v.getId()) {
			case R.id.create:
		        new EnpointsConsult(INSERT_FRUTA).execute("Creando Fruta");  
				break;
			case R.id.delete:
				new EnpointsConsult(DELETE_FRUTA).execute("Borrando Fruta");
				break;
			case R.id.update:
				new EnpointsConsult(UPDATE_FRUTA).execute("Update Fruta");
				break;
			case R.id.getlist:
				new EnpointsConsult(LIST_FRUTA).execute("Creando Fruta");
				break;
			case R.id.read:
				new EnpointsConsult(SELECT_FRUTA).execute("Creando Fruta");
				break;
			default:
				break;
		}	}
	};
	
	private class EnpointsConsult extends AsyncTask<String, Float, Integer> {
		public int metodo;
		public Boolean resultado;

		public EnpointsConsult(int metodoFruta) {
			// TODO Auto-generated constructor stub
			metodo = metodoFruta;
		}

		protected void onPreExecute() {

		}

		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
		
			// Example holaendpoint =
			// CloudEndpointUtils.updateBuilder(builder).build();

			try {
				// contenido = holaendpoint.holaMundo().execute();
				EndpointEnt entity = new EndpointEnt();
				switch (metodo) {
				case LIST_FRUTA:
					adapte=null;
					List<Fruta> lista = entity.listFrutas();
					adapte = new CustomAdapter(getBaseContext(), lista);
					break;
				case DELETE_FRUTA:
					resultado=entity.deleteFruta(bean_fruta);
					break;
				case UPDATE_FRUTA:
					resultado=entity.updateFruta(bean_fruta);
					break;
				case INSERT_FRUTA:
					resultado=entity.insertFruta(bean_fruta);
					break;
				case SELECT_FRUTA:
					Fruta temporal = new Fruta();
					temporal=entity.getFruta(bean_fruta.getNombre());
					bean_fruta=temporal;
					break;
				default:
					break;
				}

			} catch (Exception e) {

				// TODO: handle exception
				e.toString();
				Log.i("Error al accesar al Entity", e.toString());
			}
			return null;
		}

		protected void onProgressUpdate(Float... valores) {

		}

		protected void onPostExecute(Integer bytes) {
			
		
			switch (metodo) {
			case LIST_FRUTA:
//				if(listavista.getCount()>0)listavista.removeAllViews();
				listavista.setAdapter(adapte);
				break;
			case DELETE_FRUTA:
				texto.setText(resultado.toString());
				break;
			case UPDATE_FRUTA:
				texto.setText(resultado.toString());
				break;
			case INSERT_FRUTA:
				texto.setText(resultado.toString());
				break;
			case SELECT_FRUTA:
				texto.setText(bean_fruta.getId()+"     "+bean_fruta.getNombre()+"    "+bean_fruta.getDescripcion());
				break;
			default:
				break;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hola_endpoints, menu);
		return true;
	
	}
}
