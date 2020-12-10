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
import android.widget.TextView;
import android.widget.Toast;

import com.example.packingapp.Adapter.DriverOrderpackagesAdapter;
import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.Helper.ItemclickforRecycler;
import com.example.packingapp.R;
import com.example.packingapp.UI.Fragments.ConfirmPasscodeFragment;
import com.example.packingapp.databinding.ActivityOrderDetailsForDriverBinding;
import com.example.packingapp.model.DriverModules.DriverPackages_Details_DB;
import com.example.packingapp.model.DriverModules.DriverPackages_Header_DB;
import com.example.packingapp.model.DriverModules.DriverPackages_Respones_Details_recycler;
import com.example.packingapp.model.RecievePacked.RecievePackedModule;
import com.example.packingapp.model.ResponseSms;
import com.example.packingapp.model.ResponseUpdateStatus;
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
    List<DriverPackages_Details_DB> driverPackages_details_dbList_Reject;

    String Orderclicked="";
    AppDatabase database;
    DriverOrderpackagesAdapter driverOrderpackagesAdapter;
    DriverOrderpackagesAdapter driverOrderpackagesAdapter_Reject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderDetailsForDriverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        orderDetailsForDriverViewModel= ViewModelProviders.of(this).get(OrderDetailsForDriverViewModel.class);
        PermissionForCall();
        CreateORUpdateRecycleView();
        CreateORUpdateRecycleView_Reject();
        database= AppDatabase.getDatabaseInstance(this);

        if (getIntent().getExtras() != null){
            Orderclicked=getIntent().getExtras().getString("Orderclicked");
        }
        Log.e(TAG, "onItemClicked: "+ Orderclicked);

        PhoneAndSmsActions();

        binding.btnRescheduleDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateStatus_ON_83();
                UpdateStatus();
            }
        });
        binding.btnSendPasscodeToConfirmDeleivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int randomNumber = random.nextInt(1280 - 65) + 65;
                Log.e(TAG, "onClick:randomNumber  "+ String.valueOf(randomNumber) );
                //ToDo Don't SendPasscode For Test Get asscode From log
                SendSMS(CustomerPhone, String.valueOf(randomNumber));

                database.userDao().UpdatePasscode(Orderclicked,String.valueOf(randomNumber));

                ConfirmPasscodeFragment detialsfragment=new ConfirmPasscodeFragment();
                Bundle bundle=new Bundle();
                bundle.putString("Orderclicked",Orderclicked);
//                bundle.putString("UserName",UserName);
//                bundle.putString("Branch",Branch);
                // bundle.putSerializable("LastOrderIdArray",LastOrderArry);

                detialsfragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content,detialsfragment);

                // databaseHelper.update_PDNEWQTY(Po_Item_List.get(position).
                // getBarcode1(),String.valueOf(Double.valueOf(Po_Item_List.get(position).getQuantity1())-1));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

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
                        Log.e(TAG, "onChangedgetdata: "+driverPackages_respones_details_recycler
                                .getRecords().get(0).getTRACKING_NO() );
                        Log.e(TAG, "onChangedgetdatasi: "+driverPackages_respones_details_recycler
                                .getRecords().size() );

                        driverPackages_details_dbList.addAll(driverPackages_respones_details_recycler.getRecords());
                        driverOrderpackagesAdapter.notifyDataSetChanged();
                        totalPriceFUN(driverPackages_respones_details_recycler.getRecords());
                    }
                });
    }

    private void PhoneAndSmsActions() {
        DriverPackages_Header_DB driverPackages_header_db=
                database.userDao().getDriverorder();
        //TODO adding my number to send sms and amke call
//        CustomerPhone=driverPackages_header_db.getCustomer_phone();
//        CustomerPhone=CustomerPhone.replace("+2","");
        Log.e(TAG, "onCreate:vvv "+ CustomerPhone);

        binding.imgBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" +CustomerPhone ));
                startActivity(intent);
            }
        });

        binding.imgBtnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sms();
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
        View promptsView = li.inflate(R.layout.prompts_send_sms, null);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();



        final EditText edit_smsInput = (EditText) promptsView
                .findViewById(R.id.edit_smsInput);

        final Button btn_send_sms = (Button) promptsView
                .findViewById(R.id.btn_send_sms);

        btn_send_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // promptsView.

                if (!edit_smsInput.getText().toString().isEmpty()) {
                    SendSMS(CustomerPhone, edit_smsInput.getText().toString());
                    alertDialog.dismiss();

                }else{
                    if (edit_smsInput.getText().toString().isEmpty()){
                        edit_smsInput.setError(getResources().getString(R.string.enter_sms_body));
                    }
                }
            }

        });
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
                //driverOrderpackagesAdapter.ReturnListOfPackages();
                RejectDialog(driverPackages_details_dbList.get(position).getTRACKING_NO(),position);
            }
        });
    }


    public void CreateORUpdateRecycleView_Reject(){

        driverPackages_details_dbList_Reject=new ArrayList<>();
        //   Po_Item_For_Recycly=database.userDao().getAllPckages();

        driverOrderpackagesAdapter_Reject =new DriverOrderpackagesAdapter(driverPackages_details_dbList_Reject);
        binding.rvCustomerRejectItems.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        binding.rvCustomerRejectItems.setLayoutManager(mLayoutManager);
        binding.rvCustomerRejectItems.setAdapter(driverOrderpackagesAdapter_Reject);


    }

    public void RejectDialog(String RejectTrackingNumber , int position){

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts_reason_of_reject, null);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        final TextView txt_tracking_number_reject = (TextView) promptsView
                .findViewById(R.id.txt_tracking_number_reject);
        txt_tracking_number_reject.setText(RejectTrackingNumber);

        final EditText edit_rejectinput = (EditText) promptsView
                .findViewById(R.id.edit_rejectinput);

        final Button btn_reject = (Button) promptsView
                .findViewById(R.id.btn_reject);

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // promptsView.

                if (!edit_rejectinput.getText().toString().isEmpty() ) {
                    //SendSMS(CustomerPhone, edit_rejectinput.getText().toString());
                    database.userDao().UpdatestatusAndReason_ForTrackingnumber(RejectTrackingNumber,
                            "Rejected under inspection",edit_rejectinput.getText().toString());
                    driverPackages_details_dbList_Reject.add(driverPackages_details_dbList.get(position));
                    Log.e(TAG, "onClick: "+ driverPackages_details_dbList.size());
                    driverPackages_details_dbList.remove(position);
                    Log.e(TAG, "onClickre: "+ driverPackages_details_dbList_Reject.size());
                    driverOrderpackagesAdapter.notifyDataSetChanged();
                    driverOrderpackagesAdapter_Reject.notifyDataSetChanged();
                    totalPriceFUN(driverPackages_details_dbList);

                    alertDialog.dismiss();

                }else{
                    if (edit_rejectinput.getText().toString().isEmpty()){
                        edit_rejectinput.setError(getResources().getString(R.string.enter_sms_body));
                    }
                }
            }
        });
        // show it
        alertDialog.show();
    }

    public void UpdateStatus_ON_83(){
//        if (database.userDao().getAllItemsWithoutTrackingnumber().size() == 0){
        List<RecievePackedModule> orderDataModuleDBHeaderkist = database.userDao().getorderNORecievePackedModule();
        if (orderDataModuleDBHeaderkist.size() > 0) {
            for (int i=0;i<orderDataModuleDBHeaderkist.size();i++) {
                orderDetailsForDriverViewModel.UpdateStatus_ON_83(
                        orderDataModuleDBHeaderkist.get(i).getORDER_NO(),
                        "Reschedule"
                );
            }
        }else {
            Toast.makeText(context, getResources().getString(R.string.not_enter), Toast.LENGTH_SHORT).show();
        }

        orderDetailsForDriverViewModel.mutableLiveData_UpdateStatus_ON_83.observe(
                OrderDetails_forDriverActivity.this, new Observer<ResponseUpdateStatus>() {
                    @Override
                    public void onChanged(ResponseUpdateStatus message) {
                        Toast.makeText(OrderDetails_forDriverActivity.this, ""+message.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onChanged:update "+message.getMessage() );
                    }
                });
//        }else {
//            Toast.makeText(GetOrderDatactivity.this, "توجد عناصر لم يتم تعبئتها", Toast.LENGTH_SHORT).show();
//        }
    }

    public void UpdateStatus(){
//        if (database.userDao().getAllItemsWithoutTrackingnumber().size() == 0){

        List<RecievePackedModule> orderDataModuleDBHeaderkist = database.userDao().getorderNORecievePackedModule();


            orderDetailsForDriverViewModel.UpdateStatus(
                    orderDataModuleDBHeaderkist.get(0).getORDER_NO(),
                    "Reschedule"
            );

        orderDetailsForDriverViewModel.mutableLiveData_UpdateStatus.observe(OrderDetails_forDriverActivity.this, new Observer<ResponseUpdateStatus>() {
            @Override
            public void onChanged(ResponseUpdateStatus message) {
                Toast.makeText(OrderDetails_forDriverActivity.this, ""+message.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onChanged:UpdateStatusroub "+message.getMessage() );
            }
        });
        orderDetailsForDriverViewModel.mutableLiveDataError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChanged:roub "+s );
                Toast.makeText(OrderDetails_forDriverActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });


    }
    void totalPriceFUN(    List<DriverPackages_Details_DB> driverPackages_details_dbList_price){
        Double TotalPrice=0.0;
        for(int i=0 ; i<driverPackages_details_dbList.size();i++){
            TotalPrice+=Double.valueOf(driverPackages_details_dbList_price.get(i).getPACKAGE_PRICE());
            Log.e(TAG, "totalPriceFUN: "+ TotalPrice);
        }

        binding.txtTotalPrice.setText(String.valueOf(TotalPrice));
    }
}