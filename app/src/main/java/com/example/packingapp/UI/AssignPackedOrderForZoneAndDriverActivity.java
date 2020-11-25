package com.example.packingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityAssignPackedOrderForZoneDriverBinding;
import com.example.packingapp.model.RecievePacked.RecievePackedModule;
import com.example.packingapp.model.ResponseDriver;
import com.example.packingapp.model.ResponseUpdateStatus;
import com.example.packingapp.viewmodel.AssignPackedOrderToZoneViewModel;

import java.util.ArrayList;
import java.util.List;

public class AssignPackedOrderForZoneAndDriverActivity extends AppCompatActivity {
    private static final String TAG = "AssignPackedOrderForZon";
ActivityAssignPackedOrderForZoneDriverBinding binding;
    AssignPackedOrderToZoneViewModel assignPackedOrderToZoneViewModel;
    AppDatabase database;
    final Context context = this;
    String Zone ,trackingnumberIn;
    ArrayList<String> Drivers_IDs_list ;
    ResponseDriver responseDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAssignPackedOrderForZoneDriverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=AppDatabase.getDatabaseInstance(this);

        assignPackedOrderToZoneViewModel= ViewModelProviders.of(this).get(AssignPackedOrderToZoneViewModel.class);

        GETDriverID();

       ControlLayout();

       ButtonsClickListnerForAssignToZone();
        
    }

    private void ButtonsClickListnerForAssignToZone() {
        binding.btnLoadingNewPurchaseOrderZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.editTrackingnumberZone.getText().toString().isEmpty() &&
                        !binding.editZone.getText().toString().isEmpty()) {
                    LoadNewPurchaseOrderBTN_Zone();
                }else {
                    if (binding.editTrackingnumberZone.getText().toString().isEmpty()) {
                        binding.editTrackingnumberZone.setError("أدخل السريال");
                        binding.editTrackingnumberZone.requestFocus();
                    }else if (binding.editZone.getText().toString().isEmpty()){
                        binding.editZone.setError("أدخل المنطقه");
                        binding.editZone.requestFocus();
                    }
                }
            }
        });


        binding.btnUpdateOrderStatusZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<RecievePackedModule>  recievePackedORDER_NO_Distinctlist=  database.userDao().getRecievePacked_ORDER_NO_Distinct();
                List<RecievePackedModule>  NOTrecievedPackedORDERlist=  new ArrayList<>();
                if (recievePackedORDER_NO_Distinctlist.size()>0) {
                    for (int i = 0; i>recievePackedORDER_NO_Distinctlist.size();i++){
                        List<String>  recievePacked_Tracking_Number_countlist=
                                database.userDao().getRecievePacked_Tracking_Number_count(recievePackedORDER_NO_Distinctlist.get(i).getORDER_NO());
                        if (!recievePacked_Tracking_Number_countlist.get(i).
                                equalsIgnoreCase(recievePackedORDER_NO_Distinctlist.get(i).getNO_OF_PACKAGES().toString())){
                            NOTrecievedPackedORDERlist.add(recievePackedORDER_NO_Distinctlist.get(i));
                        }
                    }

                    if (NOTrecievedPackedORDERlist.size()==0){
                        //TODO UPDATE STATUS
                        // Toast.makeText(RecievePackedOrderForSortingActivity.this, String.format("%s",getString(R.string.message_equalfornoofpaclkageandcountoftrackingnumbers)), Toast.LENGTH_SHORT).show();
                        UpdateStatus_ON_83("in sorting");
                        UpdateStatus("in sorting");
                    }else {
                        Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, String.format("%s",
                                getString(R.string.message_not_equalfornoofpaclkageandcountoftrackingnumbers)), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, String.format("%s",
                            getString(R.string.there_is_no_trackednumber_scanned)), Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnDeleteLastTrackingnumbersZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<RecievePackedModule>  recievePackedORDER_NO_Distinctlist=  database.userDao().getRecievePacked_ORDER_NO_Distinct();
                if (recievePackedORDER_NO_Distinctlist.size()>0) {
                    new AlertDialog.Builder(AssignPackedOrderForZoneAndDriverActivity.this)
                            .setTitle(getString(R.string.delete_dialoge))
                            .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    database.userDao().deleteRecievePackedModule();
                                    binding.editTrackingnumberZone.setError(null);
                                    binding.editZone.setError(null);
                                    binding.editZone.setText("");
                                }
                            })
                            .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.cancel();
                                }
                            }).show();
                }else {
                    Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, "لايوجد بيانات للحذف", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnAssignOrdersToZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.editTrackingnumberZone.getText().toString().isEmpty()
                        && binding.editZone.getText().toString().isEmpty()) {
                    UpdateStatus("sorted");
                    UpdateStatus_ON_83("sorted");
                }else{
                    if (binding.editTrackingnumberZone.getText().toString().isEmpty()){
                        binding.editTrackingnumberZone.setError("Enter tracking number");
                    }else if (binding.editZone.getText().toString().isEmpty())
                        binding.editZone.setError("enter zone");

                }
            }
        });
    }


    private void ControlLayout() {
        binding.btnShowAssignOrdersToZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.linearAssignOrderToZone.getVisibility() == View.GONE) {
                    binding.linearAssignOrderToZone.setVisibility(View.VISIBLE);
                    binding.linearAssignOrderToDriver.setVisibility(View.GONE);
                }else if (binding.linearAssignOrderToZone.getVisibility() == View.VISIBLE){
                    binding.linearAssignOrderToZone.setVisibility(View.GONE);
                    binding.linearAssignOrderToDriver.setVisibility(View.GONE);
                }
            }
        });

        binding.btnShowAssignOrdersToDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.linearAssignOrderToDriver.getVisibility() == View.GONE) {
                    binding.linearAssignOrderToZone.setVisibility(View.GONE);
                    binding.linearAssignOrderToDriver.setVisibility(View.VISIBLE);
                }else if (binding.linearAssignOrderToDriver.getVisibility() == View.VISIBLE){
                    binding.linearAssignOrderToZone.setVisibility(View.GONE);
                    binding.linearAssignOrderToDriver.setVisibility(View.GONE);
                }



            }
        });
    }



    private void LoadNewPurchaseOrderBTN_Zone() {
        String OrderNumber;
        if (
                //!binding.editTrackingnumberZone.getText().toString().isEmpty() &&
                binding.editTrackingnumberZone.getText().toString().contains("-")) {
            OrderNumber=
                    binding.editTrackingnumberZone.getText().toString().substring(0,
                            binding.editTrackingnumberZone.getText().toString().indexOf("-"));

            List<RecievePackedModule> recievePackedlist=  database.userDao().getRecievePacked_ORDER_NO(OrderNumber);
            if (recievePackedlist.size() == 0){
                binding.editTrackingnumberZone.setError(null);

                GETOrderData(binding.editTrackingnumberZone.getText().toString());
                AssignToZone(binding.editTrackingnumberZone.getText().toString()
                        ,binding.editZone.getText().toString());
                Log.e(TAG, "onClick: Ord "+OrderNumber );
            //    Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, "تم", Toast.LENGTH_SHORT).show();
            }else if (database.userDao().getRecievePacked_Tracking_Number(binding.editTrackingnumberZone.getText().toString())
                    .size() ==0){
                binding.editTrackingnumberZone.setError(null);

                GETOrderData(binding.editTrackingnumberZone.getText().toString());
                Log.e(TAG, "onClick: Trac "+binding.editTrackingnumberZone.getText().toString() );
            //    Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, "تم", Toast.LENGTH_SHORT).show();
                AssignToZone(binding.editTrackingnumberZone.getText().toString()
                        ,binding.editZone.getText().toString());
            }else {
                binding.editTrackingnumberZone.setError("تم أدخال هذا من قبل ");
                binding.editTrackingnumberZone.setText("");
                binding.editZone.setText("");
                binding.editTrackingnumberZone.requestFocus();
            }
        }else {

            binding.editTrackingnumberZone.setError(getString(R.string.enter_valid_tracking_number));
            binding.editTrackingnumberZone.setText("");
            binding.editZone.setText("");
            binding.editTrackingnumberZone.requestFocus();

        }

    }

   private void AssignToZone(String trackingnumber1 ,String Zone1 ){
        String OrderNumber=
                trackingnumber1.substring(0,
                        trackingnumber1.indexOf("-"));

        List<RecievePackedModule>  recievePackedlist =  database.userDao().getRecievePacked_ORDER_NO(OrderNumber);
        if (recievePackedlist.size() == 0) {
            GETOrderData(trackingnumber1);
            AssignToZone(trackingnumber1,Zone1);
            Toast.makeText(context, "تم", Toast.LENGTH_SHORT).show();
        }else if (recievePackedlist.size() >= 0){
            if (recievePackedlist.get(0).getZone().isEmpty()) {
                database.userDao().UpdatezoneForORDER_NO(OrderNumber,Zone1);
                Toast.makeText(context, "تم", Toast.LENGTH_SHORT).show();
            }else if (!recievePackedlist.get(0).getZone().isEmpty()){
                new AlertDialog.Builder(AssignPackedOrderForZoneAndDriverActivity.this)
                        .setTitle(getString(R.string.updte_zone_if_exist))
                        .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                database.userDao().UpdatezoneForORDER_NO(OrderNumber,Zone1);
                                Toast.makeText(context, "تم", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        }).show();
            }
        }
    }

    private void GETOrderData(String trackingnumber ){
        assignPackedOrderToZoneViewModel.fetchdata(trackingnumber);
        assignPackedOrderToZoneViewModel.getOrderDataLiveData().observe(AssignPackedOrderForZoneAndDriverActivity.this, new Observer<RecievePackedModule>() {
            @Override
            public void onChanged(RecievePackedModule responseGetOrderData) {
                    Log.e(TAG, "onChanged: " + responseGetOrderData.getNO_OF_PACKAGES());

                    database.userDao().insertRecievePacked(new RecievePackedModule(
                            responseGetOrderData.getORDER_NO(), responseGetOrderData.getNO_OF_PACKAGES(),
                            trackingnumber));
                    binding.editTrackingnumberZone.setText("");
                    binding.editTrackingnumberZone.setError(null);
                    binding.editZone.setText("");
                    binding.editZone.setError(null);
                    Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, "تم", Toast.LENGTH_SHORT).show();

                    Log.e(TAG, "onChanged: insertR" + trackingnumber);

            }

        });

        assignPackedOrderToZoneViewModel.mutableLiveDataError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChanged: "+s );

                if (s.equals("HTTP 503 Service Unavailable")) {
                      Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, "السريال غير موجود على السرفير", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, s, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void LoadNewPurchaseOrderBTN_Driver() {
        String OrderNumber;
        if (!binding.editTrackingnumberDriver.getText().toString().isEmpty() &&
                binding.editTrackingnumberDriver.getText().toString().contains("-")) {
            OrderNumber=
                    binding.editTrackingnumberDriver.getText().toString().substring(0,
                            binding.editTrackingnumberDriver.getText().toString().indexOf("-"));

            List<RecievePackedModule> recievePackedlist=  database.userDao().getRecievePacked_ORDER_NO(OrderNumber);
            if (recievePackedlist.size() == 0){
                binding.editTrackingnumberDriver.setError(null);

                GETOrderData(binding.editTrackingnumberDriver.getText().toString());
                Log.e(TAG, "onClick: Ord "+OrderNumber );
                Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, "تم", Toast.LENGTH_SHORT).show();
            }else if (database.userDao().getRecievePacked_Tracking_Number(binding.editTrackingnumberDriver.getText().toString())
                    .size() ==0){
                binding.editTrackingnumberDriver.setError(null);

                GETOrderData(binding.editTrackingnumberDriver.getText().toString());
                Log.e(TAG, "onClick: Trac "+binding.editTrackingnumberDriver.getText().toString() );
                Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, "تم", Toast.LENGTH_SHORT).show();
            }else {
                binding.editTrackingnumberDriver.setError("تم أدخال هذا من قبل ");
                binding.editTrackingnumberDriver.setText("");
                binding.editTrackingnumberDriver.requestFocus();
            }
        }else {

            binding.editTrackingnumberDriver.setError(getString(R.string.enter_tracking_number));
            binding.editTrackingnumberDriver.setText("");
            binding.editTrackingnumberDriver.requestFocus();

        }

    }

    public void UpdateStatus_ON_83(String Status){
//        if (database.userDao().getAllItemsWithoutTrackingnumber().size() == 0){
        List<RecievePackedModule> orderDataModuleDBHeaderkist = database.userDao().getorderNORecievePackedModule();
        if (orderDataModuleDBHeaderkist.size() > 0) {
            assignPackedOrderToZoneViewModel.UpdateStatus_ON_83(
                    orderDataModuleDBHeaderkist.get(0).getORDER_NO(),
                    Status
            );
        }else {
            Toast.makeText(context, "لم يتم أدخال ", Toast.LENGTH_SHORT).show();
        }
        assignPackedOrderToZoneViewModel.mutableLiveData_UpdateStatus_ON_83.observe(AssignPackedOrderForZoneAndDriverActivity.this, new Observer<ResponseUpdateStatus>() {
            @Override
            public void onChanged(ResponseUpdateStatus message) {
                Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, ""+message.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onChanged:update "+message.getMessage() );
            }
        });
//        }else {
//            Toast.makeText(GetOrderDatactivity.this, "توجد عناصر لم يتم تعبئتها", Toast.LENGTH_SHORT).show();
//        }
    }

    public void UpdateStatus(String Status){
//        if (database.userDao().getAllItemsWithoutTrackingnumber().size() == 0){

        List<RecievePackedModule> orderDataModuleDBHeaderkist = database.userDao().getorderNORecievePackedModule();

        assignPackedOrderToZoneViewModel.UpdateStatus(
                orderDataModuleDBHeaderkist.get(0).getORDER_NO(),
                Status
        );
        assignPackedOrderToZoneViewModel.mutableLiveData_UpdateStatus.observe(AssignPackedOrderForZoneAndDriverActivity.this, new Observer<ResponseUpdateStatus>() {
            @Override
            public void onChanged(ResponseUpdateStatus message) {
                Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, ""+message.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onChanged:UpdateStatus "+message.getMessage() );
            }
        });
        assignPackedOrderToZoneViewModel.mutableLiveDataError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChanged: "+s );
                Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });


    }

    private void GETDriverID() {
        Drivers_IDs_list =new ArrayList<>();
        assignPackedOrderToZoneViewModel.GetDriversID();
        //TODO TO ASSIGN ID TO SPINNER
        ArrayAdapter<String> spinnerAdapterDriver=new ArrayAdapter<String>(AssignPackedOrderForZoneAndDriverActivity.this,
                android.R.layout.simple_spinner_item,Drivers_IDs_list);
        spinnerAdapterDriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinerDriverId.setAdapter(spinnerAdapterDriver);

        assignPackedOrderToZoneViewModel.mutableLiveDataRead.observe(AssignPackedOrderForZoneAndDriverActivity.this
                , (ResponseDriver responseDriver) -> {
                    if (Drivers_IDs_list.size() == 0) {
                        for (int i = 0; i < responseDriver.getRecords().size(); i++) {
                            if (i == 0)
                                Drivers_IDs_list.add("Select ID Driver");
                            Drivers_IDs_list.add(responseDriver.getRecords().get(i).getDriverID());
                        }
                    }
                    this.responseDriver = responseDriver;
                    spinnerAdapterDriver.notifyDataSetChanged();

                });
    }

}