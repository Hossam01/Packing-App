package com.example.packingapp.UI.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.packingapp.databinding.ManageDriverBinding;
import com.example.packingapp.viewmodel.DriverViewModel;
import com.example.packingapp.workmanagerapi.ReadDataWorkManageVehicle;
import com.example.packingapp.workmanagerapi.WorkerManagerApiDriver;

import java.util.ArrayList;
import java.util.List;

public class DriverManage extends Fragment {
    ManageDriverBinding binding;
    DriverViewModel driverViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        driverViewModel = ViewModelProviders.of(this).get(DriverViewModel.class);
        binding= ManageDriverBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        driverViewModel.fetchDataVehicle();
        List<String> categories = new ArrayList<String>();
        for (int i=0;i<ReadDataWorkManageVehicle.mutableLiveData.getValue().getRecords().size();i++)
        {
            categories.add(ReadDataWorkManageVehicle.mutableLiveData.getValue().getRecords().get(i).getVechileID());
        }
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.VechileID.setAdapter(spinnerAdapter);
        binding.create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverViewModel.fetchdata(binding.NameArabic.getText().toString(),binding.NameEnglish.getText().toString(),binding.Status.getText().toString(),binding.Company.getText().toString(),binding.Phone.getText().toString(),binding.Address.getText().toString(),binding.VechileID.getSelectedItem().toString());
                Toast.makeText(getContext(), WorkerManagerApiDriver.mutableLiveData.getValue().getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
