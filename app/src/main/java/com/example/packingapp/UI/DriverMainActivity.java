package com.example.packingapp.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityDriverMainBinding;

import androidx.appcompat.app.AppCompatActivity;

public class DriverMainActivity extends AppCompatActivity {
    ActivityDriverMainBinding binding;
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDriverMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnCheckin.setVisibility(View.GONE);
        binding.btnCheckout.setVisibility(View.GONE);
        database= AppDatabase.getDatabaseInstance(this);

        binding.confirmReceivedTrackingnumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(DriverMainActivity.this)
                        .setTitle(getString(R.string.delete_last_assigned_order_of_driver))
                        .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //TODO why you delete
                                database.userDao().deleteRecievePackedModule();
                                database.userDao().deleteDriverPackages_Header_DB();
                                database.userDao().deleteDriverPackages_Header_DB();
                                Intent gotoconfirmrecieve = new Intent(DriverMainActivity.this,RecievedPackedAndSortedOrderForSortingAndDriverActivity.class);
                                gotoconfirmrecieve.putExtra("RecievePackedOrConfirmForDriver","ConfirmForDriver");
                                startActivity(gotoconfirmrecieve);
                            }
                        })
                        .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent gotoconfirmrecieve = new Intent(DriverMainActivity.this,RecievedPackedAndSortedOrderForSortingAndDriverActivity.class);
                                gotoconfirmrecieve.putExtra("RecievePackedOrConfirmForDriver","ConfirmForDriver");
                                startActivity(gotoconfirmrecieve);
                            }
                        }).show();


            }
        });

        binding.btnShowMyOrdersOfRuntimesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(DriverMainActivity.this)
                        .setTitle(getString(R.string.delete_last_assigned_order_of_driver))
                        .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                database.userDao().deleteDriverPackages_Header_DB();
                                database.userDao().deleteDriverPackages_Details_DB();
                                Intent go=new Intent(DriverMainActivity.this ,OrdersOfRuntimeSheetNowForDriverActivity.class);
                                startActivity(go);
                            }
                        })
                        .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        }).show();

            }
        });



        binding.btnShowMyOrdersOfEndofday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent endofday=new Intent(DriverMainActivity.this,EndOfDayActivity.class);
                startActivity(endofday);
            }
        });

    }

}