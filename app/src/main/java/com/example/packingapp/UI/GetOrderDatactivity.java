package com.example.packingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.databinding.ActivityGetOrderDataBinding;
import com.example.packingapp.model.GetOrderResponse.OrderDataModuleDBHeader;
import com.example.packingapp.model.GetOrderResponse.ResponseGetOrderData;
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
                getOrderDataViewModel.getOrderDataLiveData().observe(GetOrderDatactivity.this, new Observer<ResponseGetOrderData>() {
                    @Override
                    public void onChanged(ResponseGetOrderData responseGetOrderData) {
                        OrderDataModuleDBHeader orderDataModuleDBHeader= new OrderDataModuleDBHeader(
                                responseGetOrderData.getOrder_number(),
                                responseGetOrderData.getCustomer().getName(),
                                responseGetOrderData.getCustomer().getPhone_number(),
                                responseGetOrderData.getCustomer().getCustomer_code(),
                                responseGetOrderData.getCustomer().getAddress().getGovern(),
                                responseGetOrderData.getCustomer().getAddress().getCity(),
                                responseGetOrderData.getCustomer().getAddress().getDistrict(),
                                responseGetOrderData.getCustomer().getAddress().getCustomer_address_detail(),
                                responseGetOrderData.getDelivery().getDate(),
                                responseGetOrderData.getDelivery().getTime(),
                                responseGetOrderData.getGrand_total(),
                                responseGetOrderData.getCurrency()
                                );

                        database.userDao().deleteAllHeader();
                        database.userDao().deleteAllOrderItems();
                       database.userDao().insertOrderHeader(orderDataModuleDBHeader);
                       database.userDao().insertOrderItems(responseGetOrderData.getItemsOrderDataDBDetails());
                        Log.e(TAG, "zzz>> currency " +  responseGetOrderData.getCurrency());

                        Log.e(TAG, "zzz>> items size " + responseGetOrderData.getItemsOrderDataDBDetails().size());
                        Log.e(TAG, "zzz>> Qty " + responseGetOrderData.getItemsOrderDataDBDetails().get(0).getQuantity());
                       Toast.makeText(GetOrderDatactivity.this, responseGetOrderData.getOrder_number(), Toast.LENGTH_SHORT).show();

                      //  Toast.makeText(GetOrderDatactivity.this, database.userDao().getHeader().size(), Toast.LENGTH_SHORT).show();
                       // Log.e(TAG, "onChanged: ","ccc "+ database.userDao().getHeader().size());
//                        Toast.makeText(GetOrderDatactivity.this, database.userDao().getHeader().get(0).getOrder_number(), Toast.LENGTH_SHORT).show();
//                        Log.e(TAG, "onChanged: ","cccdd "+ database.userDao().getHeader().get(0).getOrder_number().toString());

                        Intent i = new Intent(getApplicationContext(), ItemActivity.class);
                        startActivity(i);
                    }
                });
            }
        });
    }
}