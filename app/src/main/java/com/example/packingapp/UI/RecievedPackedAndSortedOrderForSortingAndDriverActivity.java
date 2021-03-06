package com.example.packingapp.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.packingapp.Adapter.RecievedPackagesAdapter;
import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.Helper.Constant;
import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityRecievePackedSortedOrderForSortingDriverBinding;
import com.example.packingapp.model.RecievePacked.RecievePackedModule;
import com.example.packingapp.model.RecievedPackageModule;
import com.example.packingapp.model.RecordsItem;
import com.example.packingapp.model.ResponseUpdateStatus;
import com.example.packingapp.viewmodel.RecievePackedOrderViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecievedPackedAndSortedOrderForSortingAndDriverActivity extends AppCompatActivity {
ActivityRecievePackedSortedOrderForSortingDriverBinding binding;
    private static final String TAG = "RecievePackedOrderForSo";
    RecievePackedOrderViewModel recievePackedOrderViewModel;
    List<RecievedPackageModule> Po_Item_For_Recycly;
    AppDatabase database;
    private RecievedPackagesAdapter recievedPackagesAdapter;
    final Context context = this;
    String Zone ,trackingnumberIn , RecievePackedOrConfirmForDriver="";
String DriverID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRecievePackedSortedOrderForSortingDriverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=AppDatabase.getDatabaseInstance(this);
        RecordsItem recordsItem = database.userDao().getUserData_MU();
        Log.e(TAG, "onCreate: "+recordsItem.getUser_id() );
        DriverID = recordsItem.getUser_id();

        if (getIntent().getExtras() !=null){
            RecievePackedOrConfirmForDriver = getIntent().getExtras().getString("RecievePackedOrConfirmForDriver");
        }
        recievePackedOrderViewModel= ViewModelProviders.of(this).get(RecievePackedOrderViewModel.class);
        ObserveFunct();
        binding.btnLoadingNewPurchaseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadNewPurchaseOrder();
            }
        });

        CreateORUpdateRecycleView();

        binding.editTrackingnumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_GO
                        || actionId == EditorInfo.IME_ACTION_NEXT
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent == null
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER
                        || keyEvent.getAction() == KeyEvent.KEYCODE_NUMPAD_ENTER
                        || keyEvent.getAction() == KeyEvent.KEYCODE_DPAD_CENTER){
                    LoadNewPurchaseOrder();
                }
                return false;
            }
        });

        binding.btnEditPackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToEditRecievedPackages=new Intent(RecievedPackedAndSortedOrderForSortingAndDriverActivity.this,
                        EditPackagesForRecievingActivity.class);
                startActivity(GoToEditRecievedPackages);
            }
        });

        binding.btnUpdateOrderStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO CHECK IF TRACKING NUMBER MORE THAN require no of packages
                List<RecievePackedModule>  recievePackedORDER_NO_Distinctlist=  database.userDao().getRecievePacked_ORDER_NO_Distinct();
                List<RecievePackedModule>  NOTrecievedPackedORDERlist=  new ArrayList<>();
                if (recievePackedORDER_NO_Distinctlist.size()>0) {
                    for (int i = 0; i<recievePackedORDER_NO_Distinctlist.size();i++){
                        List<String>  recievePacked_Tracking_Number_countlist=
                                database.userDao().getRecievePacked_Tracking_Number_count(recievePackedORDER_NO_Distinctlist.get(i).getORDER_NO());
                        if (!recievePacked_Tracking_Number_countlist.get(0).
                                equalsIgnoreCase(recievePackedORDER_NO_Distinctlist.get(i).getNO_OF_PACKAGES().toString())){
                            NOTrecievedPackedORDERlist.add(recievePackedORDER_NO_Distinctlist.get(i));
                        }
                    }

                    if (NOTrecievedPackedORDERlist.size()==0){
                        //TODO UPDATE STATUS
                       // Toast.makeText(RecievePackedOrderForSortingActivity.this, String.format("%s",getString(R.string.message_equalfornoofpaclkageandcountoftrackingnumbers)), Toast.LENGTH_SHORT).show();
                        UpdateStatus_ON_83();
                        //TODO TODO UPDATE STATUS on magento
                        UpdateStatus();
                    }else {
                        Toast.makeText(context, String.format("%s",
                                getString(R.string.message_not_equalfornoofpaclkageandcountoftrackingnumbers)), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RecievedPackedAndSortedOrderForSortingAndDriverActivity.this, String.format("%s",
                            getString(R.string.there_is_no_trackednumber_scanned)), Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnDeleteLastTrackingnumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<RecievePackedModule>  recievePackedORDER_NO_Distinctlist=  database.userDao().getRecievePacked_ORDER_NO_Distinct();
                if (recievePackedORDER_NO_Distinctlist.size()>0) {
                    new AlertDialog.Builder(RecievedPackedAndSortedOrderForSortingAndDriverActivity.this)
                            .setTitle(getString(R.string.delete_dialoge))
                            .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    database.userDao().deleteRecievePackedModule();
                                    binding.editTrackingnumber.setError(null);
                                    binding.editTrackingnumber.setText("");
                                }
                            })
                            .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.cancel();
                                }
                            }).show();
                }else {
                    Toast.makeText(RecievedPackedAndSortedOrderForSortingAndDriverActivity.this, "لايوجد بيانات للحذف", Toast.LENGTH_SHORT).show();
                }
            }
        });
      /*  binding.btnAssignOrdersToZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText zoneInput = (EditText) promptsView
                        .findViewById(R.id.edit_zone);
                final EditText trackingnumberInput = (EditText) promptsView
                        .findViewById(R.id.edit_trackingnumber);

                Zone = zoneInput.getText().toString();
                trackingnumberIn = trackingnumberInput.getText().toString();


                final Button btn_assign_zone = (Button) promptsView
                        .findViewById(R.id.btn_assign_to_zone);

                /*final Button btn_Dismiss = (Button) promptsView
                        .findViewById(R.id.btn_dismiss);
                btn_Dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();

                    }
                });

                btn_assign_zone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (trackingnumberInput.getText().toString().isEmpty()
                                && zoneInput.getText().toString().isEmpty()) {
                            AssignToZone(trackingnumberIn, Zone);
                        }else{
                            if (trackingnumberInput.getText().toString().isEmpty()){
                                trackingnumberInput.setError("Enter tracking number");
                            }else if (zoneInput.getText().toString().isEmpty())
                                zoneInput.setError("enter zone");
                        }
                    }
                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });*/
    }

    private void ObserveFunct() {
        recievePackedOrderViewModel.getOrderDataLiveData().observe(RecievedPackedAndSortedOrderForSortingAndDriverActivity.this, new Observer<RecievePackedModule>() {
            @Override
            public void onChanged(RecievePackedModule responseGetOrderData) {
                Log.e(TAG, "onChanged: "+ responseGetOrderData.getORDER_NO());
                Log.e(TAG, "onChanged: "+ responseGetOrderData.getNO_OF_PACKAGES());
                Log.e(TAG, "onChanged:stat "+ responseGetOrderData.getSTATUS());
                Log.e(TAG, "onChanged:packeOr "+ RecievePackedOrConfirmForDriver);
                if (RecievePackedOrConfirmForDriver.equalsIgnoreCase("RecievePacked")) {
                    if (responseGetOrderData.getSTATUS().equalsIgnoreCase("packed")) {
                        AfterGetOrderData(responseGetOrderData , binding.editTrackingnumber.getText().toString());
                    }else {
                        Toast.makeText(RecievedPackedAndSortedOrderForSortingAndDriverActivity.this, "This Order in "+responseGetOrderData.getSTATUS()+" State", Toast.LENGTH_SHORT).show();
                        binding.editTrackingnumber.setError(null);
                        binding.editTrackingnumber.setText("");
                    }
                }else if (RecievePackedOrConfirmForDriver.equalsIgnoreCase("ConfirmForDriver")) {
                    if (responseGetOrderData.getSTATUS().equalsIgnoreCase("sorted")) {
                        if (responseGetOrderData.getDRIVER_ID() != null) {
                            if (responseGetOrderData.getDRIVER_ID().equalsIgnoreCase(DriverID)) {
                                AfterGetOrderData(responseGetOrderData, binding.editTrackingnumber.getText().toString());
                            }else {
                                Toast.makeText(RecievedPackedAndSortedOrderForSortingAndDriverActivity.this, "لم يتم تسجيل هذا الامر لك", Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(RecievedPackedAndSortedOrderForSortingAndDriverActivity.this, "لم يتم تسجيل هذا الامر لك", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(RecievedPackedAndSortedOrderForSortingAndDriverActivity.this, "This Order in "+responseGetOrderData.getSTATUS()+" State", Toast.LENGTH_SHORT).show();
                        binding.editTrackingnumber.setError(null);
                        binding.editTrackingnumber.setText("");
                    }
                }

            }

        });

        recievePackedOrderViewModel.mutableLiveDataError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChanged: "+s );
                Toast.makeText(RecievedPackedAndSortedOrderForSortingAndDriverActivity.this, "load order data error "+s, Toast.LENGTH_LONG).show();

                if (s.equals("HTTP 503 Service Unavailable")) {
                    Toast.makeText(context, getResources().getString(R.string.invalidnumber), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void LoadNewPurchaseOrder() {
        String OrderNumber;
        if (!binding.editTrackingnumber.getText().toString().isEmpty() &&
                binding.editTrackingnumber.getText().toString().contains("-")) {
            //  if (!my_match.find()) {
            if (Constant.RegulerExpre_forTrackingNumbeer(binding.editTrackingnumber.getText().toString())) {
                // Toast.makeText(context, "Special character not found in the string", Toast.LENGTH_SHORT).show();
                OrderNumber =
                        binding.editTrackingnumber.getText().toString().substring(0,
                                binding.editTrackingnumber.getText().toString().indexOf("-"));
                //TODO CHECK IF TRACKING NUMBER MORE THAN require no of packages
                String NOtrackingnumber = binding.editTrackingnumber.getText().toString().substring(
                        binding.editTrackingnumber.getText().toString().indexOf("-") + 1);
                Log.e(TAG, "onClick: " + NOtrackingnumber);

                List<RecievePackedModule> recievePackedlist = database.userDao().getRecievePacked_ORDER_NO(OrderNumber);
                Log.e(TAG, "onClick: OrdrecievePackedlist " + recievePackedlist.size());

                if (recievePackedlist.size() == 0) {
                    binding.editTrackingnumber.setError(null);

                    GETOrderData(OrderNumber);
                    Log.e(TAG, "onClick: Ord " + OrderNumber);

//                    binding.editTrackingnumber.setError(null);
//                    binding.editTrackingnumber.setText("");

                    //    Toast.makeText(RecievePackedOrderForSortingActivity.this, "تم", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "LoadNewPurchaseOrder: "+binding.editTrackingnumber.getText().toString() );
                    Log.e(TAG, "LoadNewPurchaseOrder: "+recievePackedlist.get(0).getNO_OF_PACKAGES() );
                    if (database.userDao().getRecievePacked_Tracking_Number(binding.editTrackingnumber.getText().toString())
                            .size() == 0 && Integer.valueOf(recievePackedlist.get(0).getNO_OF_PACKAGES()) >=
                            Integer.valueOf(NOtrackingnumber)) {
                        database.userDao().insertRecievePacked(new RecievePackedModule(
                                recievePackedlist.get(0).getORDER_NO(), recievePackedlist.get(0).getNO_OF_PACKAGES(),
                                binding.editTrackingnumber.getText().toString()));
                        CreateORUpdateRecycleView();
                        Toast.makeText(RecievedPackedAndSortedOrderForSortingAndDriverActivity.this, "تم", Toast.LENGTH_SHORT).show();

                        //    GETOrderData(binding.editTrackingnumber.getText().toString());
                  //      Log.e(TAG, "onClick: Trac " + binding.editTrackingnumber.getText().toString());
                        binding.editTrackingnumber.setError(null);
                        binding.editTrackingnumber.setText("");
                    } else {
                        if (database.userDao().getRecievePacked_Tracking_Number(binding.editTrackingnumber.getText().toString())
                                .size() > 0) {
                            binding.editTrackingnumber.setError(getResources().getString(R.string.enterbefor));
                            binding.editTrackingnumber.setText("");
                            binding.editTrackingnumber.requestFocus();
                        }else {
                            binding.editTrackingnumber.setError(getResources().getString(R.string.invalidnumber));
                            binding.editTrackingnumber.setText("");
                            binding.editTrackingnumber.requestFocus();
                        }
                    }
                }
            }else {
                Toast.makeText(context, "Special character found in the string", Toast.LENGTH_SHORT).show();
            }
        }else {
            binding.editTrackingnumber.setError(getString(R.string.enter_tracking_number));
            binding.editTrackingnumber.setText("");
            binding.editTrackingnumber.requestFocus();
        }
    }

    public void CreateORUpdateRecycleView(){
        Po_Item_For_Recycly = new ArrayList<>();

        Po_Item_For_Recycly=database.userDao().getAllRecievedPackages();
        recievedPackagesAdapter = new RecievedPackagesAdapter(Po_Item_For_Recycly);

        binding.recycleItemsView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        binding.recycleItemsView.setLayoutManager(mLayoutManager);
        binding.recycleItemsView.setAdapter(recievedPackagesAdapter);

    }

    private void GETOrderData(String ordernumber ){
        recievePackedOrderViewModel.fetchdata(ordernumber);

    }

    private void AfterGetOrderData(RecievePackedModule responseGetOrderData , String trackingnumber) {
        String NOtrackingnumber= binding.editTrackingnumber.getText().toString().substring(
                binding.editTrackingnumber.getText().toString().indexOf("-")+1);

        if (Integer.valueOf(responseGetOrderData.getNO_OF_PACKAGES()) >=
                Integer.valueOf(NOtrackingnumber)) {
            database.userDao().insertRecievePacked(new RecievePackedModule(
                    responseGetOrderData.getORDER_NO(), responseGetOrderData.getNO_OF_PACKAGES(),
                    trackingnumber));
            binding.editTrackingnumber.setError(null);
            binding.editTrackingnumber.setText("");
            CreateORUpdateRecycleView();
            Toast.makeText(RecievedPackedAndSortedOrderForSortingAndDriverActivity.this, "تم", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, getResources().getString(R.string.invalidnumber), Toast.LENGTH_SHORT).show();
        }
        Log.e(TAG, "onChanged: insertR"+trackingnumber );
    }

    public void UpdateStatus_ON_83(){
//        if (database.userDao().getAllItemsWithoutTrackingnumber().size() == 0){
        List<RecievePackedModule> orderDataModuleDBHeaderkist = database.userDao().getorderNORecievePackedModule();
        if (orderDataModuleDBHeaderkist.size() > 0) {
            for (int i=0;i<orderDataModuleDBHeaderkist.size();i++) {

                if (RecievePackedOrConfirmForDriver.equalsIgnoreCase("RecievePacked")) {
                    recievePackedOrderViewModel.UpdateStatus_ON_83(
                            orderDataModuleDBHeaderkist.get(i).getORDER_NO(),
                            "in sorting"
                    );
            }else if (RecievePackedOrConfirmForDriver.equalsIgnoreCase("ConfirmForDriver")){
                recievePackedOrderViewModel.UpdateStatus_ON_83(
                        orderDataModuleDBHeaderkist.get(i).getORDER_NO(),
                        "Ready To Go"
                );

            }else {
                Toast.makeText(context, getResources().getString(R.string.statusnotUpdate), Toast.LENGTH_SHORT).show();
            }
            }
        }else {
            Toast.makeText(context, getResources().getString(R.string.not_enter), Toast.LENGTH_SHORT).show();
        }
        recievePackedOrderViewModel.mutableLiveData_UpdateStatus_ON_83.observe(
                RecievedPackedAndSortedOrderForSortingAndDriverActivity.this, new Observer<ResponseUpdateStatus>() {
            @Override
            public void onChanged(ResponseUpdateStatus message) {
                Toast.makeText(RecievedPackedAndSortedOrderForSortingAndDriverActivity.this, ""+message.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onChanged:update83 "+message.getMessage() );
            }
        });
//        }else {
//            Toast.makeText(GetOrderDatactivity.this, "توجد عناصر لم يتم تعبئتها", Toast.LENGTH_SHORT).show();
//        }
    }

    public void UpdateStatus(){
//        if (database.userDao().getAllItemsWithoutTrackingnumber().size() == 0){
        List<RecievePackedModule> orderDataModuleDBHeaderkist = database.userDao().getorderNORecievePackedModule();
        if (orderDataModuleDBHeaderkist.size() > 0) {
            for (int i=0;i<orderDataModuleDBHeaderkist.size();i++) {

                if (RecievePackedOrConfirmForDriver.equalsIgnoreCase("RecievePacked")) {

                    recievePackedOrderViewModel.UpdateStatus(orderDataModuleDBHeaderkist.get(i).getORDER_NO(),"in_sorting");

                }else if (RecievePackedOrConfirmForDriver.equalsIgnoreCase("ConfirmForDriver")){

                    recievePackedOrderViewModel.UpdateStatus(orderDataModuleDBHeaderkist.get(i).getORDER_NO(),"ready_to_go");
                }else {
                    Toast.makeText(context, getResources().getString(R.string.statusnotUpdate), Toast.LENGTH_SHORT).show();
                }
            }
        }else {
            Toast.makeText(context, getResources().getString(R.string.not_enter), Toast.LENGTH_SHORT).show();
        }

        recievePackedOrderViewModel.mutableLiveData_UpdateStatus.observe(RecievedPackedAndSortedOrderForSortingAndDriverActivity.this, new Observer<ResponseUpdateStatus>() {
            @Override
            public void onChanged(ResponseUpdateStatus message) {
                Toast.makeText(RecievedPackedAndSortedOrderForSortingAndDriverActivity.this, ""+message.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onChanged:UpdateStatusrou "+message.getMessage() );
            }
        });
        recievePackedOrderViewModel.mutableLiveDataError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChanged:roub "+s );
                Toast.makeText(RecievedPackedAndSortedOrderForSortingAndDriverActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });


    }
    /*
    private void AssignToZone(String trackingnumber1 ,String Zone1){
        String OrderNumber=
                trackingnumber1.substring(0,
                        trackingnumber1.indexOf("-"));

        List<RecievePackedModule>  recievePackedlist=  database.userDao().getRecievePacked_ORDER_NO(OrderNumber);

        if (recievePackedlist.size() == 0) {
            GETOrderData(binding.editTrackingnumber.getText().toString());
        //    database.userDao().UpdatezoneForORDER_NO(OrderNumber,Zone1);
            Toast.makeText(context, "تم", Toast.LENGTH_SHORT).show();
        }else if (recievePackedlist.size() >= 0){
            if (recievePackedlist.get(0).getZone().isEmpty()) {
                database.userDao().UpdatezoneForORDER_NO(OrderNumber,Zone1);
                Toast.makeText(context, "تم", Toast.LENGTH_SHORT).show();
            }else if (!recievePackedlist.get(0).getZone().isEmpty()){
                new AlertDialog.Builder(RecievePackedOrderForSortingActivity.this)
                        .setTitle(getString(R.string.updte_zone_if_exist))
                        .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                database.userDao().UpdatezoneForORDER_NO(OrderNumber,Zone1);
                                Toast.makeText(context, "تم", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        }).show();
            }
        }
    }
     */
}