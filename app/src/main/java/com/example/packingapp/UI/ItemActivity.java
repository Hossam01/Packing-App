package com.example.packingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityGetOrderDataBinding;
import com.example.packingapp.databinding.ActivityItemBinding;
import com.example.packingapp.model.Product;

public class ItemActivity extends AppCompatActivity {
    ActivityItemBinding binding;
    ItemAdapter itemAdapter;
    Product product;
    double totalPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        itemAdapter = new ItemAdapter();
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product = new Product("laptop", "5", 20000.0);
                itemAdapter.fillAdapterData(product);
                totalPrice += product.getPrice();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        itemAdapter.clearAdapterData();
    }
}