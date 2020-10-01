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
import com.example.packingapp.databinding.ManageDriverBinding;
import com.example.packingapp.viewmodel.DriverViewModel;
import com.example.packingapp.workmanagerapi.ReadDataWorkManageDriver;
import com.example.packingapp.workmanagerapi.ReadDataWorkManageVehicle;
import com.example.packingapp.workmanagerapi.UpdateWorkerManagerApiDriver;
import com.example.packingapp.workmanagerapi.WorkerManagerApiDriver;

import java.util.ArrayList;
import java.util.List;

public class DriverManage extends Fragment {
    ManageDriverBinding binding;
    DriverViewModel driverViewModel;
    List<String> categories;
    private View mLeak;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding= ManageDriverBinding.inflate(inflater, container, false);
        mLeak = binding.getRoot();
        driverViewModel = ViewModelProviders.of(this).get(DriverViewModel.class);
        driverViewModel.fetchDataVehicle();
        driverViewModel.fetchDataDriver();
        categories = new ArrayList<String>();
        List<String> driver = new ArrayList<String>();

        ArrayAdapter spinnerAdapterDriver = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, driver);
        spinnerAdapterDriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.DriverID.setAdapter(spinnerAdapterDriver);

        ReadDataWorkManageDriver.mutableLiveData.observe(getViewLifecycleOwner(), responseDriver -> {
            if (driver.size()==0) {
                for (int i = 0; i < responseDriver.getRecords().size(); i++) {
                    driver.add(responseDriver.getRecords().get(i).getDriverID());
                }
            }
            spinnerAdapterDriver.notifyDataSetChanged();
        });

        ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.VechileID.setAdapter(spinnerAdapter);
        ReadDataWorkManageVehicle.mutableLiveData.observe(getViewLifecycleOwner(), responseVehicle -> {
            if (categories.size()==0) {
                for (int i = 0; i < responseVehicle.getRecords().size(); i++) {
                    categories.add(responseVehicle.getRecords().get(i).getVechileID());
                }
            }
            spinnerAdapter.notifyDataSetChanged();
        });




        binding.create.setOnClickListener(v -> {
            driverViewModel.fetchdata(binding.NameArabic.getText().toString(),binding.NameEnglish.getText().toString(),binding.Status.getText().toString(),binding.Company.getText().toString(),binding.Phone.getText().toString(),binding.Address.getText().toString(),binding.VechileID.getSelectedItem().toString());
            WorkerManagerApiDriver.mutableLiveData.observe(getViewLifecycleOwner(), message -> Toast.makeText(getContext(), message.getMessage(),Toast.LENGTH_SHORT).show());
        });


        binding.update.setOnClickListener(v -> {
            driverViewModel.updateData(binding.DriverID.getSelectedItem().toString(),binding.NameArabic.getText().toString(),binding.NameEnglish.getText().toString(),binding.Status.getText().toString(),binding.Company.getText().toString(),binding.Phone.getText().toString(),binding.Address.getText().toString(),binding.VechileID.getSelectedItem().toString());
            UpdateWorkerManagerApiDriver.mutableLiveData.observe(getViewLifecycleOwner(), message -> Toast.makeText(getContext(), message.getMessage(),Toast.LENGTH_SHORT).show());

        });

        return mLeak;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLeak = null;
    }
}
