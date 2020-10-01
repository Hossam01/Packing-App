package com.example.packingapp.UI.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.packingapp.databinding.ManageWayBinding;
import com.example.packingapp.viewmodel.WayViewModel;
import com.example.packingapp.workmanagerapi.ReadDataWorkManageDriver;
import com.example.packingapp.workmanagerapi.ReadDataWorkManageVehicle;
import com.example.packingapp.workmanagerapi.UpdateWorkerManagerApiWay;
import com.example.packingapp.workmanagerapi.WorkerManagerApiWay;

import java.util.ArrayList;
import java.util.List;

public class WayManage extends Fragment {
    ManageWayBinding binding;
    WayViewModel wayViewModel;
    private View mLeak;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        wayViewModel = ViewModelProviders.of(this).get(WayViewModel.class);
        binding= ManageWayBinding.inflate(inflater, container, false);
        mLeak = binding.getRoot();
        wayViewModel.fetchDataDriver();
        wayViewModel.fetchDataVehicle();
        wayViewModel.fetchDataWay();

        List<String> vehicle = new ArrayList<String>();
        List<String> driver = new ArrayList<String>();
        List<String> way = new ArrayList<String>();

        ArrayAdapter spinnerAdapterWay = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, way);
        spinnerAdapterWay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.WayID.setAdapter(spinnerAdapterWay);
        ReadDataWorkManageVehicle.mutableLiveData.observe(getViewLifecycleOwner(), responseVehicle -> {
            for (int i = 0; i < responseVehicle.getRecords().size(); i++) {
                way.add(responseVehicle.getRecords().get(i).getVechileID());
            }
            spinnerAdapterWay.notifyDataSetChanged();
        });


        ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, vehicle);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.VechileID.setAdapter(spinnerAdapter);
        ReadDataWorkManageVehicle.mutableLiveData.observe(getViewLifecycleOwner(), responseVehicle -> {
            for (int i = 0; i < responseVehicle.getRecords().size(); i++) {
           vehicle.add(responseVehicle.getRecords().get(i).getVechileID());
        }
            spinnerAdapter.notifyDataSetChanged();
        });

        ArrayAdapter spinnerAdapterDriver = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, driver);
        spinnerAdapterDriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.DriverID.setAdapter(spinnerAdapterDriver);

        ReadDataWorkManageDriver.mutableLiveData.observe(getViewLifecycleOwner(), responseDriver -> {
            for (int i = 0; i< responseDriver.getRecords().size(); i++)
       {
           driver.add(responseDriver.getRecords().get(i).getDriverID());
       }
            spinnerAdapterDriver.notifyDataSetChanged();
        });



        binding.create.setOnClickListener(v -> {
            wayViewModel.fetchdata(binding.NameArabic.getText().toString(),binding.NameEnglish.getText().toString(),binding.Status.getText().toString(),binding.EstimationTime.getText().toString(),binding.Stations.getText().toString(),binding.DriverID.getSelectedItem().toString(),binding.VechileID.getSelectedItem().toString());
            WorkerManagerApiWay.mutableLiveData.observe(getViewLifecycleOwner(), message -> Toast.makeText(getContext(), message.getMessage(),Toast.LENGTH_SHORT).show());

        });

        binding.update.setOnClickListener(v -> {
            wayViewModel.updateData(binding.WayID.getSelectedItem().toString(),binding.NameArabic.getText().toString(),binding.NameEnglish.getText().toString(),binding.Status.getText().toString(),binding.EstimationTime.getText().toString(),binding.Stations.getText().toString(),binding.DriverID.getSelectedItem().toString(),binding.VechileID.getSelectedItem().toString());
            UpdateWorkerManagerApiWay.mutableLiveData.observe(getViewLifecycleOwner(), message -> Toast.makeText(getContext(), message.getMessage(),Toast.LENGTH_SHORT).show());

        });

        return mLeak;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mLeak = null;
    }
}
