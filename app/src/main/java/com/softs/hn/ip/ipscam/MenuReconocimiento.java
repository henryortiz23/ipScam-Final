package com.softs.hn.ip.ipscam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.os.Bundle;

import com.softs.hn.ip.ipscam.databinding.ActivityMenuReconocimientoBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuReconocimiento extends AppCompatActivity {

    private ActivityMenuReconocimientoBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuReconocimientoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        if (intent.hasExtra("JSON")) {

            String jsonString = intent.getStringExtra("JSON");

            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                binding.vinDetalle.setText(jsonObject.getString("vin"));
                binding.placaDetalle.setText(jsonObject.getString("placa"));
                binding.marcaDetalle.setText(jsonObject.getString("marca"));
                binding.modeloDetalle.setText(jsonObject.getString("modelo"));
                binding.colorDetalle.setText(jsonObject.getString("color"));
                binding.motorDetalle.setText(jsonObject.getString("motor"));
                binding.nombreDetalle.setText(jsonObject.getString("financiera"));
                binding.noPrestamoDetalle.setText(jsonObject.getString("no_prestamo"));
                binding.nombreClienteDetalle.setText(jsonObject.getString("cliente"));
                binding.estadoDetalle.setText(jsonObject.getString("estado"));
                binding.diasMoraDetalle.setText(jsonObject.getString("dias_mora"));
                binding.montoAdeudadoDetalle.setText(jsonObject.getString("montoadeudato"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        /*
        if (intent.hasExtra("placa")){
            String placa = intent.getStringExtra("placa");
            //Toast.makeText(this,placa,Toast.LENGTH_LONG).show();
            runOnUiThread(() -> {
                try {
                    new HttpGetRequest(binding,this).execute("http://34.125.242.85:8080/api/consultar/"+placa);
                    //
                } catch (Exception e) {
                    throw new RuntimeException(e);
                };
            });
        }
        // Obtener la URI de la imagen seleccionada desde el Intent
        String imageUriString = getIntent().getStringExtra("image_uri");
        if (imageUriString != null) {
            // Convertir la URI nuevamente a un objeto Uri
            Uri imageUri = Uri.parse(imageUriString);

            // Mostrar la imagen en el ImageView utilizando Glide

        }
         */
        //animar();
    }

    private void animar(){

    }
}