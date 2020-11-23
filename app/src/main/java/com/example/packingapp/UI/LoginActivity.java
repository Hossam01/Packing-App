package com.example.packingapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.databinding.ActivityLoginBinding;
import com.example.packingapp.model.ResponseLogin;
import com.example.packingapp.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
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
        lgoinViewModel.mutableLiveDataError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChanged: "+s );
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();

                if (s.equals("HTTP 503 Service Unavailable")) {
                    Toast.makeText(LoginActivity.this, "برجاء التاكد من اسم المستخدم ورقم السري", Toast.LENGTH_LONG).show();
                }
            }
        });
        lgoinViewModel.getSmsLiveData().observe(LoginActivity.this, new Observer<ResponseLogin>() {
            @Override
            public void onChanged(ResponseLogin responseLogin) {
                database.userDao().deleteAll();
                Log.e(TAG, "onChanged: "+responseLogin.getRecords().get(0).getGroupID() );
                database.userDao().insertUser(responseLogin.getRecords().get(0));

                if (responseLogin.getRecords().get(0).getGroupID().equalsIgnoreCase("1")){
                    Intent i = new Intent(getApplicationContext(), GetOrderDatactivity.class);
                    startActivity(i);
                }else if (responseLogin.getRecords().get(0).getGroupID().equalsIgnoreCase("2")){
                    Log.e(TAG, "onChanged: else if 2 " );

                    Intent i = new Intent(getApplicationContext(), RecievePackedOrderForSortingActivity.class);
                    startActivity(i);
                }else if (responseLogin.getRecords().get(0).getGroupID().equalsIgnoreCase("4")){
                    Log.e(TAG, "onChanged: else if 4 " );

                    Intent i = new Intent(getApplicationContext(), AddAndEditActivity.class);
                    startActivity(i);
                }

            }
        });
    }

}