package com.example.packingapp.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.packingapp.databinding.ActivitySendSMSBinding;
import com.example.packingapp.model.ResponseSms;
import com.example.packingapp.viewmodel.SendSmsViewModel;

public class SendSMSActivity extends AppCompatActivity {
    ActivitySendSMSBinding binding;
    SendSmsViewModel SmsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendSMSBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SmsViewModel = ViewModelProviders.of(this).get(SendSmsViewModel.class);

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsViewModel.fetchdata(binding.phone.getText().toString(), binding.message.getText().toString());
                SmsViewModel.getSmsLiveData().observe(SendSMSActivity.this, new Observer<ResponseSms>() {
                    @Override
                    public void onChanged(ResponseSms responseSms) {
                        Toast.makeText(SendSMSActivity.this, responseSms.getSMSStatus().toString(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}