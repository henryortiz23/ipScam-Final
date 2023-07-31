package com.softs.hn.ip.ipscam;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Reader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Peticion {

	public static JSONArray get(String ruta, String token) throws Exception {
		 
		StringBuilder resultado = new StringBuilder();
		  URL url = new URL(ruta);

		  HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
		  conexion.setRequestMethod("GET");
		  if(!token.isEmpty())conexion.setRequestProperty("Authorization","Bearer "+token);
		  BufferedReader rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
		  String linea;
		  while ((linea = rd.readLine()) != null) {
		    resultado.append(linea);
		  }
		  
		  rd.close();
	
		  return new JSONArray(resultado.toString());
	}

}

