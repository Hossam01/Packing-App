package com.example.packingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.packingapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    LoginViewModel lgoinViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        lgoinViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lgoinViewModel.fetchdata(binding.username.getText().toString(), binding.password.getText().toString());
            }
        });
        WorkerManagerApi.mutableLiveDataError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("HTTP 503 Service Unavailable")) {
                    Toast.makeText(LoginActivity.this, "برجاء التاكد من اسم المستخدم ورقم السري", Toast.LENGTH_LONG).show();
                }

            }
        });

        WorkerManagerApi.mutableLiveData.observe(LoginActivity.this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

}