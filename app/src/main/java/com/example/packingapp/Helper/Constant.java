package com.example.packingapp.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constant {
    private static final String TAG = "Constant";
    public static boolean isOnline(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();

    }

    public static boolean RegulerExpre_forTrackingNumbeer(String Trackingnumber) {
        Pattern my_pattern = Pattern.compile("[a-z ]");
        Matcher my_match = my_pattern.matcher(Trackingnumber);
        Log.e(TAG, "onClick: "+my_match.find() );
        if (!my_match.find()) {
            return true;
        }else {
            return false;
        }


    }

}
