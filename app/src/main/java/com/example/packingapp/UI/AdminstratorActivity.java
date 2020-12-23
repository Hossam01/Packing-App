package com.example.packingapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.databinding.ActivityAdminstratorBinding;
import com.example.packingapp.model.ModulesIDS;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class AdminstratorActivity extends AppCompatActivity {
ActivityAdminstratorBinding binding;
    AppDatabase database;
    private static final String TAG = "AdminstratorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminstratorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database= AppDatabase.getDatabaseInstance(this);

        List<ModulesIDS> modulesIDSList= database.userDao().getModules();
        Log.e(TAG, "onCreate: "+modulesIDSList.size() );

        for (int i=0;i<modulesIDSList.size();i++){

            if (modulesIDSList.get(i).getModules_ID().equalsIgnoreCase("1")){
                binding.btnPacked.setVisibility(View.VISIBLE);
            }else if (modulesIDSList.get(i).getModules_ID().equalsIgnoreCase("2")){
                binding.btnSortingTeam.setVisibility(View.VISIBLE);
            }else if (modulesIDSList.get(i).getModules_ID().equalsIgnoreCase("3")){
                binding.btnSortedTeam.setVisibility(View.VISIBLE);
            }else if (modulesIDSList.get(i).getModules_ID().equalsIgnoreCase("4")){
                binding.btnDriver.setVisibility(View.VISIBLE);
            }else if (modulesIDSList.get(i).getModules_ID().equalsIgnoreCase("6")){
                binding.btnSupervisor.setVisibility(View.VISIBLE);
            }
        }

        binding.btnPacked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GetOrderDatactivity.class);
                startActivity(i);
            }
        });

        binding.btnSortingTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.userDao().deleteRecievePackedModule();
                Intent i = new Intent(getApplicationContext(), RecievedPackedAndSortedOrderForSortingAndDriverActivity.class);
                i.putExtra("RecievePackedOrConfirmForDriver","RecievePacked");
                startActivity(i);
            }
        });
        binding.btnSortedTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.userDao().deleteRecievePackedModule();

                Intent i = new Intent(getApplicationContext(), AssignPackedOrderForZoneAndDriverActivity.class);
                startActivity(i);
            }
        });
        binding.btnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DriverMainActivity.class);
                startActivity(i);
            }
        });

        binding.btnSupervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddAndEditActivity.class);
                startActivity(i);
            }
        });
    }
}