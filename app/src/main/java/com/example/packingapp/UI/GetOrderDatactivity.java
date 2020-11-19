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
import com.example.packingapp.model.GetOrderResponse.ItemsOrderDataDBDetails;
import com.example.packingapp.model.GetOrderResponse.OrderDataModuleDBHeader;
import com.example.packingapp.model.GetOrderResponse.ResponseGetOrderData;
import com.example.packingapp.model.Message;
import com.example.packingapp.model.ResponseUpdateStatus;
import com.example.packingapp.viewmodel.GetOrderDataViewModel;

import java.util.List;

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
                if (!binding.editOutbounddelievery.getText().toString().isEmpty()&&
                        !binding.editMagentoorder.getText().toString().isEmpty()) {
                    GETOrderData();
                }else {
                    if (!binding.editOutbounddelievery.getText().toString().isEmpty()) {
                        binding.editOutbounddelievery.setError("أدخل ");
                    }  else if (binding.editMagentoorder.getText().toString().isEmpty()){
                        binding.editOutbounddelievery.setError("أدخل ");
                    }
                }
            }
        });

        binding.btnLoadingLastPurchaseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AssignItemToPackagesActivity.class);
                startActivity(i);
            }
        });

        binding.btnPrintAwb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UploadHeader();
               // UploadDetails();
                UpdateStatus();
            }
        });

        binding.btnEditPackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (database.userDao().CheckItemsWithTrackingnumber().size() > 0){
                    Intent GoTopackedPackages=new Intent(GetOrderDatactivity.this, EditPackagesActivity.class);
                    startActivity(GoTopackedPackages);
                }else {
                    Toast.makeText(GetOrderDatactivity.this, "لا توجد عناصر  تم تعبئتها لتعديلها", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void GETOrderData(){
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
                        responseGetOrderData.getShipping_fees(),
                        responseGetOrderData.getPicker_confirmation_time(),
                        responseGetOrderData.getCurrency(),responseGetOrderData.getOut_From_Loc()
                );

                database.userDao().deleteAllHeader();
                database.userDao().deleteAllOrderItems();
                database.userDao().deleteAllTrckingNumber();
                database.userDao().insertOrderHeader(orderDataModuleDBHeader);
                database.userDao().UpdateOutBoundDelievery(binding.editOutbounddelievery.getText().toString(),responseGetOrderData.getOrder_number());
                database.userDao().insertOrderItems(responseGetOrderData.getItemsOrderDataDBDetails());
                Log.e(TAG, "zzz>> currency " +  responseGetOrderData.getCurrency());

                Log.e(TAG, "zzz>> items size " + responseGetOrderData.getItemsOrderDataDBDetails().size());
                Log.e(TAG, "zzz>> Qty " + responseGetOrderData.getItemsOrderDataDBDetails().get(0).getQuantity());
                Toast.makeText(GetOrderDatactivity.this, responseGetOrderData.getOrder_number(), Toast.LENGTH_SHORT).show();

                //  Toast.makeText(GetOrderDatactivity.this, database.userDao().getHeader().size(), Toast.LENGTH_SHORT).show();
                // Log.e(TAG, "onChanged: ","ccc "+ database.userDao().getHeader().size());
//                        Toast.makeText(GetOrderDatactivity.this, database.userDao().getHeader().get(0).getOrder_number(), Toast.LENGTH_SHORT).show();
//                        Log.e(TAG, "onChanged: ","cccdd "+ database.userDao().getHeader().get(0).getOrder_number().toString());

                Intent i = new Intent(getApplicationContext(), AssignItemToPackagesActivity.class);
                startActivity(i);
            }
        });

    }
    private void UploadDetails() {
        if (database.userDao().getAllItemsWithoutTrackingnumber().size() == 0){
            List<ItemsOrderDataDBDetails> itemsOrderDataDBDetailsList = database.userDao().getDetailsTrackingnumberToUpload();
            String OrderNumber = database.userDao().getOrderNumber();

            getOrderDataViewModel.InsertOrderdataDetails(OrderNumber , itemsOrderDataDBDetailsList);

            getOrderDataViewModel.mutableLiveData_Details.observe(GetOrderDatactivity.this, new Observer<Message>() {
                @Override
                public void onChanged(Message message) {
                    Toast.makeText(GetOrderDatactivity.this, ""+message.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onChanged: "+message.getMessage() );
                }
            });
        }else {
            Toast.makeText(GetOrderDatactivity.this, "توجد عناصر لم يتم تعبئتها", Toast.LENGTH_SHORT).show();
        }
    }

    public void UploadHeader(){
        if (database.userDao().getAllItemsWithoutTrackingnumber().size() == 0){
            OrderDataModuleDBHeader orderDataModuleDBHeader = database.userDao().getHeaderToUpload();
            getOrderDataViewModel.InsertOrderdataHeader(
                    orderDataModuleDBHeader.getOrder_number(),
                    orderDataModuleDBHeader.getOutBound_delivery(),
                    orderDataModuleDBHeader.getCustomer_name(),
                    orderDataModuleDBHeader.getCustomer_phone(),
                    orderDataModuleDBHeader.getCustomer_code(),
                    orderDataModuleDBHeader.getCustomer_address_govern(),
                    orderDataModuleDBHeader.getCustomer_address_city(),
                    orderDataModuleDBHeader.getCustomer_address_district(),
                    orderDataModuleDBHeader.getCustomer_address_detail(),
                    orderDataModuleDBHeader.getDelivery_date(),
                    orderDataModuleDBHeader.getDelivery_time(),
                    orderDataModuleDBHeader.getPicker_confirmation_time(),
                    orderDataModuleDBHeader.getGrand_total(),
                    orderDataModuleDBHeader.getCurrency(),
                    orderDataModuleDBHeader.getShipping_fees(),
                    orderDataModuleDBHeader.getOut_From_Loc()
            );

            getOrderDataViewModel.mutableLiveData.observe(GetOrderDatactivity.this, new Observer<Message>() {
                @Override
                public void onChanged(Message message) {
                    Toast.makeText(GetOrderDatactivity.this, ""+message.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onChanged: "+message.getMessage() );
                }
            });
        }else {
            Toast.makeText(GetOrderDatactivity.this, "توجد عناصر لم يتم تعبئتها", Toast.LENGTH_SHORT).show();
        }
    }

    public void UpdateStatus(){
//        if (database.userDao().getAllItemsWithoutTrackingnumber().size() == 0){
            OrderDataModuleDBHeader orderDataModuleDBHeader = database.userDao().getHeaderToUpload();
            getOrderDataViewModel.UpdateStatus(
                    orderDataModuleDBHeader.getOrder_number(),
                    "packed"
            );
            getOrderDataViewModel.mutableLiveData_UpdateStatus.observe(GetOrderDatactivity.this, new Observer<ResponseUpdateStatus>() {
                @Override
                public void onChanged(ResponseUpdateStatus message) {
                    Toast.makeText(GetOrderDatactivity.this, ""+message.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onChanged: "+message.getMessage() );
                }
            });
//        }else {
//            Toast.makeText(GetOrderDatactivity.this, "توجد عناصر لم يتم تعبئتها", Toast.LENGTH_SHORT).show();
//        }
    }


}