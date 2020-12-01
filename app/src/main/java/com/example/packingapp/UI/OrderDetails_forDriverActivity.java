package com.example.packingapp.UI;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.packingapp.Adapter.DriverOrderpackagesAdapter;
import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.Helper.ItemclickforRecycler;
import com.example.packingapp.R;
import com.example.packingapp.UI.Fragments.ConfirmPasscodeFragment;
import com.example.packingapp.databinding.ActivityOrderDetailsForDriverBinding;
import com.example.packingapp.model.DriverModules.DriverPackages_Details_DB;
import com.example.packingapp.model.DriverModules.DriverPackages_Respones_Details_recycler;
import com.example.packingapp.model.ResponseSms;
import com.example.packingapp.viewmodel.OrderDetailsForDriverViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderDetails_forDriverActivity extends AppCompatActivity {
    private static final String TAG = "OrderDetails_forDriverA";
ActivityOrderDetailsForDriverBinding binding;
private static final int REQUEST_PHONE_CALL = 1;
    OrderDetailsForDriverViewModel orderDetailsForDriverViewModel;
    String CustomerPhone="01065551910";
    Context context=OrderDetails_forDriverActivity.this;
    List<DriverPackages_Details_DB> driverPackages_details_dbList;
    String Orderclicked="";
    AppDatabase database;
    DriverOrderpackagesAdapter driverOrderpackagesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderDetailsForDriverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        orderDetailsForDriverViewModel= ViewModelProviders.of(this).get(OrderDetailsForDriverViewModel.class);
        PermissionForCall();
        CreateORUpdateRecycleView();
        database= AppDatabase.getDatabaseInstance(this);

        if (getIntent().getExtras() != null){
            Orderclicked=getIntent().getExtras().getString("Orderclicked");
            Log.e(TAG, "onItemClicked: "+ Orderclicked);

        }
        Log.e(TAG, "onItemClicked: "+ Orderclicked);


        binding.imgBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" +CustomerPhone.replace("+2","") ));
                    startActivity(intent);
                }
            });

        binding.imgBtnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sms();
            }
        });

        binding.btnSendPasscodeToConfirmDeleivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int randomNumber = random.nextInt(1280 - 65) + 65;
                Log.e(TAG, "onClick:randomNumber  "+ String.valueOf(randomNumber) );
                SendSMS(CustomerPhone, String.valueOf(randomNumber));
            }
        });

        orderDetailsForDriverViewModel.ReadDriverRunsheetOrdersData(Orderclicked);
        Log.e(TAG, "onCreate: "+ Orderclicked);
        orderDetailsForDriverViewModel.GetDriverOrdersReadyDetailsDataLiveData().observe(OrderDetails_forDriverActivity.this,
                new Observer<DriverPackages_Respones_Details_recycler>() {
                    @Override
                    public void onChanged(DriverPackages_Respones_Details_recycler driverPackages_respones_details_recycler) {

//                        DriverPackages_DB driverPackages_respones_recycler1=
//                                new  DriverPackages_DB(driverPackages_respones_recycler.getOrderNumber(),
//                                        driverPackages_respones_recycler.getCUSTOMER_PHONE());
                        database.userDao().insertDriverPackages(driverPackages_respones_details_recycler.getRecords());

                        Log.e(TAG, "onChanged: "+driverPackages_respones_details_recycler
                                .getRecords().get(0).getTRACKING_NO() );
                        driverPackages_details_dbList.addAll(driverPackages_respones_details_recycler.getRecords());
                        driverOrderpackagesAdapter.notifyDataSetChanged();
                    }
                });


    }

    public void PermissionForCall(){
        if (ActivityCompat.checkSelfPermission(OrderDetails_forDriverActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(OrderDetails_forDriverActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }
    }

    public void sms(){

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText edit_smsInput = (EditText) promptsView
                .findViewById(R.id.edit_smsInput);

        final Button btn_send_sms = (Button) promptsView
                .findViewById(R.id.btn_send_sms);

        btn_send_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_smsInput.getText().toString().isEmpty()) {
                    SendSMS(CustomerPhone, edit_smsInput.getText().toString());
                }else{
                    if (edit_smsInput.getText().toString().isEmpty()){
                        edit_smsInput.setError("Enter sms body");
                    }
                }
            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void SendSMS(String CustomerPhone ,String SMSBody) {

        orderDetailsForDriverViewModel.SendSms(CustomerPhone , SMSBody);
        orderDetailsForDriverViewModel.getSmsLiveData().observe(OrderDetails_forDriverActivity.this, new Observer<ResponseSms>() {
            @Override
            public void onChanged(ResponseSms responseSms) {
                Toast.makeText(OrderDetails_forDriverActivity.this,
                        responseSms.getSMSStatus().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void CreateORUpdateRecycleView(){

        driverPackages_details_dbList=new ArrayList<>();
        //   Po_Item_For_Recycly=database.userDao().getAllPckages();

        driverOrderpackagesAdapter =new DriverOrderpackagesAdapter(driverPackages_details_dbList);
        binding.rvCustomerItems.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        binding.rvCustomerItems.setLayoutManager(mLayoutManager);
        binding.rvCustomerItems.setAdapter(driverOrderpackagesAdapter);


        ItemclickforRecycler.addTo(binding.rvCustomerItems).setOnItemClickListener(new ItemclickforRecycler.OnItemClickListener() {

            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                ConfirmPasscodeFragment detialsfragment=new ConfirmPasscodeFragment();
                Bundle bundle=new Bundle();
//                bundle.putString("Barcode",Po_Item_List.get(position).getBarcode1());
//                bundle.putString("UserName",UserName);
//                bundle.putString("Branch",Branch);
                // bundle.putSerializable("LastOrderIdArray",LastOrderArry);

                detialsfragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content,detialsfragment);

                // databaseHelper.update_PDNEWQTY(Po_Item_List.get(position).getBarcode1(),String.valueOf(Double.valueOf(Po_Item_List.get(position).getQuantity1())-1));

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }


    public void CreateORUpdateRecycleView_Reject(){

        driverPackages_details_dbList=new ArrayList<>();
        //   Po_Item_For_Recycly=database.userDao().getAllPckages();

        driverOrderpackagesAdapter =new DriverOrderpackagesAdapter(driverPackages_details_dbList);
        binding.rvCustomerRejectItems.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        binding.rvCustomerRejectItems.setLayoutManager(mLayoutManager);
        binding.rvCustomerRejectItems.setAdapter(driverOrderpackagesAdapter);


    }
}