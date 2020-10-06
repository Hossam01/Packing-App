package com.example.packingapp.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.packingapp.workmanagerapi.ReadDataWorkManageDriver;
import com.example.packingapp.workmanagerapi.ReadDataWorkManageVehicle;
import com.example.packingapp.workmanagerapi.UpdateWorkerManagerApiDriver;
import com.example.packingapp.workmanagerapi.WorkerManagerApiDriver;

public class DriverViewModel extends ViewModel {

    public void fetchdata(String nameArabic, String nameEnglish, String status, String company, String Phone, String address, String Vechile_ID) {


        WorkManager mWorkManager = WorkManager.getInstance();
        Constraints.Builder builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED);
        Data.Builder data = new Data.Builder();
        data.putString("NameArabic", nameArabic);
        data.putString("NameEnglish", nameEnglish);
        data.putString("Status", status);
        data.putString("Company", company);
        data.putString("Phone", Phone);
        data.putString("Address", address);
        data.putString("Vechile_ID", Vechile_ID);


        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(WorkerManagerApiDriver.class)
                .addTag("Sync")
                .setInputData(data.build())
                .setConstraints(builder.build())
                .build();
        mWorkManager.enqueue(workRequest);

    }

    public void updateData(String id, String nameArabic, String nameEnglish, String status, String company, String Phone, String address, String Vechile_ID) {


        WorkManager mWorkManager = WorkManager.getInstance();
        Constraints.Builder builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED);
        Data.Builder data = new Data.Builder();
        data.putString("Driver_ID", id);
        data.putString("NameArabic", nameArabic);
        data.putString("NameEnglish", nameEnglish);
        data.putString("Status", status);
        data.putString("Company", company);
        data.putString("Phone", Phone);
        data.putString("Address", address);
        data.putString("Vechile_ID", Vechile_ID);

        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(UpdateWorkerManagerApiDriver.class)
                .addTag("Sync")
                .setInputData(data.build())
                .setConstraints(builder.build())
                .build();
        mWorkManager.enqueue(workRequest);

    }
    public void fetchDataVehicle(){
        WorkManager mWorkManager = WorkManager.getInstance();
        Constraints.Builder builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED);
        Data.Builder data = new Data.Builder();
        data.putString("username", "username");

        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(ReadDataWorkManageVehicle.class)
                .addTag("Sync")
                .setInputData(data.build())
                .setConstraints(builder.build())
                .build();
        mWorkManager.enqueue(workRequest);
    }

    public void fetchDataDriver(){
        WorkManager mWorkManager = WorkManager.getInstance();
        Constraints.Builder builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED);
        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(ReadDataWorkManageDriver.class)
                .addTag("Sync")
                .setConstraints(builder.build())
                .build();
        mWorkManager.enqueue(workRequest);
    }


}
