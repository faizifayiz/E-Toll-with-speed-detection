package com.example.e_troll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.example.ETroll.Services.LocationUploadService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.util.List;
import java.util.Map;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_troll.Services.LocationService;



public class HomeActivity extends AppCompatActivity {

    GlobalPreference globalPreference;

    CardView walletCV;
    CardView locationCv;
    CardView feedbackCV;
    CardView fineCv;
    CardView tollcv;
    TextView nameTV;
    ImageView profileIV;


    private String uid;
    private String ip;
    private  String name;
    private String latitude,longitude;
    private String location;
    private SensorManager sm;


    private long lastUpdate = 0;
    //private float last_x, last_y, last_z;
    //private static final int SHAKE_THRESHOLD = 600;
    private List<Sensor> list;
    private static final String TAG = "HomeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent mIntent = new Intent(HomeActivity.this, LocationService.class);
        startService(mIntent);

        globalPreference = new GlobalPreference(this);
        uid = globalPreference.getID();
        name = globalPreference.getname();
        latitude = globalPreference.getLatitude();

        longitude = globalPreference.getLongitude();


        ip = globalPreference.getIp();

        walletCV = findViewById(R.id.walletCV);
        locationCv = findViewById(R.id.locationCV);
        nameTV = findViewById(R.id.hnameTextView);
        profileIV = findViewById(R.id.profileImageView);
        fineCv = findViewById(R.id.FineCV);
        feedbackCV = findViewById(R.id.feedbackCV);
        tollcv = findViewById(R.id.tollCV);


        location = "Location\nhttps://www.google.com/maps?q=" + latitude + ',' + longitude;
        //Toast.makeText(this, "asdfg" + latitude, Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, "" + latitude + longitude, Toast.LENGTH_SHORT).show();

        nameTV.setText(name);

        walletCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, WalletActivity.class);
                startActivity(intent);

            }
        });

        locationCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!latitude.isEmpty() && !longitude.isEmpty()) {
                    location = "Location\nhttps://www.google.com/maps?q=" + latitude + "," + longitude;
                   // Toast.makeText(HomeActivity.this, location, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
                    startActivity(intent);
                } else {
                    // Location data is not available yet, handle this case as needed
                    Toast.makeText(HomeActivity.this, "Location data is not available yet. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        profileIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        feedbackCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });


        fineCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this,FineActivity.class);
                startActivity(intent);

            }
        });
        updateAccelValues();


        tolllocation();

        tollcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,TollActivity.class);
                startActivity(intent);
            }
        });

    }

        private void updateAccelValues() {

            /* Get a SensorManager instance */
            sm = (SensorManager)getSystemService(SENSOR_SERVICE);

            list = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
            if(list.size()>0){
                sm.registerListener(sel, (Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
            }else{
                Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();
            }
        }

        private void accelrometer(String x, String y, String z,String acc) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ip+"/E-Toll/api/accelerometer.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Acce" ,"onResponse: "+response);
                    if(response.equals("success")){
                        Toast.makeText(getApplicationContext(),"sensor is on",Toast.LENGTH_SHORT).show();


                        Intent intent= new Intent(HomeActivity.this,FineActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("error"+error.getMessage());
                    Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("uid",uid);
                    params.put("x",x);
                    params.put("y",y);
                    params.put("z",z);
                    params.put("lat",latitude);
                    params.put("lng",longitude);
                    params.put("acc",acc);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }

        SensorEventListener sel = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                //textView1.setText("x: "+values[0]+"\ny: "+values[1]+"\nz: "+values[2]);

                GlobalPreference globalPreference = new GlobalPreference(getApplicationContext());
                ip = globalPreference.getIp();

                Sensor mySensor = event.sensor;

                if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    final float x = event.values[0];
                    final float y = event.values[1];
                    float z = event.values[2];
                    Log.d(TAG, "onSensorChanged x: " + x);
                    Log.d(TAG, "onSensorChanged y: " + y);
                    Log.d(TAG, "onSensorChanged z: " + z);

                    float gvalue = (float) (Math.sqrt((x * x) + (y * y) + (z * z)) / 9.8);


                    long curTime = System.currentTimeMillis();

                    if ((curTime - lastUpdate) > 1000) {
                        lastUpdate = curTime;

                        System.out.println("G value" + gvalue);
                        if (gvalue > 2) {
                   /*  String phoneNo = "8943409211";
                     SmsManager smsManager = SmsManager.getDefault();
                     smsManager.sendTextMessage(phoneNo, null, mesage, null, null);
                     Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();  */

                           // getBlacklistNotification();

                        }

                        updatedata(x,y,z);

                    }

                }




            }
        };

        private void updatedata(float a,float b,float c) {


            //Toast.makeText(HomeActivity.this,""+a,Toast.LENGTH_LONG).show();
            String x = String.valueOf(a);
            String y = String.valueOf(b);
            String z = String.valueOf(c);


            if (a > 10.0 ) {
                // [speed]
                String acc = "Over Speed detected";

                accelrometer( x, y, z,acc);

               // Toast.makeText(this, "Over Speeded", Toast.LENGTH_SHORT).show();
            }

//            if (a < 0) {
//                // [accident]
//
//                System.out.println("accident");
//
//                String acc = "accident";
//                accelrometer(uid, x, y, z, latitude, longitude, acc);
//
//            }
//            if (a > 0 || b > 0 || c > 10.0) {
//                // [pot hole]
//                String acc = "pit hole";
//                accelrometer(uid, x, y, z, latitude, longitude, acc);
//            }


        }




        @Override
        protected void onStop() {
            if(list.size()>0){
                sm.unregisterListener(sel);
            }
            super.onStop();



    }
    private void tolllocation() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/E-Toll/api/toll.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                    Toast.makeText(HomeActivity.this, "location passed", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(HomeActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", uid);
                params.put("lat", latitude);
                params.put("lon", longitude);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(stringRequest);

    }

}