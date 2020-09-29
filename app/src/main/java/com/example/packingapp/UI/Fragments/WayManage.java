package com.example.packingapp.UI.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.packingapp.databinding.ManageWayBinding;
import com.example.packingapp.viewmodel.WayViewModel;
import com.example.packingapp.workmanagerapi.ReadDataWorkManageDriver;
import com.example.packingapp.workmanagerapi.ReadDataWorkManageVehicle;
import com.example.packingapp.workmanagerapi.WorkerManagerApiWay;

import java.util.ArrayList;
import java.util.List;

public class WayManage extends Fragment {
    ManageWayBinding binding;
    WayViewModel wayViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        wayViewModel = ViewModelProviders.of(this).get(WayViewModel.class);
        binding= ManageWayBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        wayViewModel.fetchDataDriver();
        wayViewModel.fetchDataVehicle();
        List<String> vehicle = new ArrayList<String>();
        List<String> driver = new ArrayList<String>();
        for (int i = 0; i< ReadDataWorkManageVehicle.mutableLiveData.getValue().getRecords().size(); i++)
        {
            vehicle.add(ReadDataWorkManageVehicle.mutableLiveData.getValue().getRecords().get(i).getVechileID());
        }
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, vehicle);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.VechileID.setAdapter(spinnerAdapter);

        for (int i = 0; i< ReadDataWorkManageDriver.mutableLiveData.getValue().getRecords().size(); i++)
        {
            driver.add(ReadDataWorkManageDriver.mutableLiveData.getValue().getRecords().get(i).getDriverID());
        }
        ArrayAdapter spinnerAdapterDriver = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, driver);
        spinnerAdapterDriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.DriverID.setAdapter(spinnerAdapterDriver);

        binding.create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wayViewModel.fetchdata(binding.NameArabic.getText().toString(),binding.NameEnglish.getText().toString(),binding.Status.getText().toString(),binding.EstimationTime.getText().toString(),binding.Stations.getText().toString(),binding.DriverID.getSelectedItem().toString(),binding.VechileID.getSelectedItem().toString());
                Toast.makeText(getContext(), WorkerManagerApiWay.mutableLiveData.getValue().getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
