package com.enpoints;

import java.io.IOException;
import java.util.List;

import android.util.Log;

import com.enpoints.frutaendpoint.Frutaendpoint;
import com.enpoints.frutaendpoint.model.Fruta;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

public class EndpointEnt {
	Frutaendpoint.Builder builder;
	Frutaendpoint fruta;

	public EndpointEnt() {
		builder = new Frutaendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					@Override
					public void initialize(HttpRequest arg0) throws IOException {
						// TODO Auto-generated method stub
					}
				});
		fruta = CloudEndpointUtils.updateBuilder(builder).build();
	}

	public List<Fruta> listFrutas() {

		try {

			return fruta.listFruta().execute().getItems();

		} catch (Exception e) {

			// TODO: handle exception
			e.toString();
			Log.i("LIST fruta Error------------------------", e.toString());
		}
		return null;
	}

	public Fruta getFruta(String Nombre) {
		Fruta frutaresponse = new Fruta();
		try {
			frutaresponse = fruta.getFruta(Nombre).execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.toString();
			Log.i("GET fruta Error------------------------", e.toString());
		}

		return frutaresponse;

	}

	public Boolean insertFruta(Fruta frutanueva) {

		try {
			fruta.insertFruta(frutanueva).execute();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("Insert fruta Error------------------------", e.toString());
			return false;
		}

	}

	public Boolean updateFruta(Fruta frutaupdate) {

		try {
			fruta.updateFruta(frutaupdate).execute();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("Update fruta Error------------------------", e.toString());
			return false;
		}
	}

	public Boolean deleteFruta(Fruta frutadelete) {
		try {
			fruta.removeFruta(frutadelete.getId()).execute();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("Delete fruta Error------------------------", e.toString());
			return false;
		}
	}

}
