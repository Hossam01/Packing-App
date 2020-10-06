package com.example.packingapp.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.packingapp.databinding.ActivitySendSMSBinding;

public class SendSMSActivity extends AppCompatActivity {
    ActivitySendSMSBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendSMSBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}