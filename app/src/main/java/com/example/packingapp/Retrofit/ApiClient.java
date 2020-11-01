package com.example.packingapp.Retrofit;

import android.net.TrafficStats;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "http://192.168.1.83/";

    public static APIRetrofit build() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        TrafficStats.setThreadStatsTag(0x1000);
        return retrofit.create(APIRetrofit.class);
    }


    public static final String BASE_URL_Roubsta = "http://www.json-generator.com/";

    public static RoubstaAPIRetrofit buildRo() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_Roubsta)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        TrafficStats.setThreadStatsTag(0x1000);
        return retrofit.create(RoubstaAPIRetrofit.class);
    }
}
