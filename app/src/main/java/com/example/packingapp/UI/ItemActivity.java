package com.example.packingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.packingapp.Adapter.ItemAdapter;
import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityItemBinding;
import com.example.packingapp.model.GetOrderResponse.ItemsOrderDataDBDetails;
import com.example.packingapp.model.Product;

public class ItemActivity extends AppCompatActivity {
    private static final String TAG = "ItemActivity";
    ActivityItemBinding binding;
    ItemAdapter itemAdapter;
    Product product;
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=AppDatabase.getDatabaseInstance(this);

        itemAdapter = new ItemAdapter();
        binding.recyclerView.setAdapter(itemAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.imagSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.editBarcode.getText().toString().isEmpty()) {
                    ItemsOrderDataDBDetails ItemsOrderDataDBDetails =database.userDao().getItem(binding.editBarcode.getText().toString());
                    product = new Product("laptop", "5");
                    Log.e(TAG, "onClick            }\n: " + ItemsOrderDataDBDetails.getQuantity().toString());
                    itemAdapter.fillAdapterData(ItemsOrderDataDBDetails);
                } else {
                    binding.editBarcode.setError(getResources().getString(R.string.enter_barcode));
                    binding.editBarcode.requestFocus();
                }
            }
        });

        binding.btnAssignItemsToPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ItemsOrderDataDBDetails orderDataModuleDB=new
//                        ItemsOrderDataDBDetails("1452","ahmed","123","cairo","القاهره",
//                        "1-5","00:00","pizza","5","10","zayed","order_num+");
//                database.userDao().insertOrder(orderDataModuleDB);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        itemAdapter.clearAdapterData();
    }
}