package com.example.packingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.packingapp.Adapter.ItemAdapter;
import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityItemBinding;
import com.example.packingapp.model.GetOrderResponse.ItemsOrderDataDBDetails;
import com.example.packingapp.model.Product;

import java.util.ArrayList;
import java.util.List;

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

                    ItemsOrderDataDBDetails itemsOrderDataDBDetails =database.userDao().getItem(binding.editBarcode.getText().toString());
//                    product = new Product("laptop", "5");

                    if (itemsOrderDataDBDetails != null) {

                        List <ItemsOrderDataDBDetails> Adapterlist = itemAdapter.ReturnListOfAdapter();
                        List <String> listClone = new ArrayList<>();

                        for (ItemsOrderDataDBDetails itemsOrderDataDBDetailsSE : Adapterlist) {
                            //matches("(?i)"+binding.editBarcode.getText().toString()+".*")
                            if(itemsOrderDataDBDetailsSE.getBarcode().toString().equalsIgnoreCase(binding.editBarcode.getText().toString())){
                                listClone.add(itemsOrderDataDBDetailsSE.getBarcode());
                            }
                        }
                        System.out.println(listClone.size());
                        System.out.println(binding.editBarcode.getText().toString());
                        //Log.e(TAG, "onClick: ", "" + binding.editBarcode.getText().toString() );
                        if (listClone.size()>0){
                            itemAdapter.fillAdapterData(itemsOrderDataDBDetails);
                        }else {
                            Toast.makeText(ItemActivity.this, "تم أضافه هذا من قبل", Toast.LENGTH_SHORT).show();
                        }
                    }
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