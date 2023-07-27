package com.example.e_troll;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    GlobalPreference globalPreference;
    private String ip;

    EditText emailET;
    EditText passwordET;
    Button signinBT;
    TextView signupTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();

        // Toast.makeText(this, ""+ip, Toast.LENGTH_SHORT).show();

        emailET = findViewById(R.id.LoginemailEditText);
        passwordET = findViewById(R.id.LoginpasswordEditText);
        signinBT = findViewById(R.id.LoginsigninButton);
        signupTV = findViewById(R.id.LoginsignupTextView);

        signinBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Toast.makeText(LoginActivity.this, ""+ip, Toast.LENGTH_SHORT).show();

                login();
            }
        });

        signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void login() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/E-Toll/api/login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("failed")) {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String id = jsonObject.getString("id");
                        globalPreference.saveID(id);
                        String name = jsonObject.getString("name");
                        globalPreference.savename(name);
                        String vehicle_no = jsonObject.getString("vehicle_no");
                        globalPreference.saveVehicle_no(vehicle_no);


                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",emailET.getText().toString());
                params.put("password",passwordET.getText().toString());
                return  params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }
}