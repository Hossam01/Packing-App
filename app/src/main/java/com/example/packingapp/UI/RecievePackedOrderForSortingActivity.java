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
import com.example.packingapp.databinding.ActivityRecievePackedOrderForSortingBinding;
import com.example.packingapp.model.GetOrderResponse.OrderDataModuleDBHeader;
import com.example.packingapp.model.GetOrderResponse.ResponseGetOrderData;
import com.example.packingapp.model.RecievePacked.RecievePackedModule;
import com.example.packingapp.viewmodel.GetOrderDataViewModel;
import com.example.packingapp.viewmodel.RecievePackedOrderViewModel;

import java.util.List;

public class RecievePackedOrderForSortingActivity extends AppCompatActivity {
ActivityRecievePackedOrderForSortingBinding binding;
    private static final String TAG = "RecievePackedOrderForSo";
    RecievePackedOrderViewModel recievePackedOrderViewModel;
    AppDatabase database;
    String OrderNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRecievePackedOrderForSortingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=AppDatabase.getDatabaseInstance(this);

        recievePackedOrderViewModel= ViewModelProviders.of(this).get(RecievePackedOrderViewModel.class);

        binding.btnLoadingNewPurchaseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.editTrackingnumber.getText().toString().isEmpty()) {
                     OrderNumber=
                            binding.editTrackingnumber.getText().toString().substring(0,
                                    binding.editTrackingnumber.getText().toString().indexOf("-"));

                    List<RecievePackedModule>  recievePackedlist=  database.userDao().getRecievePacked_ORDER_NO(OrderNumber);
                    if (recievePackedlist.size() == 0){
                        GETOrderData();
                        Log.e(TAG, "onClick: Ord "+OrderNumber );

                    }else if (database.userDao().getRecievePacked_Tracking_Number(binding.editTrackingnumber.getText().toString())
                    .size() ==0){
                        GETOrderData();
                        Log.e(TAG, "onClick: Trac "+binding.editTrackingnumber.getText().toString() );
                    }else {
                        binding.editTrackingnumber.setError("تم أدخال هذا من قبل ");
                    }
                }else {
                    binding.editTrackingnumber.setError("أدخل ");
                }
            }
        });

//        binding.btnLoadingLastPurchaseOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), AssignItemToPackagesActivity.class);
//                startActivity(i);
//            }
//        });
//
//        binding.btnPrintAwb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//               // UpdateStatus();
//            }
//        });
//
//        binding.btnEditPackages.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (database.userDao().CheckItemsWithTrackingnumber().size() > 0){
//                    Intent GoTopackedPackages=new Intent(RecievePackedOrderForSortingActivity.this, EditPackagesActivity.class);
//                    startActivity(GoTopackedPackages);
//                }else {
//                    Toast.makeText(RecievePackedOrderForSortingActivity.this, "لا توجد عناصر  تم تعبئتها لتعديلها", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        binding.btnUpdateOrderStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    List<RecievePackedModule>  recievePackedlist=  database.userDao().getRecievePacked_ORDER_NO(OrderNumber);
                    if (recievePackedlist.size() == 0){
                        GETOrderData();
                        Log.e(TAG, "onClick: Ord "+OrderNumber );

                    }else if (database.userDao().getRecievePacked_Tracking_Number(binding.editTrackingnumber.getText().toString())
                            .size() ==0){
                        GETOrderData();
                        Log.e(TAG, "onClick: Trac "+binding.editTrackingnumber.getText().toString() );
                    }else {
                        binding.editTrackingnumber.setError("تم أدخال هذا من قبل ");
                    }

            }
        });
    }

    private void GETOrderData(){
        recievePackedOrderViewModel.fetchdata(binding.editTrackingnumber.getText().toString());
        recievePackedOrderViewModel.getOrderDataLiveData().observe(RecievePackedOrderForSortingActivity.this, new Observer<RecievePackedModule>() {
            @Override
            public void onChanged(RecievePackedModule responseGetOrderData) {
                Log.e(TAG, "onChanged: "+ responseGetOrderData.getNO_OF_PACKAGES());

                database.userDao().insertRecievePacked(new RecievePackedModule(
                        responseGetOrderData.getORDER_NO(),responseGetOrderData.getNO_OF_PACKAGES(),
                        binding.editTrackingnumber.getText().toString()));
                Log.e(TAG, "onChanged: insertR"+binding.editTrackingnumber.getText().toString() );
            }

        });

//        recievePackedOrderViewModel.mutableLiveDataError.observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                Log.e(TAG, "onChanged: "+s );
//                Toast.makeText(RecievePackedOrderForSortingActivity.this, s, Toast.LENGTH_LONG).show();
//
//                if (s.equals("HTTP 503 Service Unavailable")) {
//                    Toast.makeText(RecievePackedOrderForSortingActivity.this, "برجاء التاكد من اسم المستخدم ورقم السري", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }



}