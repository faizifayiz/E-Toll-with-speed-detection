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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    GlobalPreference globalPreference;
    private String ip;

    EditText nameET;
    EditText emailET;
    EditText vehicleno;
    EditText phoneET;
    EditText passwordET;
    Button signupBT;
    TextView signinTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        nameET=findViewById(R.id.nameEditText);
        emailET=findViewById(R.id.emailEditText);
        vehicleno=findViewById(R.id.vehiclenoEditText);
        phoneET=findViewById(R.id.phoneEditText);
        passwordET=findViewById(R.id.passwordEditText);
        signupBT=findViewById(R.id.signupButton);
        signinTV=findViewById(R.id.signinTextView);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();



        signinTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        signupBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                String vehicle = vehicleno.getText().toString();
                String phone = phoneET.getText().toString();

                // Toast.makeText(RegisterActivity.this, ""+ip, Toast.LENGTH_SHORT).show();


                rRegister(name, email, password, vehicle, phone);
            }
        });


    }

    private void rRegister(String name, String email, String password, String vehicle, String phone) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/E-Toll/api/register.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                    Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("phone", phone);
                params.put("vehicle", vehicle);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(stringRequest);
    }
}