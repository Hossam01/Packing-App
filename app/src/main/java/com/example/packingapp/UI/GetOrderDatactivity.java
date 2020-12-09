package com.example.packingapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityGetOrderDataBinding;
import com.example.packingapp.model.GetOrderResponse.ItemsOrderDataDBDetails;
import com.example.packingapp.model.GetOrderResponse.OrderDataModuleDBHeader;
import com.example.packingapp.model.GetOrderResponse.ResponseGetOrderData;
import com.example.packingapp.model.Message;
import com.example.packingapp.model.ResponseUpdateStatus;
import com.example.packingapp.viewmodel.GetOrderDataViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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


        binding.btnLoadingNewPurchaseOrder.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_GO
                        || actionId == EditorInfo.IME_ACTION_NEXT
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent == null
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER
                        || keyEvent.getAction() == KeyEvent.KEYCODE_NUMPAD_ENTER
                        || keyEvent.getAction() == KeyEvent.KEYCODE_DPAD_CENTER){
                    LoadNewPurchaseOrder();
                }

                return false;
            }
        });


        binding.btnLoadingNewPurchaseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadNewPurchaseOrder();
            }
        });

        binding.btnLoadingLastPurchaseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AssignItemToPackagesActivity.class);
                i.putExtra("AddNewPackageORAddForExistPackage","New");
                startActivity(i);
            }
        });

        binding.btnPrintAwb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialog alert = new ViewDialog();
                alert.showDialog(GetOrderDatactivity.this);
                UploadHeader();
                UploadDetails();
                //TODO Update staatus on magento
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

    private void LoadNewPurchaseOrder() {
        if (!binding.editMagentoorder.getText().toString().isEmpty()) {
            GETOrderData();
        }else {
            binding.editMagentoorder.setError("أدخل ");
        }
    }

    private void GETOrderData(){
        getOrderDataViewModel.fetchdata(binding.editMagentoorder.getText().toString());
        getOrderDataViewModel.getOrderDataLiveData().observe(GetOrderDatactivity.this,
                new Observer<ResponseGetOrderData>() {
            @Override
            public void onChanged(ResponseGetOrderData responseGetOrderData) {
                Log.e(TAG, "onChanged: "+responseGetOrderData.getStatus() );
                if (responseGetOrderData.getStatus().equalsIgnoreCase("picked")) {
                    ActionAfterGetData(responseGetOrderData);
                }else {
                    Toast.makeText(GetOrderDatactivity.this, "This Order in "+responseGetOrderData.getStatus()+" State", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getOrderDataViewModel.mutableLiveDataError.observe(GetOrderDatactivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChanged:mutableLiveD  "+s );
                if (s.contains("HTTP 400")) {
                    Toast.makeText(GetOrderDatactivity.this, String.format("%s", getString(R.string.order_not_found)), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(GetOrderDatactivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void ActionAfterGetData(ResponseGetOrderData responseGetOrderData) {
        OrderDataModuleDBHeader orderDataModuleDBHeader= new OrderDataModuleDBHeader(
                responseGetOrderData.getOrder_number(),
                responseGetOrderData.getOutBound_delivery(),
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
        //  database.userDao().UpdateOutBoundDelievery(binding.editOutbounddelievery.getText().toString(),responseGetOrderData.getOrder_number());
        database.userDao().insertOrderItems(responseGetOrderData.getItemsOrderDataDBDetails());
        Log.e(TAG, "zzz>> currency " +  responseGetOrderData.getItemsOrderDataDBDetails().size());
        Log.e(TAG, "zzz>> items size " + responseGetOrderData.getItemsOrderDataDBDetails().size());
        Log.e(TAG, "zzz>> Qty " + responseGetOrderData.getItemsOrderDataDBDetails().get(0).getQuantity());
        Log.e(TAG, "zzz>> sku " + responseGetOrderData.getItemsOrderDataDBDetails().get(0).getSku());
        // Toast.makeText(GetOrderDatactivity.this, responseGetOrderData.getOrder_number(), Toast.LENGTH_SHORT).show();

        //  Toast.makeText(GetOrderDatactivity.this, database.userDao().getHeader().size(), Toast.LENGTH_SHORT).show();
        // Log.e(TAG, "onChanged: ","ccc "+ database.userDao().getHeader().size());
//                        Toast.makeText(GetOrderDatactivity.this, database.userDao().getHeader().get(0).getOrder_number(), Toast.LENGTH_SHORT).show();
//                        Log.e(TAG, "onChanged: ","cccdd "+ database.userDao().getHeader().get(0).getOrder_number().toString());

        Intent i = new Intent(getApplicationContext(), AssignItemToPackagesActivity.class);
        i.putExtra("AddNewPackageORAddForExistPackage","New");
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

    private void UploadDetails() {
        if (database.userDao().getAllItemsWithoutTrackingnumber().size() == 0){
            List<ItemsOrderDataDBDetails> itemsOrderDataDBDetailsList = database.userDao().getDetailsTrackingnumberToUpload();
            String OrderNumber = database.userDao().getOrderNumber();
            OrderDataModuleDBHeader orderDataModuleDBHeader = database.userDao().getordernumberData(OrderNumber);

            float SumOfQTY = database.userDao().SumOfQTYFromDetials();
            Log.e(TAG, "UploadDetails:SumOfQTY "+SumOfQTY );
            float Shippingfees=orderDataModuleDBHeader.getShipping_fees();
            Log.e(TAG, "UploadDetails:Shippingfees "+Shippingfees );
            float ShippingfeesPerItem=Shippingfees/SumOfQTY ;
            Log.e(TAG, "UploadDetails:ShippingfeesPerItem "+ShippingfeesPerItem );

            getOrderDataViewModel.InsertOrderdataDetails(OrderNumber , itemsOrderDataDBDetailsList , ShippingfeesPerItem );

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
            List<String> NO_OF_PACKAGES =
                    database.userDao().getNoOfPackagesToUpload(orderDataModuleDBHeader.getOrder_number() +"%");
            Log.e(TAG, "UploadHeader:NO_OF_P "+NO_OF_PACKAGES.size() );
            /*Log.e(TAG, "zzUploadHeader:NO_OF_PAC: "+NO_OF_PACKAGES );
            Log.e(TAG, "zzUploadHeader:OutBo: "+orderDataModuleDBHeader.getOutBound_delivery() );
            Log.e(TAG, "zzUploadHeader:OutBo: "+orderDataModuleDBHeader.getCustomer_name() );
            Log.e(TAG, "zzUploadHeader:OutBo: "+orderDataModuleDBHeader.getCustomer_phone() );
            Log.e(TAG, "zzUploadHeader:OutBo: "+orderDataModuleDBHeader.getCustomer_code() );
            Log.e(TAG, "zzUploadHeader:OutBo: "+orderDataModuleDBHeader.getCustomer_address_govern() );
            Log.e(TAG, "zzUploadHeader:OutBo: "+orderDataModuleDBHeader.getCustomer_address_city() );
            Log.e(TAG, "zzUploadHeader:OutBo: "+orderDataModuleDBHeader.getCustomer_address_district() );
            Log.e(TAG, "zzUploadHeader:OutBo: "+orderDataModuleDBHeader.getCustomer_address_detail() );
            Log.e(TAG, "zzUploadHeader:OutBo: "+orderDataModuleDBHeader.getDelivery_date() );
            Log.e(TAG, "zzUploadHeader:OutBo: "+orderDataModuleDBHeader.getDelivery_time() );
            Log.e(TAG, "zzUploadHeader:OutBo: "+orderDataModuleDBHeader.getPicker_confirmation_time() );
            Log.e(TAG, "zzUploadHeader:OutBo: "+orderDataModuleDBHeader.getGrand_total() );
            Log.e(TAG, "zzUploadHeader:OutBo: "+orderDataModuleDBHeader.getCurrency() );

            Log.e(TAG, "zzUploadHeader:OutBo: "+orderDataModuleDBHeader.getShipping_fees() );
            Log.e(TAG, "zzUploadHeader:OutBo: "+NO_OF_PACKAGES );
            Log.e(TAG, "zzUploadHeader:OutBo: "+orderDataModuleDBHeader.getOut_From_Loc() );*/
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
                        String.valueOf(NO_OF_PACKAGES.size()),
                        orderDataModuleDBHeader.getOut_From_Loc()
            );

            getOrderDataViewModel.mutableLiveData.observe(GetOrderDatactivity.this, new Observer<Message>() {
                @Override
                public void onChanged(Message message) {
                    Toast.makeText(GetOrderDatactivity.this, ""+message.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onChanged: "+message.getMessage() );
                    Toast.makeText(GetOrderDatactivity.this, "Done for Header", Toast.LENGTH_SHORT).show();
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