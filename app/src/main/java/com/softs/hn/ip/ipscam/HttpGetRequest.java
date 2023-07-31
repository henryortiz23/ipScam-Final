package com.softs.hn.ip.ipscam;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.softs.hn.ip.ipscam.databinding.ActivityConsultarBinding;
import com.softs.hn.ip.ipscam.databinding.ActivityMenuReconocimientoBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetRequest extends AsyncTask<String, Void, String> {
	private Context context;
	private ActivityMenuReconocimientoBinding binding;

	public HttpGetRequest(ActivityMenuReconocimientoBinding binding,Context context) {
		this.context = context;
		this.binding = binding;
	}
	@Override
	protected String doInBackground(String... urls) {
		String response = "";
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(urls[0]);
			urlConnection = (HttpURLConnection) url.openConnection();

			// Configurar la conexión
			urlConnection.setRequestMethod("GET");
			urlConnection.setReadTimeout(10000 /* milisegundos */);
			urlConnection.setConnectTimeout(15000 /* milisegundos */);
			urlConnection.connect();

			// Leer la respuesta del servidor
			InputStream inputStream = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				response += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return response;
	}

	@Override
	protected void onPostExecute(String result) {
		try {
			//Toast.makeText(context,"Llega aqui"+result,Toast.LENGTH_LONG).show();
			if(result == null || result.isEmpty()){
				binding.layout404.setVisibility(View.VISIBLE);
				binding.layout200.setVisibility(View.GONE);
				return;
			}
			JSONArray res = new JSONArray(result);
			if (res.length()>0){
				JSONObject json = res.getJSONObject(0);
				binding.vinDetalle.setText(json.get("vin").toString());
				binding.placaDetalle.setText(json.get("placa").toString());
				binding.marcaDetalle.setText(json.get("marca").toString());
				binding.modeloDetalle.setText(json.get("modelo").toString());
				binding.colorDetalle.setText(json.get("color").toString());
				binding.motorDetalle.setText(json.get("motor").toString());
				binding.nombreDetalle.setText(json.get("financiera").toString());
				binding.noPrestamoDetalle.setText(json.get("No_prestamo").toString());
				binding.nombreClienteDetalle.setText(json.get("cliente").toString());
				binding.estadoDetalle.setText(json.get("estado").toString());
				binding.diasMoraDetalle.setText(json.get("dias_mora").toString());
				binding.montoAdeudadoDetalle.setText(json.get("montoAdeudato").toString());
			}else{
				binding.layout404.setVisibility(View.VISIBLE);
				binding.layout200.setVisibility(View.GONE);
			}
		} catch (JSONException e) {
			binding.layout404.setVisibility(View.VISIBLE);
			binding.layout200.setVisibility(View.GONE);
			throw new RuntimeException(e);
		}
	}
}
