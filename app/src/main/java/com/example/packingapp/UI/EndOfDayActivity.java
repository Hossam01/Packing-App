package com.example.packingapp.UI;

import android.os.Bundle;
import android.util.Log;

import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.databinding.ActivityEndOfDayBinding;
import com.example.packingapp.model.DriverModules.EndOfDayModule;
import com.example.packingapp.model.DriverModules.ResponeEndOfDay;
import com.example.packingapp.model.RecordsItem;
import com.example.packingapp.viewmodel.EndOfDayViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class EndOfDayActivity extends AppCompatActivity {
    private static final String TAG = "EndOfDayActivity";
    AppDatabase database;
    EndOfDayViewModel endOfDayViewModel;
    ActivityEndOfDayBinding binding;
    List<EndOfDayModule> FailedList ,SuccessList;
    Double FailedValue=0.0,SuccessValue=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEndOfDayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database= AppDatabase.getDatabaseInstance(this);
        endOfDayViewModel= ViewModelProviders.of(this).get(EndOfDayViewModel.class);
        RecordsItem recordsItem = database.userDao().getUserData_MU();
        Log.e(TAG, "onCreate: "+recordsItem.getUser_id() );

        FailedList=new ArrayList<>();
        SuccessList=new ArrayList<>();

        endOfDayViewModel.GetOrderForEndOfDay_ON_83(recordsItem.getUser_id());
        endOfDayViewModel.DriverOrdersReadyDataLiveData().observe(EndOfDayActivity.this,
                new Observer<ResponeEndOfDay>() {
                    @Override
                    public void onChanged(ResponeEndOfDay responeEndOfDay) {
                        Log.e(TAG, "onChanged: "+responeEndOfDay.getEndOfDayModule().size() );
//                        DriverPackages_DB driverPackages_respones_recycler1=
//                                new  DriverPackages_DB(driverPackages_respones_recycler.getOrderNumber(),
//                                        driverPackages_respones_recycler.getCUSTOMER_PHONE());
                      //  database.userDao().insertDriverOrders(driverPackages_respones_Header_recycler.getRecords());

//                        driverPackages_Header_dbList.addAll(driverPackages_respones_Header_recycler.getRecords());
//                        driverOrdersAdapter.notifyDataSetChanged();
                        for (int i=0;i<responeEndOfDay.getEndOfDayModule().size();i++){
                            if (responeEndOfDay.getEndOfDayModule().get(i).getSTATUS().equalsIgnoreCase("Rejected under inspection")){
                                FailedList.add(responeEndOfDay.getEndOfDayModule().get(i));
                                FailedValue +=Double.valueOf(responeEndOfDay.getEndOfDayModule().get(i).getITEM_PRICE());
                            } else if (responeEndOfDay.getEndOfDayModule().get(i).getSTATUS().equalsIgnoreCase("Has-Been-Delivered")) {
                                SuccessList.add(responeEndOfDay.getEndOfDayModule().get(i));
                                SuccessValue +=Double.valueOf(responeEndOfDay.getEndOfDayModule().get(i).getITEM_PRICE());
                            }
                        }

                        binding.txtSuccessValue.setText(String.valueOf(SuccessValue+FailedValue));
                        binding.txtRequiredValue.setText(String.valueOf(SuccessValue));
                        binding.txtFailedValue.setText(String.valueOf(FailedValue));


                    }
                });
    }
}