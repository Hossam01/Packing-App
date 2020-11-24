package com.example.packingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityGetOrderDataBinding;
import com.example.packingapp.databinding.ActivityRecievePackedOrderForSortingBinding;
import com.example.packingapp.model.GetOrderResponse.OrderDataModuleDBHeader;
import com.example.packingapp.model.GetOrderResponse.ResponseGetOrderData;
import com.example.packingapp.model.RecievePacked.RecievePackedModule;
import com.example.packingapp.viewmodel.GetOrderDataViewModel;
import com.example.packingapp.viewmodel.RecievePackedOrderViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecievePackedOrderForSortingActivity extends AppCompatActivity {
ActivityRecievePackedOrderForSortingBinding binding;
    private static final String TAG = "RecievePackedOrderForSo";
    RecievePackedOrderViewModel recievePackedOrderViewModel;
    AppDatabase database;
    final Context context = this;
    String Zone ,trackingnumberIn;
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
                String OrderNumber;

                if (!binding.editTrackingnumber.getText().toString().isEmpty() &&
                        binding.editTrackingnumber.getText().toString().contains("-")) {
                     OrderNumber=
                            binding.editTrackingnumber.getText().toString().substring(0,
                                    binding.editTrackingnumber.getText().toString().indexOf("-"));

                    List<RecievePackedModule>  recievePackedlist=  database.userDao().getRecievePacked_ORDER_NO(OrderNumber);
                    if (recievePackedlist.size() == 0){
                        binding.editTrackingnumber.setError(null);

                        GETOrderData(binding.editTrackingnumber.getText().toString());
                        Log.e(TAG, "onClick: Ord "+OrderNumber );
                        Toast.makeText(RecievePackedOrderForSortingActivity.this, "تم", Toast.LENGTH_SHORT).show();
                    }else if (database.userDao().getRecievePacked_Tracking_Number(binding.editTrackingnumber.getText().toString())
                    .size() ==0){
                        binding.editTrackingnumber.setError(null);

                        GETOrderData(binding.editTrackingnumber.getText().toString());
                        Log.e(TAG, "onClick: Trac "+binding.editTrackingnumber.getText().toString() );
                        Toast.makeText(RecievePackedOrderForSortingActivity.this, "تم", Toast.LENGTH_SHORT).show();
                    }else {
                        binding.editTrackingnumber.setError("تم أدخال هذا من قبل ");
                        binding.editTrackingnumber.setText("");
                    }
                }else {

                    binding.editTrackingnumber.setError(getString(R.string.enter_Tracking_number));
                    binding.editTrackingnumber.setText("");
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
                        Toast.makeText(RecievePackedOrderForSortingActivity.this, String.format("%s",
                                getString(R.string.message_equalfornoofpaclkageandcountoftrackingnumbers)), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(RecievePackedOrderForSortingActivity.this, String.format("%s",
                                getString(R.string.message_not_equalfornoofpaclkageandcountoftrackingnumbers)), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RecievePackedOrderForSortingActivity.this, String.format("%s",
                            getString(R.string.there_is_no_trackednumber_scanned)), Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnDeleteLastTrackingnumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<RecievePackedModule>  recievePackedORDER_NO_Distinctlist=  database.userDao().getRecievePacked_ORDER_NO_Distinct();
                if (recievePackedORDER_NO_Distinctlist.size()>0) {
                    new AlertDialog.Builder(RecievePackedOrderForSortingActivity.this)
                            .setTitle(getString(R.string.delete_dialoge))
                            .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    database.userDao().deleteRecievePackedModule();
                                }
                            })
                            .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.cancel();
                                }
                            }).show();
                }else {
                    Toast.makeText(RecievePackedOrderForSortingActivity.this, "لايوجد بيانات للحذف", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnAssignOrdersToZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText zoneInput = (EditText) promptsView
                        .findViewById(R.id.edit_zone);
                final EditText trackingnumberInput = (EditText) promptsView
                        .findViewById(R.id.edit_trackingnumber);

                Zone = zoneInput.getText().toString();
                trackingnumberIn = trackingnumberInput.getText().toString();


                final Button btn_assign_zone = (Button) promptsView
                        .findViewById(R.id.btn_assign_to_zone);

                /*final Button btn_Dismiss = (Button) promptsView
                        .findViewById(R.id.btn_dismiss);
                btn_Dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();

                    }
                });*/

                btn_assign_zone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (trackingnumberInput.getText().toString().isEmpty()
                                && zoneInput.getText().toString().isEmpty()) {
                            AssignToZone(trackingnumberIn, Zone);
                        }else{
                            if (trackingnumberInput.getText().toString().isEmpty()){
                                trackingnumberInput.setError("Enter tracking number");
                            }else if (zoneInput.getText().toString().isEmpty())
                                zoneInput.setError("enter zone");
                        }
                    }
                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
    }

    private void AssignToZone(String trackingnumber1 ,String Zone1){
        String OrderNumber=
                trackingnumber1.substring(0,
                        trackingnumber1.indexOf("-"));

        List<RecievePackedModule>  recievePackedlist=  database.userDao().getRecievePacked_ORDER_NO(OrderNumber);
        if (recievePackedlist.size() == 0) {
            GETOrderData(binding.editTrackingnumber.getText().toString());
            database.userDao().UpdatezoneForORDER_NO(OrderNumber,Zone1);

        }
    }
    private void GETOrderData(String trackingnumber){
        recievePackedOrderViewModel.fetchdata(trackingnumber);
        recievePackedOrderViewModel.getOrderDataLiveData().observe(RecievePackedOrderForSortingActivity.this, new Observer<RecievePackedModule>() {
            @Override
            public void onChanged(RecievePackedModule responseGetOrderData) {
                Log.e(TAG, "onChanged: "+ responseGetOrderData.getNO_OF_PACKAGES());

                database.userDao().insertRecievePacked(new RecievePackedModule(
                        responseGetOrderData.getORDER_NO(),responseGetOrderData.getNO_OF_PACKAGES(),
                        trackingnumber));
                Log.e(TAG, "onChanged: insertR"+trackingnumber );
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