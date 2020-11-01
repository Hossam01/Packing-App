package com.example.packingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityGetOrderDataBinding;
import com.example.packingapp.model.OrderDataModuleDB;
import com.example.packingapp.model.ResponseGetOrderData;
import com.example.packingapp.model.ResponseSms;
import com.example.packingapp.viewmodel.GetOrderDataViewModel;

public class GetOrderDatactivity extends AppCompatActivity {
    ActivityGetOrderDataBinding binding;
    GetOrderDataViewModel getOrderDataViewModel;
    private static final String TAG = "GetOrderDatactivity";
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGetOrderDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=AppDatabase.getDatabaseInstance(this);

        getOrderDataViewModel= ViewModelProviders.of(this).get(GetOrderDataViewModel.class);

        binding.btnLoadingNewPurchaseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOrderDataViewModel.fetchdata(binding.editMagentoorder.getText().toString());
                getOrderDataViewModel.getOrderDataLiveData().observe(GetOrderDatactivity.this, new Observer<OrderDataModuleDB>() {
                    @Override
                    public void onChanged(OrderDataModuleDB responseGetOrderData) {
                        database.userDao().insertOrder(responseGetOrderData);
                        Toast.makeText(GetOrderDatactivity.this, responseGetOrderData.getOrder_number().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}