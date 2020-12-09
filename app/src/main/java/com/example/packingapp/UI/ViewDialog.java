package com.example.packingapp.UI;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.R;
import com.example.packingapp.UI.Printer.DemoSleeper;
import com.example.packingapp.UI.Printer.SettingsHelper;
import com.example.packingapp.model.GetOrderResponse.ItemsOrderDataDBDetails;
import com.example.packingapp.model.GetOrderResponse.OrderDataModuleDBHeader;
import com.example.packingapp.model.TrackingnumbersListDB;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.comm.TcpConnection;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.PrinterStatus;
import com.zebra.sdk.printer.SGD;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;
import com.zebra.sdk.printer.ZebraPrinterLinkOs;

import java.util.ArrayList;
import java.util.List;

public class ViewDialog {
    private Connection connection;

    private RadioButton btRadioButton;
    private EditText macAddressEditText;
    private EditText ipAddressEditText;
    private EditText portNumberEditText;
    private static final String bluetoothAddressKey = "ZEBRA_DEMO_BLUETOOTH_ADDRESS";
    private static final String tcpAddressKey = "ZEBRA_DEMO_TCP_ADDRESS";
    private static final String tcpPortKey = "ZEBRA_DEMO_TCP_PORT";
    private static final String PREFS_NAME = "OurSavedAddress";
    AppDatabase database;

    private Button testButton;
    private ZebraPrinter printer;
    private TextView statusField;
    Dialog dialog;
    Activity activity;
    public void showDialog(Activity activity){
        this.activity=activity;
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialogbox_otp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        database= AppDatabase.getDatabaseInstance(activity);

        SharedPreferences settings = dialog.getContext().getSharedPreferences(PREFS_NAME, 0);

        ipAddressEditText = (EditText) dialog.findViewById(R.id.ipAddressInput);
        String ip = settings.getString(tcpAddressKey, "");
        ipAddressEditText.setText(ip);

        portNumberEditText = (EditText) dialog.findViewById(R.id.portInput);
        String port = settings.getString(tcpPortKey, "");
        portNumberEditText.setText(port);

        macAddressEditText = (EditText) dialog.findViewById(R.id.macInput);
        String mac = settings.getString(bluetoothAddressKey, "");
        macAddressEditText.setText(mac);

//        TextView t2 = (TextView) dialog.findViewById(R.id.launchpad_link);
//        t2.setMovementMethod(LinkMovementMethod.getInstance());

        statusField = (TextView) dialog.findViewById(R.id.statusText);


        btRadioButton = (RadioButton) dialog.findViewById(R.id.bluetoothRadio);


        RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.bluetoothRadio) {
                    toggleEditField(macAddressEditText, true);
                    toggleEditField(portNumberEditText, false);
                    toggleEditField(ipAddressEditText, false);
                } else {
                    toggleEditField(portNumberEditText, true);
                    toggleEditField(ipAddressEditText, true);
                    toggleEditField(macAddressEditText, false);
                }
            }
        });
        testButton = (Button) dialog.findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        enableTestButton(false);
                        Looper.prepare();
                        doConnectionTest();
                        Looper.loop();
                        Looper.myLooper().quit();
                    }
                }).start();
            }
        });


        dialog.show();
    }



    public ZebraPrinter connect() {
        setStatus("Connecting...", Color.YELLOW);
        connection = null;
        if (isBluetoothSelected()) {
            connection = new BluetoothConnection(getMacAddressFieldText());
            SettingsHelper.saveBluetoothAddress(dialog.getContext(), getMacAddressFieldText());
        } else {
            try {
                int port = Integer.parseInt(getTcpPortNumber());
                connection = new TcpConnection(getTcpAddress(), port);
                SettingsHelper.saveIp(dialog.getContext(), getTcpAddress());
                SettingsHelper.savePort(dialog.getContext(), getTcpPortNumber());
            } catch (NumberFormatException e) {
                setStatus("Port Number Is Invalid", Color.RED);
                return null;
            }
        }

        try {
            connection.open();
            setStatus("Connected", Color.GREEN);
        } catch (ConnectionException e) {
            setStatus("Comm Error! Disconnecting", Color.RED);
            DemoSleeper.sleep(1000);
            disconnect();
        }

        ZebraPrinter printer = null;

        if (connection.isConnected()) {
            try {

                printer = ZebraPrinterFactory.getInstance(connection);
                setStatus("Determining Printer Language", Color.YELLOW);
                String pl = SGD.GET("device.languages", connection);
                setStatus("Printer Language " + pl, Color.BLUE);
            } catch (ConnectionException e) {
                setStatus("Unknown Printer Language", Color.RED);
                printer = null;
                DemoSleeper.sleep(1000);
                disconnect();
            } catch (ZebraPrinterLanguageUnknownException e) {
                setStatus("Unknown Printer Language", Color.RED);
                printer = null;
                DemoSleeper.sleep(1000);
                disconnect();
            }
        }

        return printer;
    }

    public void disconnect() {
        try {
            setStatus("Disconnecting", Color.RED);
            if (connection != null) {
                connection.close();
            }
            setStatus("Not Connected", Color.RED);
        } catch (ConnectionException e) {
            setStatus("COMM Error! Disconnected", Color.RED);
        } finally {
            enableTestButton(true);
        }
    }

    private void setStatus(final String statusMessage, final int color) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                statusField.setBackgroundColor(color);
                statusField.setText(statusMessage);
            }
        });
        DemoSleeper.sleep(1000);
    }


    private void sendTestLabel() {
        try {
            ZebraPrinterLinkOs linkOsPrinter = ZebraPrinterFactory.createLinkOsPrinter(printer);

            PrinterStatus printerStatus = (linkOsPrinter != null) ? linkOsPrinter.getCurrentStatus() : printer.getCurrentStatus();

            if (printerStatus.isReadyToPrint) {
                ArrayList<ItemsOrderDataDBDetails> print = new ArrayList<>();
                for (int i=0;i<database.userDao().getTrackingnumberDB().size();i++) {
                    print.clear();
                    for (int j=0;j<database.userDao().getDetailsTrackingnumberToUpload().size();j++) {
                        if (database.userDao().getTrackingnumberDB().get(i).getTrackingNumber().equals(database.userDao().getDetailsTrackingnumberToUpload().get(j).getTrackingNumber())) {
                            ItemsOrderDataDBDetails itemsOrderDataDBDetails = new ItemsOrderDataDBDetails(database.userDao().getDetailsTrackingnumberToUpload().get(j).getName(),database.userDao().getDetailsTrackingnumberToUpload().get(j).getPrice(),database.userDao().getDetailsTrackingnumberToUpload().get(j).getQuantity(),database.userDao().getDetailsTrackingnumberToUpload().get(j).getSku(),database.userDao().getDetailsTrackingnumberToUpload().get(j).getUnite(),database.userDao().getDetailsTrackingnumberToUpload().get(j).getTrackingNumber());
                            print.add(itemsOrderDataDBDetails);
                        }
                    }
                        byte[] configLabel = getConfigLabel(print);
                        connection.write(configLabel);
                        setStatus("Sending Data", Color.BLUE);

                }
            } else if (printerStatus.isHeadOpen) {
                setStatus("Printer Head Open", Color.RED);
            } else if (printerStatus.isPaused) {
                setStatus("Printer is Paused", Color.RED);
            } else if (printerStatus.isPaperOut) {
                setStatus("Printer Media Out", Color.RED);
            }
            DemoSleeper.sleep(1500);
            if (connection instanceof BluetoothConnection) {
                String friendlyName = ((BluetoothConnection) connection).getFriendlyName();
                setStatus(friendlyName, Color.MAGENTA);
                DemoSleeper.sleep(500);
            }
        } catch (ConnectionException e) {
            setStatus(e.getMessage(), Color.RED);
        } finally {
            disconnect();
        }
    }

    private void enableTestButton(final boolean enabled) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                testButton.setEnabled(enabled);
            }
        });
    }
    private byte[] getConfigLabel(List<ItemsOrderDataDBDetails> DetailsList) {
        byte[] configLabel = null;
        try {
            PrinterLanguage printerLanguage = printer.getPrinterControlLanguage();
            SGD.SET("device.languages", "zpl", connection);

            double totel = 0.0;
            List<TrackingnumbersListDB> trackingnumbersListDBS = database.userDao().countShipment();
            List<ItemsOrderDataDBDetails> orderDataModuleDBHeaderkist = DetailsList;
            ItemsOrderDataDBDetails itemsOrderDataDBDetails = new ItemsOrderDataDBDetails(" ", " ", 0, 0, " ");
            for (int i = 0; i < 30; i++) {
                if (i < orderDataModuleDBHeaderkist.size()) {
                    continue;
                } else {
                    orderDataModuleDBHeaderkist.add(i, itemsOrderDataDBDetails);
                    Log.e("size", "" + orderDataModuleDBHeaderkist.get(i).getName());
                }
            }
            for (int i = 0; i < orderDataModuleDBHeaderkist.size(); i++) {
                totel += Double.valueOf(orderDataModuleDBHeaderkist.get(i).getPrice());
            }
            if (printerLanguage == PrinterLanguage.ZPL) {

                    OrderDataModuleDBHeader orderDataModuleDBHeader = database.userDao().getHeaderToUpload();
                    try {

                        String name = "^XA" +
                                "^CWZ,E:TT0003M_.TTF^FS" +
                                "^CF0,10" +
                                "^FO30,30^CI28^AZN,20,15^FDفرع زايد^FS" +
                                "^FO700,30^CI28^AZN,20,15^FDفرع زايد^FS" +
                                "^FX" +
                                "^FO30,50^GB750,80,1^FS" +
                                "^FO500,50^GB1,80,1^FS" +
                                "^FO500,50^GB1,80,1^FS" +
                                "^FO500,90^GB280,1^FS" +
                                "^FO30,130^GB750,80,1^FS" +
                                "^FO500,50^GB1,160,1^FS" +
                                "^CF0,15" +
                                "^FO590,65^CI28^AZN,20,15^FDشركة الشحن^FS" +
                                "^FO600,105^CI28^AZN,20,15^FD" + orderDataModuleDBHeader.getCustomer_address_city() + "^FS" +
                                "^FO600,160^CI28^AZN,20,15^FD" + orderDataModuleDBHeader.getCustomer_address_govern() + "^FS" +
                                "^FO570,270^CI28^AZN,20,15^FDعدد الشحنات في الطلب^FS" +
                                "^FO450,270^CI28^AZN,20,15^FD" + trackingnumbersListDBS.size() + "^FS" +
                                "^CF0,25" +
                                "^FO200,65^CI28^AZN,20,15^FD" + orderDataModuleDBHeader.getCustomer_name() + "^FS" +
                                "^FO200,90^CI28^AZN,20,15^FD" + orderDataModuleDBHeader.getCustomer_phone() + "^FS" +
                                "^FO100,150^CI28^AZN,20,15^FD" + orderDataModuleDBHeader.getCustomer_address_detail() + "^FS" +
                                "^CF0,15" +
                                "^FO250,270^CI28^AZN,20,15^FDرقم الشحنه^FS" +
                                "^FO100,270^FD1of" + trackingnumbersListDBS.size() + "^FS" +
                                "^FO600,230^CI28^AZN,20,15^FD تحقيق الهوية ^FS" +
                                "^CF0,25" +
                                "^FO400,230^FD " + orderDataModuleDBHeader.getGrand_total() + " ^FS" +
                                "^FO100,230^CI28^AZN,20,15^FD " + checkPaymentMethod(orderDataModuleDBHeader.getGrand_total()) + " ^FS" +
                                "^CF0,15" +
                                "^FO200,465^CI28^AZN,20,15^FDاجمالي الطلب^FS" +
                                "^FO200,505^CI28^AZN,20,15^FDتكلفه الشحن^FS" +
                                "^FO220,550^CI28^AZN,20,15^FDالاجمالي^FS" +
                                "^FO650,465^CI28^AZN,20,15^FDرقم الطلب^FS" +
                                "^FO640,505^CI28^AZN,20,15^FDعدد القطع^FS" +
                                "^FO540,505^CI28^AZN,20,15^FD" + database.userDao().getDetailsTrackingnumberToUpload().size() + "^FS" +
                                "^FO500,465^CI28^AZN,20,15^FD" + orderDataModuleDBHeader.getOrder_number() + "^FS" +
                                "^FO80,465^CI28^AZN,20,15^FD" + totel + "^FS" +
                                "^FO100,505^CI28^AZN,20,15^FD" + orderDataModuleDBHeader.getShipping_fees() + "^FS\n" +
                                "^FO80,550^CI28^AZN,20,15^FD" + totel + Double.valueOf(orderDataModuleDBHeader.getShipping_fees()) + "^FS\n" +
                                "^FO590,610^CI28^AZN,20,15^FDاسم المنتج^FS" +
                                "^FO200,610^CI28^AZN,20,15^FDاسم المنتج^FS" +
                                "^FO405,610^CI28^AZN,20,15^FDالكميه^FS" +
                                "^FO35,610^CI28^AZN,20,15^FDالكميه^FS" +
                                "^FO50,640^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(1).getQuantity() + "^FS" +
                                "^FO50,670^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(3).getQuantity() + "^FS" +
                                "^FO50,700^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(5).getQuantity() + "^FS" +
                                "^FO50,730^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(7).getQuantity() + "^FS" +
                                "^FO50,760^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(9).getQuantity() + "^FS" +
                                "^FO50,790^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(11).getQuantity() + "^FS" +
                                "^FO50,820^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(13).getQuantity() + "^FS" +
                                "^FO50,850^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(15).getQuantity() + "^FS" +
                                "^FO50,880^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(17).getQuantity() + "^FS" +
                                "^FO50,910^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(19).getQuantity() + "^FS" +
                                "^FO50,940^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(21).getQuantity() + "^FS" +
                                "^FO50,970^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(23).getQuantity() + "^FS" +
                                "^FO50,1000^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(25).getQuantity() + "^FS" +
                                "^FO50,1030^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(27).getQuantity() + "^FS" +
                                "^FO50,1060^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(29).getQuantity() + "^FS" +
                                "^FO420,640^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(0).getQuantity() + "^FS" +
                                "^FO420,670^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(2).getQuantity() + "^FS" +
                                "^FO420,700^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(4).getQuantity() + "^FS" +
                                "^FO420,730^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(6).getQuantity() + "^FS" +
                                "^FO420,760^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(8).getQuantity() + "^FS" +
                                "^FO420,790^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(10).getQuantity() + "^FS" +
                                "^FO420,820^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(12).getQuantity() + "^FS" +
                                "^FO420,850^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(14).getQuantity() + "^FS" +
                                "^FO420,880^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(16).getQuantity() + "^FS" +
                                "^FO420,910^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(18).getQuantity() + "^FS" +
                                "^FO420,940^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(20).getQuantity() + "^FS" +
                                "^FO420,970^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(22).getQuantity() + "^FS" +
                                "^FO420,1000^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(24).getQuantity() + "^FS" +
                                "^FO420,1030^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(26).getQuantity() + "^FS" +
                                "^FO420,1060^CI28^AZN,20,15^FD" + orderDataModuleDBHeaderkist.get(28).getQuantity() + "^FS" +
                                "^FO500,640^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(0).getName())) + "^FS" +
                                "^FO500,670^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(2).getName())) + "^FS" +
                                "^FO500,700^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(4).getName())) + "^FS" +
                                "^FO500,730^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(6).getName())) + "^FS" +
                                "^FO500,760^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(8).getName())) + "^FS" +
                                "^FO500,790^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(10).getName())) + "^FS" +
                                "^FO500,820^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(12).getName())) + "^FS" +
                                "^FO500,850^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(14).getName())) + "^FS" +
                                "^FO500,880^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(16).getName())) + "^FS" +
                                "^FO500,910^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(18).getName())) + "^FS" +
                                "^FO500,940^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(20).getName())) + "^FS" +
                                "^FO500,970^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(22).getName())) + "^FS" +
                                "^FO500,1000^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(24).getName())) + "^FS" +
                                "^FO500,1030^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(26).getName())) + "^FS" +
                                "^FO500,1060^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(28).getName())) + "^FS" +
                                "^FO130,640^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(1).getName())) + "^FS" +
                                "^FO130,670^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(3).getName())) + "^FS" +
                                "^FO130,700^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(5).getName())) + "^FS" +
                                "^FO130,730^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(7).getName())) + "^FS" +
                                "^FO130,760^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(9).getName())) + "^FS" +
                                "^FO130,790^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(11).getName())) + "^FS" +
                                "^FO130,820^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(13).getName())) + "^FS" +
                                "^FO130,850^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(15).getName())) + "^FS" +
                                "^FO130,880^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(17).getName())) + "^FS" +
                                "^FO130,910^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(19).getName())) + "^FS" +
                                "^FO130,940^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(21).getName())) + "^FS" +
                                "^FO130,970^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(23).getName())) + "^FS" +
                                "^FO130,1000^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(25).getName())) + "^FS" +
                                "^FO130,1030^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(27).getName())) + "^FS" +
                                "^FO130,1060^CI28^AZN,20,15^FD" + maxLength(checkNull(orderDataModuleDBHeaderkist.get(29).getName())) + "^FS" +
                                "^FO30,260^GB750,40,1^FS" +
                                "^FO500,260^GB1,40,1^FS" +
                                "^FO400,260^GB1,40,1^FS" +
                                "^FO200,260^GB1,40,1^FS" +
                                "^CF0,5" +
                                "^AZN,35,20^AAN,15,10^FO90,320^BCN,85,Y,N^FD>" + " " + DetailsList.get(0).getTrackingNumber() + "^FS^PQ1" +
                                "^FO600,320^BQN,2,4^FD" + "   " + DetailsList.get(0).getTrackingNumber() + "^FS" +
                                "^FO480,450^GB300,80,1^FS" +
                                "^FO600,450^GB1,80,1^FS" +
                                "^FO480,490^GB300,1^FS" +
                                "^FO30,450^GB300,130,1^FS" +
                                "^FO150,450^GB1,130,1^FS" +
                                "^FO30,490^GB300,1^FS" +
                                "^FO30,530^GB300,1^FS" +
                                "^FO30,600^GB750,500,1^FS" +
                                "^FO90,600^GB1,500,1^FS" +
                                "^FO400,600^GB1,500,1^FS" +
                                "^FO460,600^GB1,500,1^FS" +
                                "^FO30,630^GB750,1^FS" +
                                "^FO30,660^GB750,1^FS" +
                                "^FO30,690^GB750,1^FS" +
                                "^FO30,720^GB750,1^FS" +
                                "^FO30,750^GB750,1^FS" +
                                "^FO30,780^GB750,1^FS" +
                                "^FO30,810^GB750,1^FS" +
                                "^FO30,840^GB750,1^FS" +
                                "^FO30,870^GB750,1^FS" +
                                "^FO30,900^GB750,1^FS" +
                                "^FO30,930^GB750,1^FS" +
                                "^FO30,960^GB750,1^FS" +
                                "^FO30,990^GB750,1^FS" +
                                "^FO30,1020^GB750,1^FS" +
                                "^FO30,1050^GB750,1^FS" +
                                "^XZ";
                        configLabel = name.getBytes();
                    } catch (NullPointerException e) {
                }
                } else if (printerLanguage == PrinterLanguage.CPCL) {
                    String cpclConfigLabel = "! 0 200 200 406 1\r\n" + "ON-FEED IGNORE\r\n" + "BOX 20 20 380 380 8\r\n" + "T 0 6 137 177 TEST\r\n" + "PRINT\r\n";
                    configLabel = cpclConfigLabel.getBytes();
                }
            } catch(ConnectionException e){
                configLabel = "^XA ^CF0,10 ^FO30,30^CI28^AZN,20,15^FDفرع زايد^FS^FO700,30^CI28^AZN,20,15^FDفرع زايد^FS^FX^FO30,50^GB750,80,1^FS^FO500,50^GB1,80,1^FS ^FO500,50^GB1,80,1^FS^FO500,90^GB280,1^FS^FO30,130^GB750,80,1^FS^FO500,50^GB1,160,1^FS ^CF0,15FO590,65^CI28^AZN,20,15^FDشركة الشحن^FS^FO600,105^CI28^AZN,20,15^FDمدينه العميل^FS^FO600,160^CI28^AZN,20,15^FDمنطقه العميل^FS^FO570,270^CI28^AZN,20,15^FDعدد الشحنات في الطلب^FS^FO450,270^CI28^AZN,20,15^FD7^FS^FO250,270^CI28^AZN,20,15^FDرقم الشحنه^FS^FO100,270^FD2 of 7^FS^FO600,230^CI28^AZN,20,15^FD تحقيق الهوية ^FS^FO400,230^FD 570.30 ^FS^FO100,230^CI28^AZN,20,15^FD طريقه الدفع ^FS ^FO200,465^CI28^AZN,20,15^FDاجمالي الطلب^FS^FO180,505^CI28^AZN,20,15^FDتكلفه الشحن^FS^FO220,550^CI28^AZN,20,15^FDالاجمالي^FS^FO650,465^CI28^AZN,20,15^FDرقم الطلب^FS^FO640,505^CI28^AZN,20,15^FDعدد القطع^FS^FO590,610^CI28^AZN,20,15^FDاسم المنتج^FS^FO200,610^CI28^AZN,20,15^FDاسم المنتج^FS^FO405,610^CI28^AZN,20,15^FDالكميه^FS^FO35,610^CI28^AZN,20,15^FDالكميه^FS^FO50,640^CI28^AZN,20,15^FD3^FS^FO50,670^CI28^AZN,20,15^FD3^FS^FO50,700^CI28^AZN,20,15^FD3^FS ^FO50,730^CI28^AZN,20,15^FD3^FS^FO50,760^CI28^AZN,20,15^FD3^FS^FO50,790^CI28^AZN,20,15^FD3^FS^FO50,820^CI28^AZN,20,15^FD3^FS^FO50,850^CI28^AZN,20,15^FD3^FS^FO50,880^CI28^AZN,20,15^FD3^FS^FO50,910^CI28^AZN,20,15^FD3^FS^FO50,940^CI28^AZN,20,15^FD3^FS^FO50,970^CI28^AZN,20,15^FD3^FS^FO50,1000^CI28^AZN,20,15^FD3^FS^FO50,1030^CI28^AZN,20,15^FD3^FS^FO50,1060^CI28^AZN,20,15^FD3^FS^FO420,640^CI28^AZN,20,15^FD3^FS^FO420,670^CI28^AZN,20,15^FD3^FS^FO420,700^CI28^AZN,20,15^FD3^FS^FO420,730^CI28^AZN,20,15^FD3^FS^FO420,760^CI28^AZN,20,15^FD3^FS^FO420,790^CI28^AZN,20,15^FD3^FS^FO420,820^CI28^AZN,20,15^FD3^FS^FO420,850^CI28^AZN,20,15^FD3^FS^FO420,880^CI28^AZN,20,15^FD3^FS^FO420,910^CI28^AZN,20,15^FD3^FS^FO420,940^CI28^AZN,20,15^FD3^FS^FO420,970^CI28^AZN,20,15^FD3^FS^FO420,1000^CI28^AZN,20,15^FD3^FS^FO420,1030^CI28^AZN,20,15^FD3^FS^FO420,1060^CI28^AZN,20,15^FD3^FS^FO730,640^CI28^AZN,20,15^FDmilk^FS^FO730,670^CI28^AZN,20,15^FDmilk^FS^FO730,700^CI28^AZN,20,15^FDmilk^FS^FO730,730^CI28^AZN,20,15^FDmilk^FS ^FO730,760^CI28^AZN,20,15^FDmilk^FS^FO730,790^CI28^AZN,20,15^FDmilk^FS\n^FO730,820^CI28^AZN,20,15^FDmilk^FS^FO730,850^CI28^AZN,20,15^FDmilk^FS^FO730,880^CI28^AZN,20,15^FDmilk^FS^FO730,910^CI28^AZN,20,15^FDmilk^FS^FO730,940^CI28^AZN,20,15^FDmilk^FS^FO730,970^CI28^AZN,20,15^FDmilk^FS ^FO730,1000^CI28^AZN,20,15^FDmilk^FS ^FO730,1030^CI28^AZN,20,15^FDmilk^FS ^FO730,1060^CI28^AZN,20,15^FDmilk^FS ^FO370,640^CI28^AZN,20,15^FDmilk^FS ^FO370,670^CI28^AZN,20,15^FDmilk^FS ^FO370,700^CI28^AZN,20,15^FDmilk^FS ^FO370,730^CI28^AZN,20,15^FDmilk^FS ^FO370,760^CI28^AZN,20,15^FDmilk^FS ^FO370,790^CI28^AZN,20,15^FDmilk^FS ^FO370,820^CI28^AZN,20,15^FDmilk^FS ^FO370,850^CI28^AZN,20,15^FDmilk^FS ^FO370,880^CI28^AZN,20,15^FDmilk^FS ^FO370,910^CI28^AZN,20,15^FDmilk^FS ^FO370,940^CI28^AZN,20,15^FDmilk^FS ^FO370,970^CI28^AZN,20,15^FDmilk^FS ^FO370,1000^CI28^AZN,20,15^FDmilk^FS ^FO370,1030^CI28^AZN,20,15^FDmilk^FS ^FO370,1060^CI28^AZN,20,15^FDmilk^FS ^FO30,260^GB750,40,1^FS ^FO500,260^GB1,40,1^FS ^FO400,260^GB1,40,1^FS ^FO200,260^GB1,40,1^FS ^AZN,35,20^AAN,15,10^FO90,320^BCN,85,Y,N^FD>;H1506070807080^FS^PQ1 ^FO600,320^BQN,2,4^FDH1506070807080^FS ^FO480,450^GB300,80,1^FS ^FO600,450^GB1,80,1^FS ^FO480,490^GB300,1^FS ^FO30,450^GB300,130,1^FS ^FO150,450^GB1,130,1^FS ^FO30,490^GB300,1^FS ^FO30,530^GB300,1^FS ^FO30,600^GB750,500,1^FS ^FO90,600^GB1,500,1^FS ^FO400,600^GB1,500,1^FS ^FO460,600^GB1,500,1^FS ^FO30,630^GB750,1^FS ^FO30,660^GB750,1^FS ^FO30,690^GB750,1^FS ^FO30,720^GB750,1^FS ^FO30,750^GB750,1^FS ^FO30,780^GB750,1^FS ^FO30,810^GB750,1^FS ^FO30,840^GB750,1^FS ^FO30,870^GB750,1^FS ^FO30,900^GB750,1^FS ^FO30,930^GB750,1^FS ^FO30,960^GB750,1^FS ^FO30,990^GB750,1^FS ^FO30,1020^GB750,1^FS ^FO30,1050^GB750,1^FS^XZ".getBytes();
            }

        return configLabel;
    }



    private void doConnectionTest() {
        printer = connect();

        if (printer != null) {
            sendTestLabel();
        } else {
            disconnect();
            dialog.dismiss();
        }
    }

    private void toggleEditField(EditText editText, boolean set) {
        /*
         * Note: Disabled EditText fields may still get focus by some other means, and allow text input.
         *       See http://code.google.com/p/android/issues/detail?id=2771
         */
        editText.setEnabled(set);
        editText.setFocusable(set);
        editText.setFocusableInTouchMode(set);
    }

    private boolean isBluetoothSelected() {
        return btRadioButton.isChecked();
    }

    private String getMacAddressFieldText() {
        return macAddressEditText.getText().toString();
    }

    private String getTcpAddress() {
        return ipAddressEditText.getText().toString();
    }

    private String getTcpPortNumber() {
        return portNumberEditText.getText().toString();
    }

    public String maxLength(String name)
    {
        if (name.length() > 40) {
            name = name.substring(0, 40);
        }
        return name;
    }

    public String checkNull(String name)
    {
        if (name.equals(null)) {
            return "";
        }
        else
        {return name;}
    }


    public String checkPaymentMethod(String name)
    {
        if (name.equals("0")) {
            return "كاش";
        }
        else
        {return "أون لاين";}
    }

}
