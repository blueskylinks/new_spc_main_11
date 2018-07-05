package com.blueskylinks.spc_main;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static String subId;
    String pass;
    TextView text;
    CheckBox c1;
    SharedPreferences prefs;
   EditText edt1;
    boolean value1 = true;
    public static String phoneNumber;
    TextView textView;
    String Subno;
    Spinner SPINNER;
    Button ADD;
    EditText EDITTEXT;
    String[] spinnerItems = new String[]{};
    String GETTEXT;
    String gettext="111";
    List<String> stringlist;
    ArrayAdapter<String> arrayadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS}, 200);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_SMS}, 200);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS}, 200);



        SPINNER = (Spinner)findViewById(R.id.spinner1);
        ADD = (Button)findViewById(R.id.button1);
        EDITTEXT = (EditText)findViewById(R.id.editText1);
        stringlist = new ArrayList<>(Arrays.asList(spinnerItems));

        arrayadapter = new ArrayAdapter<String>(MainActivity.this,R.layout.textview,stringlist);

        arrayadapter.setDropDownViewResource(R.layout.textview);
        SPINNER.setAdapter(arrayadapter);

        prefs = getSharedPreferences("TAG", Context.MODE_PRIVATE);
        gettext= prefs.getString("0", null);
        if(gettext!=null){
            Log.i("value",gettext);
            stringlist.add(gettext);
            arrayadapter.notifyDataSetChanged();
        }

        gettext= prefs.getString("1", null);
        if(gettext!=null){
            Log.i("value",gettext);
            stringlist.add(gettext);
            arrayadapter.notifyDataSetChanged();
        }

        gettext= prefs.getString("2", null);
        if(gettext!=null){
            Log.i("value",gettext);
            stringlist.add(gettext);
            arrayadapter.notifyDataSetChanged();
        }

        gettext= prefs.getString("3", null);
        if(gettext!=null){
            Log.i("value",gettext);
            stringlist.add(gettext);
            arrayadapter.notifyDataSetChanged();
        }

        gettext= prefs.getString("4", null);
        if(gettext!=null){
            Log.i("value",gettext);
            stringlist.add(gettext);
            arrayadapter.notifyDataSetChanged();
        }



        SPINNER.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Subno = SPINNER.getItemAtPosition(i).toString();
                Log.i("Selected item",Subno);
                phoneNumber=Subno;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


public void display_editBox(View v){
    EDITTEXT.setVisibility(View.VISIBLE);
    ADD.setVisibility(View.VISIBLE);
}


    public void update_spinner(View v){
        GETTEXT = EDITTEXT.getText().toString();
        if(stringlist.contains(GETTEXT)){}
        else{
        stringlist.add(GETTEXT);
        arrayadapter.notifyDataSetChanged();
        prefs = this.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        int device_size=stringlist.size();
        for(int i=0;i<device_size;i++){
            if(device_size<=5) {
                prefs.edit().putString(String.valueOf(i), stringlist.get(i)).commit();
            }
        }

        Log.i("updated string",stringlist.toString());
        }
        EDITTEXT.setVisibility(View.INVISIBLE);
        ADD.setVisibility(View.INVISIBLE);
    }

    public void set_activity(View view) {
        /*subId = et1.getText().toString();
        pass = et2.getText().toString();
        Log.i("sub ID:",subId);
        Log.i("password",pass);
        if (TextUtils.isEmpty(subId) || TextUtils.isEmpty(pass)) {
            if (TextUtils.isEmpty(subId)) et1.setError("please Enter subscriber Id");
            else et2.setError("please Enter password");
        } else if (subId.length()!=10) {
            text.setText("Incorrect subscribe ID!!..");
        } else if (!pass.equals("1234")) {
            text.setText("Incorrect  password!!..");
        } else {
            phoneNumber=subId;*/
            //starting another activity..
            subId=Subno;
            Intent it = new Intent(MainActivity.this, splashActivity.class);
            startActivity(it);

    }
}

