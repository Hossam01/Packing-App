package com.example.packingapp.UI.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.packingapp.databinding.ManageVehicleBinding;
import com.example.packingapp.model.ResponseVehicle;
import com.example.packingapp.viewmodel.VehicleViewModel;

import java.util.ArrayList;
import java.util.List;

public class VehicleManage extends Fragment {
    ManageVehicleBinding binding;
    VehicleViewModel vehicleViewModel;
    List<String> categories;
    View mLeak;
    ResponseVehicle responseVehicle;
    String State = "create";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ManageVehicleBinding.inflate(inflater, container, false);
        mLeak = binding.getRoot();
        vehicleViewModel = ViewModelProviders.of(getActivity()).get(VehicleViewModel.class);
        vehicleViewModel.fetchDataVehicle();
        categories = new ArrayList<String>();
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.VechileID.setAdapter(spinnerAdapter);

        vehicleViewModel.mutableLiveDataVehicle.observe(getViewLifecycleOwner(), responseVehicle -> {
            if (categories.size()==0) {
                for (int i = 0; i < responseVehicle.getRecords().size(); i++) {
                    if (i == 0)
                        categories.add("Select Vehicle ID");
                    categories.add(responseVehicle.getRecords().get(i).getVechileID());

                }
            }
            spinnerAdapter.notifyDataSetChanged();
            this.responseVehicle = responseVehicle;
        });
        binding.VechileID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                } else {
                    binding.NameArabic.setText(responseVehicle.getRecords().get(position - 1).getNameArabic().toString());
                    binding.NameEnglish.setText(responseVehicle.getRecords().get(position - 1).getNameEnglish().toString());
                    binding.LienceNumber.setText(responseVehicle.getRecords().get(position - 1).getLienceNumber().toString());
                    binding.Weight.setText(responseVehicle.getRecords().get(position - 1).getWeight().toString());
                    binding.create.setText("Update");
                    State = "update";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.create.setOnClickListener(v -> {
            if (State.equals("create"))
                create();
            else
                update();
        });


        return mLeak;
    }

    public void create() {
        State = "create";
        vehicleViewModel.fetchdata(binding.NameArabic.getText().toString(), binding.NameEnglish.getText().toString(), binding.LienceNumber.getText().toString(), binding.Weight.getText().toString());
        vehicleViewModel.mutableLiveData.observe(getViewLifecycleOwner(), message -> Toast.makeText(getActivity(), message.getMessage().toString(), Toast.LENGTH_SHORT).show());
        clear();

    }

    public void update() {
        vehicleViewModel.updateData(binding.VechileID.getSelectedItem().toString(), binding.NameArabic.getText().toString(), binding.NameEnglish.getText().toString(), binding.LienceNumber.getText().toString(), binding.Weight.getText().toString());
        vehicleViewModel.mutableLiveData.observe(getViewLifecycleOwner(), message -> Toast.makeText(getActivity(), message.getMessage().toString(), Toast.LENGTH_SHORT).show());
        binding.create.setText("Create");
        State = "create";
        clear();

    }

    public void clear() {
        binding.NameArabic.setText("");
        binding.NameEnglish.setText("");
        binding.LienceNumber.setText("");
        binding.Weight.setText("");
    }
}
