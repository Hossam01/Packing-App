package com.example.packingapp.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.packingapp.databinding.ActivitySendSMSBinding;
import com.example.packingapp.model.ResponseSms;
import com.example.packingapp.viewmodel.OrderDetailsForDriverViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class SendSMSActivity extends AppCompatActivity {
    ActivitySendSMSBinding binding;
    OrderDetailsForDriverViewModel SmsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendSMSBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SmsViewModel = ViewModelProviders.of(this).get(OrderDetailsForDriverViewModel.class);

        binding.send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SmsViewModel.SendSms(binding.phone.getText().toString(), binding.message.getText().toString());
                SmsViewModel.getSmsLiveData().observe(SendSMSActivity.this, new Observer<ResponseSms>() {
                    @Override
                    public void onChanged(ResponseSms responseSms) {
                        Toast.makeText(SendSMSActivity.this,
                                responseSms.getSMSStatus().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}