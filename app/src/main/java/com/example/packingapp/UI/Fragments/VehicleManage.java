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
import com.example.packingapp.databinding.ManageVehicleBinding;
import com.example.packingapp.viewmodel.VehicleViewModel;
import com.example.packingapp.workmanagerapi.ReadDataWorkManageVehicle;
import com.example.packingapp.workmanagerapi.UpdateWorkerManagerApiVehicle;
import com.example.packingapp.workmanagerapi.WorkerManagerApiVehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleManage extends Fragment {
    ManageVehicleBinding binding;
    VehicleViewModel vehicleViewModel;
    List<String> categories;
    View mLeak;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= ManageVehicleBinding.inflate(inflater, container, false);
        mLeak = binding.getRoot();
        vehicleViewModel = ViewModelProviders.of(getActivity()).get(VehicleViewModel.class);
        vehicleViewModel.fetchDataVehicle();
        categories = new ArrayList<String>();
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
            vehicleViewModel.fetchdata(binding.NameArabic.getText().toString(),binding.NameEnglish.getText().toString(),binding.LienceNumber.getText().toString(),binding.Weight.getText().toString());
            WorkerManagerApiVehicle.mutableLiveData.observe(getViewLifecycleOwner(), message -> Toast.makeText(getActivity(),message.getMessage().toString(),Toast.LENGTH_SHORT).show());
        });

        binding.update.setOnClickListener(v -> {
            vehicleViewModel.updateData(binding.VechileID.getSelectedItem().toString(),binding.NameArabic.getText().toString(),binding.NameEnglish.getText().toString(),binding.LienceNumber.getText().toString(),binding.Weight.getText().toString());
            UpdateWorkerManagerApiVehicle.mutableLiveData.observe(getViewLifecycleOwner(), message -> Toast.makeText(getActivity(),message.getMessage().toString(),Toast.LENGTH_SHORT).show());
        });



        return mLeak;
    }



}
