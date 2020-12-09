package com.example.packingapp.UI;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityAddAndEditBinding;

public class AddAndEditActivity extends AppCompatActivity {



    ActivityAddAndEditBinding binding;
    String test="vehicle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddAndEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        binding.chooser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio0:
                        if (test.equals("driver") ){
                        navController.navigate(R.id.action_driverManage_to_vehicleManage);
                    }
                        else if (test.equals("way"))
                        navController.navigate(R.id.action_wayManage_to_vehicleManage);
                        test="vehicle";
                        break;
                    case R.id.radio1:
                        if (test.equals("vehicle"))
                           navController.navigate(R.id.action_vehicleManage_to_driverManage);
                        else if (test.equals("way"))
                           navController.navigate(R.id.action_wayManage_to_driverManage);
                        test="driver";
                        break;
                    case R.id.radio2:
                        if (test.equals("driver"))
                            navController.navigate(R.id.action_driverManage_to_wayManage);
                        else if (test.equals("vehicle"))
                            navController.navigate(R.id.action_vehicleManage_to_wayManage);
                        test="way";
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}