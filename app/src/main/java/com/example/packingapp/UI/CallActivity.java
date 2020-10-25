package com.example.packingapp.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.packingapp.R;

public class CallActivity extends AppCompatActivity {

    private static final int REQUEST_PHONE_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        TextView textView = findViewById(R.id.distance);
        final LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                textView.setText(String.valueOf(getDistance(location.getLatitude(), location.getLongitude(),
                        30.0281722, 31.01875)));
            }
        };

        PermissionForCall();

        Button call = findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:01065551910"));
                startActivity(intent);

//                Intent intent = getPackageManager().getLaunchIntentForPackage("com.zoiper.android.noinapp.app");
//                if (intent != null) {
//                    // We found the activity now start the activity
////                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    intent.setData(Uri.parse("tel:" + "01286751382"));
////                    intent.putExtra("KeyName","01286751382");
//
//                    intent.setAction(Intent.ACTION_SEND);
//                    intent.putExtra(Intent.EXTRA_TEXT, "01286751382");
//                    intent.setType("text/plain");
//                    Intent shareIntent = Intent.createChooser(intent, null);
//
//                    startActivity(shareIntent);
//                } else {
//                    // Bring user to the market or let them choose an app?
//                    intent = new Intent(Intent.ACTION_VIEW);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.setData(Uri.parse("market://details?id=" + "com.zoiper.android.noinapp.app"));
//                    startActivity(intent);
//                }

            }
        });
    }

    private float getDistance(double lat1, double lon1, double lat2, double lon2) {
        float[] distance = new float[2];
        Location.distanceBetween(lat1, lon1, lat2, lon2, distance);
        return distance[0] + distance[1];
    }

    public void PermissionForCall(){
        if (ActivityCompat.checkSelfPermission(CallActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CallActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }
    }
}