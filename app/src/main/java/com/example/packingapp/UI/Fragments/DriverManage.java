package com.example.packingapp.UI.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.packingapp.databinding.ManageDriverBinding;
import com.example.packingapp.model.ResponseDriver;
import com.example.packingapp.viewmodel.DriverViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class DriverManage extends Fragment {
    ManageDriverBinding binding;
    DriverViewModel driverViewModel;
    List<String> categories;
    private View mLeak;
    ResponseDriver responseDriver;
    String State = "create";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = ManageDriverBinding.inflate(inflater, container, false);
        mLeak = binding.getRoot();
        driverViewModel = ViewModelProviders.of(this).get(DriverViewModel.class);
        driverViewModel.fetchDataVehicle();
        driverViewModel.fetchDataDriver();
        categories = new ArrayList<String>();
        List<String> driver = new ArrayList<String>();
        

        ArrayAdapter spinnerAdapterDriver = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, driver);
        spinnerAdapterDriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.DriverID.setAdapter(spinnerAdapterDriver);


        driverViewModel.mutableLiveDataRead.observe(getViewLifecycleOwner(), (ResponseDriver responseDriver) -> {
            if (driver.size() == 0) {
                for (int i = 0; i < responseDriver.getRecords().size(); i++) {
                    if (i == 0)
                        driver.add("Select ID Driver");
                    driver.add(responseDriver.getRecords().get(i).getDriverID());
                }
            }
            this.responseDriver = responseDriver;
            spinnerAdapterDriver.notifyDataSetChanged();

        });

        binding.DriverID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                } else {
                    binding.NameArabic.setText(responseDriver.getRecords().get(position - 1).getNameArabic().toString());
                    binding.NameEnglish.setText(responseDriver.getRecords().get(position - 1).getNameEnglish().toString());
                    binding.Status.setText(responseDriver.getRecords().get(position - 1).getStatus().toString());
                    binding.Company.setText(responseDriver.getRecords().get(position - 1).getCompany().toString());
                    binding.Phone.setText(responseDriver.getRecords().get(position - 1).getPhone().toString());
                    binding.Address.setText(responseDriver.getRecords().get(position - 1).getAddress().toString());
                    binding.VechileID.setPrompt(responseDriver.getRecords().get(position - 1).getVechileID().toString());
                    binding.National.setText(responseDriver.getRecords().get(position - 1).getNational_ID().toString());
                    binding.create.setText("Update");
                    State = "update";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.VechileID.setAdapter(spinnerAdapter);
        driverViewModel.mutableLiveDataVehicle.observe(getViewLifecycleOwner(), responseVehicle -> {
            if (categories.size() == 0) {
                for (int i = 0; i < responseVehicle.getRecords().size(); i++) {
                    categories.add(responseVehicle.getRecords().get(i).getVechileID());
                }
            }
            spinnerAdapter.notifyDataSetChanged();
        });
        binding.create.setOnClickListener(v -> {
            if (State.equals("create"))
                create();
            else
                update();
        });


        return mLeak;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLeak = null;
    }


    public void create() {
        State = "create";
        driverViewModel.fetchdata(binding.NameArabic.getText().toString(), binding.NameEnglish.getText().toString(), binding.Status.getText().toString(), binding.Company.getText().toString(), binding.Phone.getText().toString(), binding.Address.getText().toString(), binding.VechileID.getSelectedItem().toString(),binding.National.getText().toString());
        driverViewModel.mutableLiveData.observe(getViewLifecycleOwner(), message -> Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_SHORT).show());
        binding.create.setText("Create");

        clear();
    }

    public void update() {
        driverViewModel.updateData(binding.DriverID.getSelectedItem().toString(), binding.NameArabic.getText().toString(), binding.NameEnglish.getText().toString(), binding.Status.getText().toString(), binding.Company.getText().toString(), binding.Phone.getText().toString(), binding.Address.getText().toString(), binding.VechileID.getSelectedItem().toString(),binding.National.getText().toString());
        driverViewModel.mutableLiveData.observe(getViewLifecycleOwner(), message -> Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_SHORT).show());
        binding.create.setText("Create");
        State = "create";
        clear();
    }

    public void clear() {
        binding.NameArabic.setText("");
        binding.NameEnglish.setText("");
        binding.Status.setText("");
        binding.Company.setText("");
        binding.Phone.setText("");
        binding.Address.setText("");
        binding.National.setText("");
    }
}
