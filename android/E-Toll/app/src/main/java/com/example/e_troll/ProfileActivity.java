package com.example.e_troll;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {


    GlobalPreference globalPreference;


    ImageView backIV;
    TextView nameTV;
    TextView emailTV;
    TextView vehicleTV;
    TextView phoneTV;



    private String id;
    private String ip;
    String userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        globalPreference= new GlobalPreference(this);
        id= globalPreference.getID();

        ip=globalPreference.getIp();
        //Toast.makeText(this, "hellooooooo"+id, Toast.LENGTH_SHORT).show();

        nameTV=findViewById(R.id.nameTextView);
        emailTV=findViewById(R.id.emailTextView);
        vehicleTV=findViewById(R.id.usernameTextView);
        phoneTV=findViewById(R.id.phoneTextView);
        backIV=findViewById(R.id.BbackImageView);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        getUserProfile();
    }

    private void getUserProfile() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/E-Toll/api/profile.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //  Toast.makeText(ProfileActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                if (!response.equals("")) ;
                {
                    try {

                        userData = response;

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject data = jsonArray.getJSONObject(0);

                        String name = data.getString("name");
                        String email = data.getString("email");
                        String vehicle = data.getString("vehicle_no");
                        String phone = data.getString("phone");


                        nameTV.setText(name);
                        emailTV.setText(email);
                        vehicleTV.setText(vehicle);
                        phoneTV.setText(phone);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, "" + error, Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            @Nullable

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", id);
                return params;


            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        requestQueue.add(stringRequest);
    }
}