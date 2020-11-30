package com.example.packingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.packingapp.databinding.ActivityAdminstratorBinding;

public class AdminstratorActivity extends AppCompatActivity {
ActivityAdminstratorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminstratorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                Intent i = new Intent(getApplicationContext(), RecievedPackedAndSortedOrderForSortingAndDriverActivity.class);
                i.putExtra("RecievePackedOrConfirmForDriver","RecievePacked");
                startActivity(i);
            }
        });
        binding.btnSortedTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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