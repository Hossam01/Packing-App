package com.example.packingapp.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.packingapp.workmanagerapi.WorkerManagerApiLogin;
import com.example.packingapp.workmanagerapi.WorkerManagerApiVehicle;

public class VehicleViewModel extends ViewModel {

    public void fetchdata(String nameArabic, String nameEnglish, String lienceNumber, String weight) {


        WorkManager mWorkManager = WorkManager.getInstance();
        Constraints.Builder builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED);
        Data.Builder data = new Data.Builder();
        data.putString("NameArabic", nameArabic);
        data.putString("NameEnglish", nameEnglish);
        data.putString("LienceNumber", lienceNumber);
        data.putString("Weight", weight);


        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(WorkerManagerApiVehicle.class)
                .addTag("Sync")
                .setInputData(data.build())
                .setConstraints(builder.build())
                .build();
        mWorkManager.enqueue(workRequest);

    }
}
