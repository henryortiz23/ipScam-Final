package com.softs.hn.ip.ipscam;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softs.hn.ip.ipscam.ui.historial.Historial;
import com.softs.hn.ip.ipscam.ui.historial.HistorialViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public class HttpGetRequest2 extends AsyncTask<String, Void, String> {
    private Context context;

    private Dialog dialog;

    Animation animation1;

    public HttpGetRequest2(Context context) {
        this.context = context;
        showDialog();
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
        if (result == null || result.isEmpty()) {
            sendDialogo("Error de conexion", "Fallo a conexion con el servidor, por favor verifique que se encuentre en linea.", 2);
        } else {
            try {
                JSONObject apexObject = new JSONObject(result);
                JSONArray apexItems = apexObject.getJSONArray("items");
                if (apexItems.length() > 0) {
                    JSONObject json = apexItems.getJSONObject(0);

                    //showInformacion(json);

					Intent intent = new Intent(context, MenuReconocimiento.class);
					intent.putExtra("JSON", json.toString());
					context.startActivity(intent);
					crear_historial(json.getString("placa"));

                    dialog.dismiss();

                } else {
                    sendDialogo("Datos no encontrados", "No se encontro el numero de placa ingresado", 3);
                }

            } catch (JSONException e) {
                sendDialogo("Error de conexion", "Fallo a conexion con el servidor, por favor verifique que se encuentre en linea.", 2);
            }

        }
    }

    private void crear_historial(String placa) {
        Historial data = new Historial(placa);
        HistorialViewModel mHistorialViewModel;
        mHistorialViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(HistorialViewModel.class);

        mHistorialViewModel.insert(data);
    }


    private void showDialog() {
        this.dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView titulo = dialog.findViewById(R.id.titulo);
        TextView msg = dialog.findViewById(R.id.msg);
        ImageView icono = dialog.findViewById(R.id.icono);

        titulo.setText("Obteniendo información");
        msg.setText("Estamos obteniendo la informacion, por favor espere...");
        icono.setImageResource(R.drawable.loading3);
        animation1 = AnimationUtils.loadAnimation(context, R.anim.loading);
        icono.startAnimation(animation1);

        Button bOk = dialog.findViewById(R.id.btnAceptar);
        bOk.setVisibility(View.GONE);


        bOk.setOnClickListener(v -> dialog.dismiss());
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    private void sendDialogo(String titulo, String msg, int icono) {
        TextView txtTitulo = dialog.findViewById(R.id.titulo);
        TextView txtMsg = dialog.findViewById(R.id.msg);
        ImageView imgIcono = dialog.findViewById(R.id.icono);
        Button btn = dialog.findViewById(R.id.btnAceptar);

        imgIcono.clearAnimation();
        btn.setVisibility(View.VISIBLE);

        if (icono == 2) {
            imgIcono.setImageResource(R.drawable.not_connection);
        } else if (icono == 3) {
            imgIcono.setImageResource(R.drawable.no_found);
        }
        txtTitulo.setText(titulo);
        txtMsg.setText(msg);
    }

    private void showInformacion(JSONObject json) {

		dialog.findViewById(R.id.lLoading).setVisibility(View.GONE);
		dialog.findViewById(R.id.lInformacion).setVisibility(View.VISIBLE);

        TextView vinDetalle = dialog.findViewById(R.id.vin_detalle);
        TextView placaDetalle = dialog.findViewById(R.id.placa_detalle);
        TextView marcaDetalle = dialog.findViewById(R.id.marca_detalle);
        TextView modeloDetalle = dialog.findViewById(R.id.modelo_detalle);
        TextView colorDetalle = dialog.findViewById(R.id.color_detalle);
        TextView motorDetalle = dialog.findViewById(R.id.motor_detalle);
        TextView nombreDetalle = dialog.findViewById(R.id.nombre_detalle);
        TextView noPrestamoDetalle = dialog.findViewById(R.id.no_prestamo_detalle);
        TextView nombreClienteDetalle = dialog.findViewById(R.id.nombre_cliente_detalle);
        TextView estadoDetalle = dialog.findViewById(R.id.estado_detalle);
        TextView diasMoraDetalle = dialog.findViewById(R.id.dias_mora_detalle);
        TextView montoAdeudadoDetalle = dialog.findViewById(R.id.monto_adeudado_detalle);

        try {
            vinDetalle.setText(json.getString("vin"));
            placaDetalle.setText(json.getString("placa"));
            marcaDetalle.setText(json.getString("marca"));
            modeloDetalle.setText(json.getString("modelo"));
            colorDetalle.setText(json.getString("color"));
            motorDetalle.setText(json.getString("motor"));
            nombreDetalle.setText(json.getString("financiera"));
            noPrestamoDetalle.setText(json.getString("no_prestamo"));
            nombreClienteDetalle.setText(json.getString("cliente"));
            estadoDetalle.setText(json.getString("estado"));
            diasMoraDetalle.setText(json.getString("dias_mora"));
            montoAdeudadoDetalle.setText(json.getString("montoadeudato"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        dialog.findViewById(R.id.btnAceptar).setVisibility(View.VISIBLE);
    }
}
