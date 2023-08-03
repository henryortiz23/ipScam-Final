package com.softs.hn.ip.ipscam;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.softs.hn.ip.ipscam.databinding.ActivityMainBinding;
import com.softs.hn.ip.ipscam.databinding.FragmentInicioBinding;
import com.softs.hn.ip.ipscam.ui.inicio.InicioFragment;

public class MainActivity extends AppCompatActivity{

    private ActivityMainBinding binding;
    BottomNavigationView bottomNav;

    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        bottomNav = binding.navView;
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        NavigationUI.setupWithNavController(bottomNav, navController);
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> toolbar.setTitle(navDestination.getLabel()));

        int currentNightMode = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(ModoDark()){
            if (currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }else{
            if (currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }

    }

    private boolean ModoDark(){
        SharedPreferences conf = getSharedPreferences("Conf_user", Context.MODE_PRIVATE);
        String valorModo = conf.getString("modo", "");
        if("".equals(valorModo)){
            valorModo="0";
        }
        return Integer.parseInt(valorModo) > 0;
    }


}
