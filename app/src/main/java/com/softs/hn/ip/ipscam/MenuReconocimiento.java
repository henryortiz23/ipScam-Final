package com.softs.hn.ip.ipscam;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent.hasExtra("JSON")) {

            String jsonString = intent.getStringExtra("JSON");

            try {
                assert jsonString != null;
                JSONObject jsonObject = new JSONObject(jsonString);
                binding.vinDetalle.setText(jsonObject.getString("vin"));
                binding.placaDetalle.setText(jsonObject.getString("placa"));
                binding.tvPlaca.setText(binding.placaDetalle.getText());
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

        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

    }

}