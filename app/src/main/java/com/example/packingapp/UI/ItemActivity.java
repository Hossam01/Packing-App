package com.example.packingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityGetOrderDataBinding;
import com.example.packingapp.databinding.ActivityItemBinding;
import com.example.packingapp.model.OrderDataModuleDB;
import com.example.packingapp.model.Product;

public class ItemActivity extends AppCompatActivity {
    ActivityItemBinding binding;
    ItemAdapter itemAdapter;
    Product product;
    double totalPrice = 0.0;
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=AppDatabase.getDatabaseInstance(this);

        itemAdapter = new ItemAdapter();
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product = new Product("laptop", "5", 20000.0);
                itemAdapter.fillAdapterData(product);
                totalPrice += product.getPrice();
            }
        });
        binding.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDataModuleDB orderDataModuleDB=new OrderDataModuleDB("1452","ahmed","123","cairo","القاهره","1-5","00:00","pizza","5","10","zayed","order_num+");
                database.userDao().insertOrder(orderDataModuleDB);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        itemAdapter.clearAdapterData();
    }
}