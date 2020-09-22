package com.example.packingapp.UI;

import android.os.Bundle;

import com.example.packingapp.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}