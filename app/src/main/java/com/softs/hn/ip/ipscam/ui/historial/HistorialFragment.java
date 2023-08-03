package com.softs.hn.ip.ipscam.ui.historial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.softs.hn.ip.ipscam.R;
import com.softs.hn.ip.ipscam.databinding.FragmentHistorialBinding;

import java.util.ArrayList;

public class HistorialFragment extends Fragment implements OnItemClickListenerHistotial<Historial>{

    private HistorialAdapter adaptador;
    private HistorialViewModel viewModel;
    private FragmentHistorialBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHistorialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(HistorialViewModel.class);

        adaptador= new HistorialAdapter(new ArrayList<>(),this);

        viewModel.getHistorialDataset().observe(getViewLifecycleOwner(),historial -> {
            adaptador.setItems(historial);
        });


        setupRecyclerView();
        return root;
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());

        binding.rvHistorial.setLayoutManager(linearLayoutManager);
        binding.rvHistorial.setAdapter(adaptador);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(Historial data) {
        NavController navController = Navigation.findNavController(this.requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle param = new Bundle();
        param.putString("rec_placa", data.getPlaca());
        navController.navigate(R.id.navigation_inicio,param);
    }


    @Override
    public void onItemClickDelete(Historial data) {
        HistorialViewModel mHistorialViewModel;
        mHistorialViewModel = new ViewModelProvider(this).get(HistorialViewModel.class);
        mHistorialViewModel.delete(data);
    }
}