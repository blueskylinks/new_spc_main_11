package com.blueskylinks.spc_main;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import static com.blueskylinks.spc_main.MainActivity.phoneNumber;
import static com.blueskylinks.spc_main.MainActivity.subId;

public class Main2Activity extends AppCompatActivity {
    public static  TextView tv;
    public static  TextView tv1;
    public static  TextView tv2;
    public static  TextView tv3;
    public static  TextView tv4;
    public static  TextView tv5;
    public static  TextView textView6;
    public static  TextView tc1;
    public static  TextView tc2;
    public static  TextView tc3;

    TextView tv9;
    public static ProgressDialog progressDialog;
    String phoneNumber = subId;
    //changes made
    String SMSBody1;

    public static SharedPreferences mt_status_pref;
    public static SharedPreferences.Editor editor;
    public static int mot_st;
    public static int app_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mt_status_pref = getSharedPreferences("status1", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS}, 200);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_SMS}, 200);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS}, 200);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_PHONE_STATE}, 200);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "digital7.ttf");
        tv = findViewById(R.id.textView11);
        tv1 = findViewById(R.id.textView12);
        tv2 = findViewById(R.id.textView13);
        tv3 = findViewById(R.id.textView17);
        tv4 = findViewById(R.id.textView18);

        tc1 = findViewById(R.id.textView23);
        tc2 = findViewById(R.id.textView24);
        tc3 = findViewById(R.id.textView25);

        tv2.setTypeface(typeface);
        tv3.setTypeface(typeface);
        tv4.setTypeface(typeface);
        tc1.setTypeface(typeface);
        tc2.setTypeface(typeface);
        tc3.setTypeface(typeface);

        tv9=findViewById(R.id.textView29);
        if(app_status!=1){
            String message = "SPC,25";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            ImageView mImageViewFilling = findViewById(R.id.image_rot);
            ((AnimationDrawable) mImageViewFilling.getBackground()).stop();
            Log.i("Test", "SMS sent!");
            progress();
            app_status =1;
        }

    }

    @Override
    protected void onResume(){
        super.onResume();
        SMSBody1="";
        editor = mt_status_pref.edit();
        app_status = mt_status_pref.getInt("a1",0);
        Log.i("aaaaa", Integer.toString(app_status));
        if(mot_st != mt_status_pref.getInt("m1",0)){
            editor.putInt("m1",mot_st);
            editor.commit();
        }

        mot_st = mt_status_pref.getInt("m1",0);
        if(mot_st==1)
            tv.setText("ON");
        if(mot_st==0)
            tv.setText("OFF");

        final IntentFilter mIntentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(sms_notify_reciver, mIntentFilter);
        registerReceiver(sms_notify_reciver,mIntentFilter);
    }

    public void refresh(View view){
            String message = "SPC,25";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("Test", "SMS sent!");
            progress();
    }

    //Progress Dialog
    public void progress(){
        progressDialog = new ProgressDialog(Main2Activity.this);
        progressDialog.setMessage("Please Wait......"); // Setting Message
        progressDialog.setTitle("Loading!!!"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();
    }


    public void settings(View view){
            //starting another activity..
            Intent it1 = new Intent(Main2Activity.this, SettingsActivity.class);
            startActivity(it1);
    }

    public void ON_OFF(View view){
        //starting another activity..
        Intent it2 = new Intent(Main2Activity.this, ON_OFFActivity.class);
        startActivity(it2);
    }

    public void Users(View view){
        Intent it3 = new Intent(Main2Activity.this, UsersActivity.class);
        //starting another activity..
        startActivity(it3);
    }

    public void manual(View view){
        //starting another activity..
        Intent it4 = new Intent(Main2Activity.this, ManualActivity.class);
        startActivity(it4);
    }


    private final BroadcastReceiver sms_notify_reciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent2) {
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent2.getAction())) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent2)) {
                    String senderNum = smsMessage.getDisplayOriginatingAddress();
                    //  Log.i("sender num", senderNum);
                    SMSBody1 += smsMessage.getMessageBody().toString();
                    Log.i("length", String.valueOf(SMSBody1.length()));
                    Log.i("Received SMS:", SMSBody1);
                    String[] lines = SMSBody1.split("\\r?\\n");
                    int l = lines.length-1;
                    Log.i("lines length", String.valueOf(l));

                    String sms = SMSBody1;

                        if (lines[1].toString().contains("on ")) {
                            ImageView mImageViewFilling = findViewById(R.id.image_rot);
                            ((AnimationDrawable) mImageViewFilling.getBackground()).start();
                            tv.setText("ON");
                            editor = mt_status_pref.edit();
                            editor.putInt("m1",1);
                            editor.commit();
                            mot_st=1;
                            if (lines[1].toString().contains("3")) tv1.setText("3 Phase Mode");
                            else tv1.setText("2 Phase Mode");
                            String s1 = lines[2].toString();
                            String s2 = lines[3].toString();
                            String s3 = lines[4].toString();
                            String s4 = lines[l].toString();
                            tv2.setText(s1.substring(4));
                            tv3.setText(s2.substring(4));
                            tv4.setText(s3.substring(4));

                            tc1.setText(lines[6].toString().substring(3));
                            tc2.setText(lines[7].toString().substring(3));
                            tc3.setText(lines[8].toString().substring(3));
                            //tv9.setText(s4.substring(6));
                        } else if (lines[1].toString().contains("off")) {
                            tv.setText("OFF");
                            editor = mt_status_pref.edit();
                            editor.putInt("m1",0);
                            editor.commit();
                            mot_st=0;
                            String s4 = lines[5].toString();
                            //tv9.setText(s4.substring(6));
                        } else return;
                        SMSBody1 = "";

                }
            }
        }
    };

    @Override
    public void onPause(){
        super.onPause();
        unregisterReceiver(sms_notify_reciver);
    }


}
