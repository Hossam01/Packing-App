package com.example.packingapp.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.Helper.Constant;
import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityAssignPackedOrderForZoneDriverBinding;
import com.example.packingapp.model.GetOrderResponse.OrderDataModuleDBHeader;
import com.example.packingapp.model.RecievePacked.RecievePackedModule;
import com.example.packingapp.model.ResponseDriver;
import com.example.packingapp.model.ResponseUpdateStatus;
import com.example.packingapp.viewmodel.AssignPackedOrderToZoneViewModel;
import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.Code93;
import com.onbarcode.barcode.android.IBarcode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssignPackedOrderForZoneAndDriverActivity extends AppCompatActivity {
    private static final String TAG = "AssignPackedOrderForZon";
    ActivityAssignPackedOrderForZoneDriverBinding binding;
    AssignPackedOrderToZoneViewModel assignPackedOrderToZoneViewModel;
    AppDatabase database;
    final Context context = this;
    String Zone ,trackingnumberIn;
    ArrayList<String> Drivers_IDs_list ;
    ResponseDriver responseDriver;
    String trackingNo="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAssignPackedOrderForZoneDriverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=AppDatabase.getDatabaseInstance(this);

        assignPackedOrderToZoneViewModel= ViewModelProviders.of(this).get(AssignPackedOrderToZoneViewModel.class);

        GETDriverID();

       ControlLayout();

        ButtonsClickListnerForAssignToZone();
        ButtonsClickListnerForAssignToDriver();
    }

    private void ButtonsClickListnerForAssignToZone() {
        binding.btnLoadingNewPurchaseOrderZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.editTrackingnumberZone.getText().toString().isEmpty() &&
                        !binding.editZone.getText().toString().isEmpty()) {
                    if (Constant.RegulerExpre_forTrackingNumbeer(binding.editTrackingnumberDriver.getText().toString())) {
                        Toast.makeText(context, "Special character not found in the string", Toast.LENGTH_SHORT).show();
                        LoadNewPurchaseOrderBTN_Zone();
                    }else {
                        Toast.makeText(context, "Special character found in the string", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (binding.editTrackingnumberZone.getText().toString().isEmpty()) {
                        binding.editTrackingnumberZone.setError("أدخل السريال");
                        binding.editTrackingnumberZone.requestFocus();
                    }else if (binding.editZone.getText().toString().isEmpty()){
                        binding.editZone.setError("أدخل المنطقه");
                        binding.editZone.requestFocus();
                    }
                }
            }
        });

        binding.btnDeleteLastTrackingnumbersZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<RecievePackedModule>  recievePackedORDER_NO_Distinctlist=  database.userDao().getRecievePacked_ORDER_NO_Distinct();
                if (recievePackedORDER_NO_Distinctlist.size()>0) {
                    new AlertDialog.Builder(AssignPackedOrderForZoneAndDriverActivity.this)
                            .setTitle(getString(R.string.delete_dialoge))
                            .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    database.userDao().deleteRecievePackedModule();
                                    binding.editTrackingnumberZone.setError(null);
                                    binding.editZone.setError(null);
                                    binding.editZone.setText("");
                                }
                            })
                            .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.cancel();
                                }
                            }).show();
                }else {
                    Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, "لايوجد بيانات للحذف", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnConfirmAssignOrdersToZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<RecievePackedModule>  recievePackedORDER_NO_Distinctlist=  database.userDao().getRecievePacked_ORDER_NO_Distinct();
                List<RecievePackedModule>  NOTrecievedPackedORDERlist=  new ArrayList<>();
                if (recievePackedORDER_NO_Distinctlist.size()>0) {
                    for (int i = 0; i>recievePackedORDER_NO_Distinctlist.size();i++){
                        List<String>  recievePacked_Tracking_Number_countlist=
                                database.userDao().getRecievePacked_Tracking_Number_count(recievePackedORDER_NO_Distinctlist.get(i).getORDER_NO());
                        if (!recievePacked_Tracking_Number_countlist.get(i).
                                equalsIgnoreCase(recievePackedORDER_NO_Distinctlist.get(i).getNO_OF_PACKAGES().toString())){
                            NOTrecievedPackedORDERlist.add(recievePackedORDER_NO_Distinctlist.get(i));
                        }
                    }

                    if (NOTrecievedPackedORDERlist.size()==0){
                        //TODO UPDATE STATUS
                        // Toast.makeText(RecievePackedOrderForSortingActivity.this, String.format("%s",getString(R.string.message_equalfornoofpaclkageandcountoftrackingnumbers)), Toast.LENGTH_SHORT).show();
                        UpdateStatus_zone_ON_83("sorted");
                        UpdateStatus("sorted");

                    }else {
                        Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, String.format("%s",
                                getString(R.string.message_not_equalfornoofpaclkageandcountoftrackingnumbers)), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, String.format("%s",
                            getString(R.string.there_is_no_trackednumber_scanned)), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void ButtonsClickListnerForAssignToDriver() {
        binding.btnLoadingNewPurchaseOrderDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.editTrackingnumberDriver.getText().toString().isEmpty()) {
                    if (Constant.RegulerExpre_forTrackingNumbeer(binding.editTrackingnumberDriver.getText().toString())) {
                        Toast.makeText(context, "Special character not found in the string", Toast.LENGTH_SHORT).show();
                        trackingNo=binding.editTrackingnumberDriver.getText().toString();
                        LoadNewPurchaseOrderBTN_Driver();
                    }else {
                        Toast.makeText(context, "Special character found in the string", Toast.LENGTH_SHORT).show();
                    }
                }else {
                        binding.editTrackingnumberDriver.setError("أدخل السريال");
                        binding.editTrackingnumberDriver.requestFocus();
                }
            }
        });

        binding.btnDeleteLastTrackingnumbersDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<RecievePackedModule>  recievePackedORDER_NO_Distinctlist=  database.userDao().getRecievePacked_ORDER_NO_Distinct();
                if (recievePackedORDER_NO_Distinctlist.size()>0) {
                    new AlertDialog.Builder(AssignPackedOrderForZoneAndDriverActivity.this)
                            .setTitle(getString(R.string.delete_dialoge))
                            .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    database.userDao().deleteRecievePackedModule();
                                    binding.editTrackingnumberDriver.setError(null);

                                }
                            })
                            .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.cancel();
                                }
                            }).show();
                }else {
                    Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, "لايوجد بيانات للحذف", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnConfirmAssignOrdersToDriver.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick:spi "+binding.spinerDriverId.getSelectedItemPosition());
                Log.e(TAG, "onClick:spic  "+binding.spinerDriverId.getSelectedItem().toString());
                //TODO apply validation to spinner driver id
                if (binding.spinerDriverId.getSelectedItemPosition()!=0) {
                    List<RecievePackedModule> recievePackedORDER_NO_Distinctlist = database.userDao().getRecievePacked_ORDER_NO_Distinct();
                    List<RecievePackedModule> NOTrecievedPackedORDERlist = new ArrayList<>();
                    if (recievePackedORDER_NO_Distinctlist.size() > 0) {
                        for (int i = 0; i > recievePackedORDER_NO_Distinctlist.size(); i++) {
                            List<String> recievePacked_Tracking_Number_countlist =
                                    database.userDao().getRecievePacked_Tracking_Number_count(recievePackedORDER_NO_Distinctlist.get(i).getORDER_NO());
                            if (!recievePacked_Tracking_Number_countlist.get(i).
                                    equalsIgnoreCase(recievePackedORDER_NO_Distinctlist.get(i).getNO_OF_PACKAGES().toString())) {
                                NOTrecievedPackedORDERlist.add(recievePackedORDER_NO_Distinctlist.get(i));
                            }
                        }

                        if (NOTrecievedPackedORDERlist.size() == 0) {
                            //TODO UPDATE STATUS and print Run time sheet
                            // Toast.makeText(RecievePackedOrderForSortingActivity.this, String.format("%s",getString(R.string.message_equalfornoofpaclkageandcountoftrackingnumbers)), Toast.LENGTH_SHORT).show();
                            // UpdateStatus_zone_ON_83("sorted");
                            // UpdateStatus("sorted");
                            UpdateDriverID_ON_83(binding.spinerDriverId.getSelectedItem().toString());
                            OrderDataModuleDBHeader orderDataModuleDBHeader= database.userDao().getHeaderToUpload();
                            PrintRunTimeSheet(trackingNo,orderDataModuleDBHeader.getCustomer_name(),orderDataModuleDBHeader.getCustomer_address_city(),orderDataModuleDBHeader.getOutBound_delivery());
                        } else {
                            Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, String.format("%s",
                                    getString(R.string.message_not_equalfornoofpaclkageandcountoftrackingnumbers)), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, String.format("%s",
                                getString(R.string.there_is_no_trackednumber_scanned)), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, getResources().getString(R.string.choice_driver_id), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void ControlLayout() {
        binding.btnShowAssignOrdersToZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<RecievePackedModule> list=database.userDao().getorderNORecievePackedModule();
                if (list.size()>0) {
                    new AlertDialog.Builder(AssignPackedOrderForZoneAndDriverActivity.this)
                            .setTitle(getString(R.string.delete_dialoge))
                            .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    database.userDao().deleteRecievePackedModule();

                                    if (binding.linearAssignOrderToZone.getVisibility() == View.GONE) {
                                        binding.linearAssignOrderToZone.setVisibility(View.VISIBLE);
                                        binding.linearAssignOrderToDriver.setVisibility(View.GONE);
                                    } else if (binding.linearAssignOrderToZone.getVisibility() == View.VISIBLE) {
                                        binding.linearAssignOrderToZone.setVisibility(View.GONE);
                                        binding.linearAssignOrderToDriver.setVisibility(View.GONE);
                                    }

                                }
                            })
                            .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.cancel();
                                }
                            }).show();
                }else {
                    if (binding.linearAssignOrderToZone.getVisibility() == View.GONE) {
                        binding.linearAssignOrderToZone.setVisibility(View.VISIBLE);
                        binding.linearAssignOrderToDriver.setVisibility(View.GONE);
                    } else if (binding.linearAssignOrderToZone.getVisibility() == View.VISIBLE) {
                        binding.linearAssignOrderToZone.setVisibility(View.GONE);
                        binding.linearAssignOrderToDriver.setVisibility(View.GONE);
                    }
                }
            }
        });

        binding.btnShowAssignOrdersToDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<RecievePackedModule> list=database.userDao().getorderNORecievePackedModule();
                if (list.size()>0) {
                    new AlertDialog.Builder(AssignPackedOrderForZoneAndDriverActivity.this)
                            .setTitle(getString(R.string.delete_dialoge))
                            .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    database.userDao().deleteRecievePackedModule();

                                    if (binding.linearAssignOrderToDriver.getVisibility() == View.GONE) {
                                        binding.linearAssignOrderToZone.setVisibility(View.GONE);
                                        binding.linearAssignOrderToDriver.setVisibility(View.VISIBLE);
                                    } else if (binding.linearAssignOrderToDriver.getVisibility() == View.VISIBLE) {
                                        binding.linearAssignOrderToZone.setVisibility(View.GONE);
                                        binding.linearAssignOrderToDriver.setVisibility(View.GONE);
                                    }

                                }
                            })
                            .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.cancel();
                                }
                            }).show();
                }else {
                    if (binding.linearAssignOrderToDriver.getVisibility() == View.GONE) {
                        binding.linearAssignOrderToZone.setVisibility(View.GONE);
                        binding.linearAssignOrderToDriver.setVisibility(View.VISIBLE);
                    } else if (binding.linearAssignOrderToDriver.getVisibility() == View.VISIBLE) {
                        binding.linearAssignOrderToZone.setVisibility(View.GONE);
                        binding.linearAssignOrderToDriver.setVisibility(View.GONE);
                    }
                }
            }
        });
    }


    private void LoadNewPurchaseOrderBTN_Zone() {
        String OrderNumber;
        if (
                //!binding.editTrackingnumberZone.getText().toString().isEmpty() &&
                binding.editTrackingnumberZone.getText().toString().contains("-")) {
            OrderNumber=
                    binding.editTrackingnumberZone.getText().toString().substring(0,
                            binding.editTrackingnumberZone.getText().toString().indexOf("-"));

            List<RecievePackedModule> recievePackedlist=  database.userDao().getRecievePacked_ORDER_NO(OrderNumber);
            if (recievePackedlist.size() == 0){
                binding.editTrackingnumberZone.setError(null);

                AssignToZone(binding.editTrackingnumberZone.getText().toString()
                        ,binding.editZone.getText().toString());
                Log.e(TAG, "onClick: Ord "+OrderNumber );
            //    Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, "تم", Toast.LENGTH_SHORT).show();
            }else if (database.userDao().getRecievePacked_Tracking_Number(binding.editTrackingnumberZone.getText().toString())
                    .size() ==0){
                binding.editTrackingnumberZone.setError(null);

                Log.e(TAG, "onClick: Trac "+binding.editTrackingnumberZone.getText().toString() );
            //    Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, "تم", Toast.LENGTH_SHORT).show();
                AssignToZone(binding.editTrackingnumberZone.getText().toString()
                        ,binding.editZone.getText().toString());
            }else {
                binding.editTrackingnumberZone.setError("تم أدخال هذا من قبل ");
                binding.editTrackingnumberZone.setText("");
                binding.editZone.setText("");
                binding.editTrackingnumberZone.requestFocus();
            }
        }else {

            binding.editTrackingnumberZone.setError(getString(R.string.enter_valid_tracking_number));
            binding.editTrackingnumberZone.setText("");
            binding.editZone.setText("");
            binding.editTrackingnumberZone.requestFocus();

        }

    }

   private void AssignToZone(String trackingnumber1 ,String Zone1 ){
        String OrderNumber=
                trackingnumber1.substring(0,
                        trackingnumber1.indexOf("-"));

        List<RecievePackedModule>  recievePackedlist =  database.userDao().getRecievePacked_ORDER_NO(OrderNumber);
        if (recievePackedlist.size() == 0) {
            GETOrderData(trackingnumber1,Zone1);
            Toast.makeText(context, "تم", Toast.LENGTH_SHORT).show();
        }else if (recievePackedlist.size() >= 0){
                if (recievePackedlist.get(0).getZone().equalsIgnoreCase(Zone1)) {
                    database.userDao().insertRecievePacked(new RecievePackedModule(
                            recievePackedlist.get(0).getORDER_NO(), recievePackedlist.get(0).getNO_OF_PACKAGES(),
                            trackingnumber1, Zone));
                    binding.editTrackingnumberZone.setText("");
                    binding.editTrackingnumberZone.setError(null);
                    binding.editZone.setText("");
                    binding.editZone.setError(null);
                    Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, "تم", Toast.LENGTH_SHORT).show();

                    Toast.makeText(context, "تم", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(AssignPackedOrderForZoneAndDriverActivity.this)
                            .setTitle(getString(R.string.updte_zone_if_exist))
                            .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    database.userDao().insertRecievePacked(new RecievePackedModule(
                                            recievePackedlist.get(0).getORDER_NO(), recievePackedlist.get(0).getNO_OF_PACKAGES(),
                                            trackingnumber1, Zone));
                                    binding.editTrackingnumberZone.setText("");
                                    binding.editTrackingnumberZone.setError(null);
                                    binding.editZone.setText("");
                                    binding.editZone.setError(null);
                                    database.userDao().UpdatezoneForORDER_NO(OrderNumber, Zone1);
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

    private void GETOrderData(String trackingnumber ,String Zone ){
        assignPackedOrderToZoneViewModel.fetchdata(trackingnumber);
        assignPackedOrderToZoneViewModel.getOrderDataLiveData().observe(AssignPackedOrderForZoneAndDriverActivity.this, new Observer<RecievePackedModule>() {
            @Override
            public void onChanged(RecievePackedModule responseGetOrderData) {
                    Log.e(TAG, "onChanged: " + responseGetOrderData.getNO_OF_PACKAGES());
                    database.userDao().insertRecievePacked(new RecievePackedModule(
                            responseGetOrderData.getORDER_NO(), responseGetOrderData.getNO_OF_PACKAGES(),
                            trackingnumber,Zone));
                    binding.editTrackingnumberZone.setText("");
                    binding.editTrackingnumberZone.setError(null);
                    binding.editZone.setText("");
                    binding.editZone.setError(null);
                    Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, "تم", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onChanged: insertR" + trackingnumber);

            }

        });

        assignPackedOrderToZoneViewModel.mutableLiveDataError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChanged: "+s );

                if (s.equals("HTTP 503 Service Unavailable")) {
                      Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, "السريال غير موجود على السرفير", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, s, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void UpdateStatus_zone_ON_83(String Status){
//        if (database.userDao().getAllItemsWithoutTrackingnumber().size() == 0){
        List<RecievePackedModule> orderDataModuleDBHeaderkist = database.userDao().getorderNORecievePackedModule();
        if (orderDataModuleDBHeaderkist.size() > 0) {
            assignPackedOrderToZoneViewModel.UpdateOrderStatus_Zone_ON_83(
                    orderDataModuleDBHeaderkist.get(0).getORDER_NO(),
                    orderDataModuleDBHeaderkist.get(0).getZone(),Status
            );
            Log.e(TAG, "UpdateStatus_zone_ON_83 zzzo : "+orderDataModuleDBHeaderkist.get(0).getZone() );
        }else {
            Toast.makeText(context, "لم يتم أدخال ", Toast.LENGTH_SHORT).show();
        }
        assignPackedOrderToZoneViewModel.mutableLiveData_UpdateStatus_Zone_ON_83.observe(AssignPackedOrderForZoneAndDriverActivity.this, new Observer<ResponseUpdateStatus>() {
            @Override
            public void onChanged(ResponseUpdateStatus message) {
                Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, ""+message.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onChanged:update "+message.getMessage() );
            }
        });
//        }else {
//            Toast.makeText(GetOrderDatactivity.this, "توجد عناصر لم يتم تعبئتها", Toast.LENGTH_SHORT).show();
//        }
    }

    public void UpdateDriverID_ON_83(String DriverID){
//        if (database.userDao().getAllItemsWithoutTrackingnumber().size() == 0){
        List<RecievePackedModule> orderDataModuleDBHeaderkist = database.userDao().getorderNORecievePackedModule();
        if (orderDataModuleDBHeaderkist.size() > 0) {
            assignPackedOrderToZoneViewModel.UpdateOrder_DriverID_ON_83(
                    orderDataModuleDBHeaderkist.get(0).getORDER_NO(),
                    DriverID
            );
           // Log.e(TAG, "UpdateStatus_zone_ON_83 zzzo : "+orderDataModuleDBHeaderkist.get(0).getZone() );
        }else {
            Toast.makeText(context, "لم يتم أدخال ", Toast.LENGTH_SHORT).show();
        }
        assignPackedOrderToZoneViewModel.mutableLiveData_UpdateDriverID_ON_83.observe(AssignPackedOrderForZoneAndDriverActivity.this, new Observer<ResponseUpdateStatus>() {
            @Override
            public void onChanged(ResponseUpdateStatus message) {
                Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, ""+message.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onChanged:update "+message.getMessage() );
            }
        });
//        }else {
//            Toast.makeText(GetOrderDatactivity.this, "توجد عناصر لم يتم تعبئتها", Toast.LENGTH_SHORT).show();
//        }
    }

    public void UpdateStatus(String Status){
//        if (database.userDao().getAllItemsWithoutTrackingnumber().size() == 0){

        List<RecievePackedModule> orderDataModuleDBHeaderkist = database.userDao().getorderNORecievePackedModule();

        assignPackedOrderToZoneViewModel.UpdateStatus(
                orderDataModuleDBHeaderkist.get(0).getORDER_NO(),
                Status
        );
        assignPackedOrderToZoneViewModel.mutableLiveData_UpdateStatus.observe(AssignPackedOrderForZoneAndDriverActivity.this, new Observer<ResponseUpdateStatus>() {
            @Override
            public void onChanged(ResponseUpdateStatus message) {
                Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, ""+message.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onChanged:UpdateStatus "+message.getMessage() );
            }
        });
        assignPackedOrderToZoneViewModel.mutableLiveDataError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChanged: "+s );
                Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });


    }

    private void GETDriverID() {
        Drivers_IDs_list =new ArrayList<>();
        assignPackedOrderToZoneViewModel.GetDriversID();
        //TODO TO ASSIGN ID TO SPINNER
        ArrayAdapter<String> spinnerAdapterDriver=new ArrayAdapter<String>(AssignPackedOrderForZoneAndDriverActivity.this,
                android.R.layout.simple_spinner_item,Drivers_IDs_list);
        spinnerAdapterDriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinerDriverId.setAdapter(spinnerAdapterDriver);

        assignPackedOrderToZoneViewModel.mutableLiveDataRead.observe(AssignPackedOrderForZoneAndDriverActivity.this
                , (ResponseDriver responseDriver) -> {
                    if (Drivers_IDs_list.size() == 0) {
                        for (int i = 0; i < responseDriver.getRecords().size(); i++) {
                            if (i == 0)
                                Drivers_IDs_list.add("Select ID Driver");
                            Drivers_IDs_list.add(responseDriver.getRecords().get(i).getDriverID());
                        }
                    }
                    this.responseDriver = responseDriver;
                    spinnerAdapterDriver.notifyDataSetChanged();

                });
    }


     private void LoadNewPurchaseOrderBTN_Driver() {
        String OrderNumber;
        if (!binding.editTrackingnumberDriver.getText().toString().isEmpty() &&
                binding.editTrackingnumberDriver.getText().toString().contains("-")) {
            OrderNumber=
                    binding.editTrackingnumberDriver.getText().toString().substring(0,
                            binding.editTrackingnumberDriver.getText().toString().indexOf("-"));

            List<RecievePackedModule> recievePackedlist=  database.userDao().getRecievePacked_ORDER_NO(OrderNumber);
            if (recievePackedlist.size() == 0){
                binding.editTrackingnumberDriver.setError(null);

                GETOrderData(binding.editTrackingnumberDriver.getText().toString(),null);
                Log.e(TAG, "onClick: Ord "+OrderNumber );
                Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, "تم", Toast.LENGTH_SHORT).show();
            }else if (database.userDao().getRecievePacked_Tracking_Number(binding.editTrackingnumberDriver.getText().toString())
                    .size() ==0){
                binding.editTrackingnumberDriver.setError(null);

                GETOrderData(binding.editTrackingnumberDriver.getText().toString(),null);
                Log.e(TAG, "onClick: Trac "+binding.editTrackingnumberDriver.getText().toString() );
                Toast.makeText(AssignPackedOrderForZoneAndDriverActivity.this, "تم", Toast.LENGTH_SHORT).show();
            }else {
                binding.editTrackingnumberDriver.setError("تم أدخال هذا من قبل ");
                binding.editTrackingnumberDriver.setText("");
                binding.editTrackingnumberDriver.requestFocus();
            }
        }else {

            binding.editTrackingnumberDriver.setError(getString(R.string.enter_tracking_number));
            binding.editTrackingnumberDriver.setText("");
            binding.editTrackingnumberDriver.requestFocus();

        }

    }

    //TODO
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void PrintRunTimeSheet(String trackingNo,String customer_name,String address,String outDelivery) {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 0);
        createPdf(trackingNo,customer_name,address,outDelivery);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf(String trackingNo,String customer_name,String address,String outDelivery) {

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        paint.setTextSize(30.0f);
        PdfDocument.Page page = pdfDocument.startPage(new PdfDocument.PageInfo.Builder(2000, 3000, 1).create());
        Canvas canvas = page.getCanvas();
        canvas.drawText("إقرار إستلام /Receiving Avowal", 700.0f, 60.0f, paint);
        canvas.drawText("التاريخ/Date : " + currentDate + "           الوقت/Time : " + currentTime + " ", 550.0f, 100.0f, paint);
        canvas.drawText("استلمت أنا ....................................... رقم قومي .............................  مندوب (شركة هايبروان للتجارة) البضاعة الموجودة بالشحنات المذكورأرقامها بالأسفل", 30.0f, 140.0f, paint);
        canvas.drawText("وذلك لتسليمها لعملاء الشركة وتحصيل قيمتها منهم على أن ألتزم برد الطلبيات التي لم تسلم للعملاء لمخزن الشركة بنفس حالة إستلامها وتسديد ما أقوم بتحصيله", 30.0f, 180.0f, paint);
        canvas.drawText("من العملاء لخزينة الشركة وتعتبر البضاعة وما أقوم بتحصيله من العملاء هو أمانة في ذمتي أتعهد بتسليمها للشركة, وإذا أخلللت بذلك أكون مبددا وخائنا للأمانة . ", 30.0f, 220.0f, paint);
        canvas.drawText("وأتحمل كافة المسئولية الجنائية والمدنية المترتبة على ذلك. ", 550.0f, 260.0f, paint);

              /*  canvas.drawText("I ............................................................. id no. .......................................  as (HyperOne Co.) delivery associate admit that I received the products in the packages numbers mentioned ", 20.0f, 230.0f, paint);
                canvas.drawText("below to deliver to (HyperOne Co.) customers and collect their values from them and I commit to return the failed delivery  to (HyperOne Co.) warehouse in", 20.0f, 270.0f, paint);
                canvas.drawText("the same receiving condition and pay what I had collected to (HyperOne Co.) cashier.", 20.0f, 310.0f, paint);
                canvas.drawText("the products and the money that belong to (HyperOne Co.) are fidelity I pledge to give it to (HyperOne Co.), if I didn't do this, I will be a traitor to the fidelity", 20.0f, 350.0f, paint);
                canvas.drawText("and bear all criminal and civil liability arising therefrom.", 20.0f, 230.0f, paint);*/
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);
        Paint paint2 = paint;
        //top of header row  line change bottom
        //bottom of table    change top
        canvas.drawRect(30.0f, 2600.0f, 1940.0f, 280.0f, paint2);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawText("S/م", 1925.0f, 310.0f, paint);
        canvas.drawLine(1870.0f, 280.0f, 1870.0f, 2600.0f, paint2);
        canvas.drawText("outBound", 1830.0f, 310.0f, paint);
        canvas.drawLine(1660.0f, 280.0f, 1660.0f, 2600.0f, paint2);
        canvas.drawText("رقم الشحنة", 1500.0f, 310.0f, paint);
        canvas.drawLine(1150.0f, 280.0f, 1150.0f, 2600.0f, paint2);
        canvas.drawText("قيمة الشحنة", 1140.0f, 310.0f, paint);
        canvas.drawLine(997.0f, 280.0f, 997.0f, 2600.0f, paint2);
        canvas.drawText("طريقة الدفع", 990.0f, 310.0f, paint);
        canvas.drawLine(850.0f, 280.0f, 850.0f, 2600.0f, paint2);
//                canvas.drawText("نوع الشحنة", 700.0f, 280.0f, paint);
//                canvas.drawLine(660.0f, 780.0f, 660.0f, 250.0f, paint2);
        canvas.drawText("إسم العميل", 760.0f, 310.0f, paint);
        canvas.drawLine(500.0f, 280.0f, 500.0f, 2600.0f, paint2);
//                canvas.drawText("نوع الشحنة", 580.0f, 280.0f, paint);
//                canvas.drawLine(500.0f, 780.0f, 500.0f, 250.0f, paint2);
        canvas.drawText("عنوان العميل", 480.0f, 310.0f, paint);
//                canvas.drawLine(400.0f, 780.0f, 400.0f, 250.0f, paint2);
//                canvas.drawText("تليفون العميل", 380.0f, 280.0f, paint);
        canvas.drawLine(300.0f, 280.0f, 300.0f, 2600.0f, paint2);
        canvas.drawText("ملاحظات", 180.0f, 310.0f, paint);

        //bottom of header row  line
        canvas.drawLine(30.0f, 330.0f, 1940.0f, 330.0f, paint2);

        canvas.drawText("توقيع المستلم/Receiver sign", 1500.0f, 2700.0f, paint);
        canvas.drawText("توقيع مسئول أمن المخزن", 1000.0f, 2700.0f, paint);
        canvas.drawText("توقيع منسق التوصيل", 600.0f, 2700.0f, paint);

        canvas.drawText(customer_name, 760.0f, 390, paint);
        canvas.drawText(address, 480.0f, 390, paint);
        canvas.drawText(outDelivery, 1830.0f, 390, paint);

        try {
            testCODE93(canvas,1150.0f, 340.0f,trackingNo);
            canvas.drawLine(30.0f, 430.0f, 1940.0f, 430.0f, paint2);


        } catch (Exception e) {
            e.printStackTrace();
        }

        pdfDocument.finishPage(page);
        try {
            pdfDocument.writeTo(new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "/HyperOne.pdf")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();

    }
    private static void testCODE93(Canvas canvas , float left, float top,String trackingnumber) throws Exception
    {
        Code93 barcode = new Code93();

        /*
           Code 93 Valid data char set:
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9 (Digits)
                A - Z (Uppercase letters)
                - (Dash), $ (Dollar), % (Percentage), (Space), . (Point), / (Slash), + (Plus)
        */
        barcode.setData(trackingnumber);

        // Unit of Measure, pixel, cm, or inch
        barcode.setUom(IBarcode.UOM_PIXEL);
        // barcode bar module width (X) in pixel
        barcode.setX(3f);
        // barcode bar module height (Y) in pixel
        barcode.setY(60f);

        // barcode image margins
//        barcode.setLeftMargin(1f);
//        barcode.setRightMargin(1f);
//        barcode.setTopMargin(1f);
//        barcode.setBottomMargin(1f);

        // barcode image resolution in dpi
        barcode.setResolution(72);

        // disply barcode encoding data below the barcode
        // barcode.setShowText(true);
        // barcode encoding data font style
        //   barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 16));
        // space between barcode and barcode encoding data
        barcode.setTextMargin(10);
        barcode.setTextColor(AndroidColor.black);

        // barcode bar color and background color in Android device
        barcode.setForeColor(AndroidColor.black);
        barcode.setBackColor(AndroidColor.white);

        /*
        specify your barcode drawing area
	    */
        RectF bounds = new RectF(left, top, 0, 0);
        barcode.drawBarcode(canvas, bounds);
    }
}