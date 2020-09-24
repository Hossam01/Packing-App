package com.example.packingapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.model.Response;
import com.example.packingapp.workmanagerapi.WorkerManagerApiLogin;
import com.example.packingapp.databinding.ActivityLoginBinding;
import com.example.packingapp.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    LoginViewModel lgoinViewModel;
    AppDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        lgoinViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        database=AppDatabase.getDatabaseInstance(this);
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lgoinViewModel.fetchdata(binding.username.getText().toString(), binding.password.getText().toString());
            }
        });
        WorkerManagerApiLogin.mutableLiveDataError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("HTTP 503 Service Unavailable")) {
                    Toast.makeText(LoginActivity.this, "برجاء التاكد من اسم المستخدم ورقم السري", Toast.LENGTH_LONG).show();
                }

            }
        });

        WorkerManagerApiLogin.mutableLiveData.observe(LoginActivity.this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                database.userDao().insertUser(response);
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

}