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

import com.example.packingapp.R;
import com.example.packingapp.databinding.ManageWayBinding;
import com.example.packingapp.model.ResponseWay;
import com.example.packingapp.viewmodel.WayViewModel;


import java.util.ArrayList;
import java.util.List;

public class WayManage extends Fragment {
    ManageWayBinding binding;
    WayViewModel wayViewModel;
    private View mLeak;
    String State = "create";
    ResponseWay responseWay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        wayViewModel = ViewModelProviders.of(this).get(WayViewModel.class);
        binding = ManageWayBinding.inflate(inflater, container, false);
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
        wayViewModel.mutableLiveDataWay.observe(getViewLifecycleOwner(), responseWay -> {
            if (way.size() == 0) {
                for (int i = 0; i < responseWay.getRecords().size(); i++) {
                    if (i == 0)
                        way.add(getResources().getString(R.string.choice_way_id));
                    way.add(responseWay.getRecords().get(i).getDirection_ID());
                }
            }
            spinnerAdapterWay.notifyDataSetChanged();
            this.responseWay = responseWay;
        });


        ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, vehicle);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.VechileID.setAdapter(spinnerAdapter);
        wayViewModel.mutableLiveDataVehicle.observe(getViewLifecycleOwner(), responseVehicle -> {
            if (vehicle.size()==0) {
                for (int i = 0; i < responseVehicle.getRecords().size(); i++) {
                    vehicle.add(responseVehicle.getRecords().get(i).getVechileID());
                }

            }
            spinnerAdapter.notifyDataSetChanged();
        });

        ArrayAdapter spinnerAdapterDriver = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, driver);
        spinnerAdapterDriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.DriverID.setAdapter(spinnerAdapterDriver);

        wayViewModel.mutableLiveDataRead.observe(getViewLifecycleOwner(), responseDriver -> {
            if (driver.size() == 0) {
                for (int i = 0; i < responseDriver.getRecords().size(); i++) {
                    driver.add(responseDriver.getRecords().get(i).getDriverID());
                }
            }
            spinnerAdapterDriver.notifyDataSetChanged();
        });


        binding.WayID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                } else {
                    binding.NameArabic.setText(responseWay.getRecords().get(position - 1).getNameArabic().toString());
                    binding.NameEnglish.setText(responseWay.getRecords().get(position - 1).getNameEnglish().toString());
                    binding.Status.setText(responseWay.getRecords().get(position - 1).getStatus().toString());
                    binding.Stations.setText(responseWay.getRecords().get(position - 1).getStations().toString());
                    binding.EstimationTime.setText(responseWay.getRecords().get(position - 1).getEstimation_Time().toString());
                    binding.create.setText("تعديل");
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
        wayViewModel.fetchdata(binding.NameArabic.getText().toString(), binding.NameEnglish.getText().toString(), binding.Status.getText().toString(), binding.EstimationTime.getText().toString(), binding.Stations.getText().toString(), binding.DriverID.getSelectedItem().toString(), binding.VechileID.getSelectedItem().toString());
        wayViewModel.mutableLiveData.observe(getViewLifecycleOwner(), message -> Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_SHORT).show());
        clear();

    }

    public void update() {

        wayViewModel.updateData(binding.WayID.getSelectedItem().toString(), binding.NameArabic.getText().toString(), binding.NameEnglish.getText().toString(), binding.Status.getText().toString(), binding.EstimationTime.getText().toString(), binding.Stations.getText().toString(), binding.DriverID.getSelectedItem().toString(), binding.VechileID.getSelectedItem().toString());
        wayViewModel.mutableLiveData.observe(getViewLifecycleOwner(), message -> Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_SHORT).show());
        binding.create.setText("اضافه");
        State = "create";
        clear();

    }

    public void clear() {
        binding.NameArabic.setText("");
        binding.NameEnglish.setText("");
        binding.EstimationTime.setText("");
        binding.Status.setText("");
        binding.Stations.setText("");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLeak = null;
    }
}
