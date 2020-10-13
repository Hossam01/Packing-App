package com.example.packingapp.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.packingapp.workmanagerapi.SmsWorkManager;

public class SendSmsViewModel extends ViewModel {

    public void fetchdata(String number, String message) {


        WorkManager mWorkManager = WorkManager.getInstance();
        Constraints.Builder builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED);
        Data.Builder data = new Data.Builder();
        data.putString("number", number);
        data.putString("message", message);


        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(SmsWorkManager.class)
                .addTag("Sync")
                .setInputData(data.build())
                .setConstraints(builder.build())
                .build();
        mWorkManager.enqueue(workRequest);


    }
}
