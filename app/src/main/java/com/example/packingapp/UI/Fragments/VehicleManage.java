package com.example.packingapp.UI.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityLoginBinding;
import com.example.packingapp.databinding.ManageVehicleBinding;
import com.example.packingapp.viewmodel.LoginViewModel;
import com.example.packingapp.viewmodel.VehicleViewModel;
import com.example.packingapp.workmanagerapi.WorkerManagerApiVehicle;

public class VehicleManage extends Fragment {
    ManageVehicleBinding binding;
    VehicleViewModel vehicleViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vehicleViewModel = ViewModelProviders.of(this).get(VehicleViewModel.class);
        binding= ManageVehicleBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicleViewModel.fetchdata(binding.NameArabic.getText().toString(),binding.NameEnglish.getText().toString(),binding.LienceNumber.getText().toString(),binding.Weight.getText().toString());
                Toast.makeText(getContext(),WorkerManagerApiVehicle.mutableLiveData.getValue().getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
