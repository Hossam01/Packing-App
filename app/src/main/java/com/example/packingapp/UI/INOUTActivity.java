package com.example.packingapp.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.packingapp.R;
import com.example.packingapp.Retrofit.APIRetrofit;
import com.example.packingapp.Retrofit.ApiClient;
import com.example.packingapp.model.Message;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class INOUTActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_n_o_u_t);


        HashMap<String, String> map = new HashMap<>();
        map.put("driverId", "1");
        map.put("status", "1");

        Button in = findViewById(R.id.checkin);
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(map);
            }
        });

        Button out = findViewById(R.id.checkout);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(map);
            }
        });


    }

    public void sendData(HashMap<String, String> map) {
        ApiClient.build().createInOut(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(responseSms -> {
                            Toast.makeText(getApplicationContext(), responseSms.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        ,throwable -> {
                            Log.d("Error",throwable.getMessage());

                        });


    }
}