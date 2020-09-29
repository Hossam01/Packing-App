package com.example.packingapp.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.packingapp.workmanagerapi.ReadDataWorkManageDriver;
import com.example.packingapp.workmanagerapi.ReadDataWorkManageVehicle;
import com.example.packingapp.workmanagerapi.WorkerManagerApiDriver;
import com.example.packingapp.workmanagerapi.WorkerManagerApiWay;

public class WayViewModel extends ViewModel {

    public void fetchdata(String nameArabic, String nameEnglish, String status, String estimationTime, String stations, String driverId, String Vechile_ID) {


        WorkManager mWorkManager = WorkManager.getInstance();
        Constraints.Builder builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED);
        Data.Builder data = new Data.Builder();
        data.putString("NameArabic", nameArabic);
        data.putString("NameEnglish", nameEnglish);
        data.putString("Status", status);
        data.putString("Estimation_Time", estimationTime);
        data.putString("Stations", stations);
        data.putString("Driver_ID", driverId);
        data.putString("Vechile_ID", Vechile_ID);


        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(WorkerManagerApiWay.class)
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
        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(ReadDataWorkManageVehicle.class)
                .addTag("Sync")
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
