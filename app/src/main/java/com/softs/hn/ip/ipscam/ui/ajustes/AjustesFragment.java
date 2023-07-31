package com.softs.hn.ip.ipscam.ui.ajustes;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.softs.hn.ip.ipscam.databinding.FragmentAjustesBinding;

import java.util.Objects;


public class AjustesFragment extends Fragment {

    private int modoDark;
    private FragmentAjustesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAjustesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            modoDark=1;
        }else {
            modoDark=0;
        }
        binding.btnModo.setOnClickListener(v ->{
            modo();
        });

        return root;
    }

    private void modo(){
            if (modoDark == 1) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                modoDark=0;
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                modoDark=1;
            }

        SharedPreferences conf = Objects.requireNonNull(getActivity()).getSharedPreferences("Conf_user", Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_quedo = conf.edit();
        Obj_quedo.putString("modo", String.valueOf(modoDark));
        Obj_quedo.apply();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}